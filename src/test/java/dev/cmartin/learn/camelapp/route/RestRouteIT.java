package dev.cmartin.learn.camelapp.route;

import lombok.val;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = RestRouteIT.DEVICES_TOPIC, bootstrapServersProperty = "kafka.brokers")
class RestRouteIT {

    static final String DEVICES_TOPIC = "devices";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafka;

    @ParameterizedTest
    @CsvSource({
            "position.json, 201",
            "position-invalid.json, 400",
            "position-empty.json, 400",
            "position-invalid-pos-coords.json, 400",
            "position-invalid-neg-coords.json, 400"
    })
    void should_return_status_for_position(final String file, final int expectedStatus) throws Exception {
        val response = post("positions", readBody(file));

        assertThat(response.statusCode()).isEqualTo(expectedStatus);
    }

    @Test
    void should_reject_missing_position() throws Exception {
        val response = post("positions", new byte[0]);

        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    void should_publish_device_to_kafka() throws Exception {
        val props = KafkaTestUtils.consumerProps(embeddedKafka, "rest-route-it", true);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (val consumer = new KafkaConsumer<>(props, new StringDeserializer(), new StringDeserializer())) {
            embeddedKafka.consumeFromAnEmbeddedTopic(consumer, DEVICES_TOPIC);

            val response = post("devices", readBody("device.json"));

            assertThat(response.statusCode()).isEqualTo(201);
            assertThat(KafkaTestUtils.getSingleRecord(consumer, DEVICES_TOPIC).value())
                    .contains("81700414-bd1a-420d-864b-5e062ee07e3e", "A350-900");
        }
    }

    private byte[] readBody(final String file) throws IOException {
        return new ClassPathResource(file).getContentAsByteArray();
    }

    private HttpResponse<Void> post(final String path, final byte[] body) throws IOException, InterruptedException {
        val request = HttpRequest.newBuilder(URI.create("http://localhost:%d/camel/api/%s".formatted(port, path)))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.discarding());
    }
}
