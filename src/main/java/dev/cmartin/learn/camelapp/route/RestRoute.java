package dev.cmartin.learn.camelapp.route;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dev.cmartin.learn.camelapp.api.Model.Device;
import dev.cmartin.learn.camelapp.api.Model.InvalidPositionException;
import dev.cmartin.learn.camelapp.api.Model.Position;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ValueBuilder;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class RestRoute
        extends RouteBuilder {


    @Override
    public void configure() {
        this.configureExceptionHandling();

        restConfiguration()
                .component("servlet")
                //.apiContextPath("/api-doc")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
        ;

        rest("/api")
                // POSITION
                .post("/positions")
                .routeId("post-position")
                .description("create a device position")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(Position.class)
                .to(RouteNames.CREATE_POSITION_ENDPOINT)
                // DEVICE
                .post("/devices")
                .routeId("post-device")
                .description("create a device")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(Device.class)
                .to(RouteNames.CREATE_DEVICE_ENDPOINT)
        ;

        from(RouteNames.CREATE_POSITION_ENDPOINT)
                .routeId("create-position")
                .log("received data: ${body}")
                .bean(PositionValidator.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, buildCreatedCode())
                .setBody(buildEmptyBody())
        ;

        from(RouteNames.CREATE_DEVICE_ENDPOINT)
                .routeId("create-device")
                .log("received data: ${body.id}")
                .bean(DeviceValidator.class)
                .bean(DeviceConverter.class)
                .to("kafka:devices?brokers=localhost:29092")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, buildCreatedCode())
                .setBody(buildEmptyBody())
        //.to("direct:my-log-1") // multiple endpoints call test
        //.to("direct:my-log-2")
        ;


        from("direct:my-log-1")
                .routeId("log-1")
                .log("${body}")
        ;

        from("direct:my-log-2")
                .routeId("log-2")
                .log("${body}")
        ;

    }

    private ValueBuilder buildEmptyBody() {
        return constant((Object) null);
    }

    private ValueBuilder buildCreatedCode() {
        return constant(HttpStatus.CREATED.value());
    }

    private void configureExceptionHandling() {
        onException(
                InvalidFormatException.class,
                InvalidPositionException.class)
                .log("invalid message format: ${body}")
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST.value()))
                .process(new InvalidPositionHandler())
        ;

    }


}
