package dev.cmartin.learn.camelapp.route;

public interface RouteNames {
    String XML_MESSAGE_HEADER = "XML_MESSAGE_HEADER";
    String VALIDATION_ENDPOINT = "direct:verifyWhsordMessage";
    String STORING_ENDPOINT = "direct:storeWhsordMessage";

    String FINDING_ENDPOINT = "direct:findWhsordMessage";
    String CREATE_POSITION_ENDPOINT = "direct:create-position";
}
