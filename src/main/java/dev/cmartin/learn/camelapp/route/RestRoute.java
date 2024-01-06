package dev.cmartin.learn.camelapp.route;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dev.cmartin.learn.camelapp.api.Model.InvalidPositionException;
import dev.cmartin.learn.camelapp.api.Model.Position;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ValueBuilder;
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
                .post("/positions")
                .apiDocs(true)
                .routeId("rest-positions")
                .description("create a device position")
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(Position.class)
                .to(RouteNames.CREATE_POSITION_ENDPOINT)
        ;

        from(RouteNames.CREATE_POSITION_ENDPOINT)
                .routeId("create-position-route")
                .log("received data: ${body}")
                .bean(PositionValidator.class)
                //.onException(InvalidPositionException.class).bean(InvalidPositionHandler.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, buildCreatedCode())
                .setBody(buildEmptyBody())
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
