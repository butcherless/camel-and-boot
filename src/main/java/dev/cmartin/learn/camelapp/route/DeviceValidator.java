package dev.cmartin.learn.camelapp.route;

import dev.cmartin.learn.camelapp.api.Model;
import dev.cmartin.learn.camelapp.api.Model.Device;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static dev.cmartin.learn.camelapp.route.ProcessorUtils.getBody;

public class DeviceValidator
        implements Processor {
    private final Logger logger = LoggerFactory.getLogger(DeviceValidator.class);

    @Override
    public void process(Exchange exchange) throws Exception{
        var device = getBody(exchange, Device.class);
        this.logger.debug("validating {}", device);

        var errors = new Validator().validate(device);

        this.logger.debug("errors {}", errors);


        ProcessorUtils. handleErrors(errors, Model.InvalidDeviceException.class);

        /*if (!errors.isEmpty()) {
            var errorList = errors.stream().collect(Collectors.joining(",", "[", "]"));
            throw new InvalidPositionException(errorList);
        }*/
    }

    /**
     * Latitude: -90 to 90 degrees
     * Longitude: -180 to 180 degrees
     */
    static class Validator {
        List<String> validate(Device device) {
            var errors = new ArrayList<String>();

            if (Objects.isNull(device)) {
                errors.add("missing device");
                return errors;
            }

            if (Objects.isNull(device.id())) {
                errors.add("missing identifier");
            }/* else {
                if (!DoubleRange.of(-180.0, 180.0).contains(device.longitude())) {
                    errors.add("invalid longitude");
                }
            }*/
            if (Objects.isNull(device.description())) {
                errors.add("missing description");
            }/* else {
                if (!DoubleRange.of(-90.0, 90.0).contains(device.latitude())) {
                    errors.add("invalid latitude");
                }
            }*/
            if (Objects.isNull(device.type())) {
                errors.add("missing type");
            }

            return errors;
        }
    }
}
