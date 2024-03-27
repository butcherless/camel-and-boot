package dev.cmartin.learn.camelapp.route;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.cmartin.learn.camelapp.api.Model;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;

import static dev.cmartin.learn.camelapp.route.ProcessorUtils.getBody;
import static dev.cmartin.learn.camelapp.route.ProcessorUtils.setBody;

public class DeviceConverter
        implements Processor {

    private final ObjectMapper objectMapper;

    public DeviceConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        var device = getBody(exchange, Model.Device.class);
        exchange.getMessage().setHeader(KafkaConstants.KEY, device.id());
        setBody(exchange, objectMapper.writeValueAsString(device));
    }
}
