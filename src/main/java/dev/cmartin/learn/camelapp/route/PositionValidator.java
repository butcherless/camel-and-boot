package dev.cmartin.learn.camelapp.route;

import dev.cmartin.learn.camelapp.api.Model.InvalidPositionException;
import dev.cmartin.learn.camelapp.api.Model.Position;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.DoubleRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static dev.cmartin.learn.camelapp.route.ProcessorUtils.getBody;

public class PositionValidator
        implements Processor {
    private final Logger logger = LoggerFactory.getLogger(PositionValidator.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        var position = getBody(exchange, Position.class);
        this.logger.debug("validating {}", position);

        var errors = new Validator().validate(position);

        this.logger.debug("errors {}", errors);


        ProcessorUtils.handleErrors(errors, InvalidPositionException.class);
    }

    /**
     * Latitude: -90 to 90 degrees
     * Longitude: -180 to 180 degrees
     */
    static class Validator {
        List<String> validate(Position position) {
            var errors = new ArrayList<String>();

            if (Objects.isNull(position)) {
                errors.add("missing position");
                return errors;
            }

            if (Objects.isNull(position.longitude())) {
                errors.add("missing longitude");
            } else {
                if (!DoubleRange.of(-180.0, 180.0).contains(position.longitude())) {
                    errors.add("invalid longitude");
                }
            }
            if (Objects.isNull(position.latitude())) {
                errors.add("missing latitude");
            } else {
                if (!DoubleRange.of(-90.0, 90.0).contains(position.latitude())) {
                    errors.add("invalid latitude");
                }
            }
            if (Objects.isNull(position.deviceId())) {
                errors.add("missing deviceId");
            }

            if (Objects.isNull(position.instant())) {
                errors.add("missing instant");
            }


            return errors;
        }
    }
}
