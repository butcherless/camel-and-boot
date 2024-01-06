package dev.cmartin.learn.camelapp.route;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import dev.cmartin.learn.camelapp.api.Model;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;

public class InvalidPositionHandler
        implements Processor {
    private final Logger logger = LoggerFactory.getLogger(PositionValidator.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.debug("handling exception");
        var exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        var detail = switch (exception) {
            case Model.InvalidPositionException ex -> ex.getMessage();
            case InvalidFormatException ex -> ex.getMessage();
            default -> "unknown error";
        };

        exchange
                .getMessage()
                .setBody(buildProblemDetail(detail));
    }

    private ProblemDetail buildProblemDetail(String detail) {
        final var pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        pd.setTitle("Invalid Position");
        pd.setType(URI.create(""));
        return pd;
    }
}
