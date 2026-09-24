module com.ryvex.client {

    requires javafx.controls;
    requires java.net.http;
    requires tools.jackson.databind;

    exports com.ryvex.client;

    opens com.ryvex.client.dto.auth
            to tools.jackson.databind;
}