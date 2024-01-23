package ru.tkbbank.smartvisa.isfront.route;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestRoute extends RouteBuilder {
    private final static String DIRECT_TO_ACTIVEMQ = "direct:to-activemq";

    @Value("${camel.servlet.mapping.context-path}")
    private String contextPath;

    @Value("${activemq.queue}")
    private String queue;

    private final Environment env;


    @Override
    public void configure() {

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.auto)
                .dataFormatProperty("prettyPrint", "true")
                .enableCORS(true)
                .port(env.getProperty("server.port", "8080"))
                .contextPath(contextPath.substring(0, contextPath.length() - 2))

                // turn on openapi api-doc
                .apiContextPath("/api-doc")
                .apiContextRouteId("api-doc")

                .apiProperty("api.title", "SmartVista CBS isFront")
                .apiProperty("api.version", "1.0")
        ;

        rest().path("/v1").tag("Notification Controller")

                .post("/notify").type(String.class).outType(String.class)
                .consumes(MediaType.TEXT_PLAIN_VALUE)
                .description("Отправка уведомления")
                .responseMessage().code(200).message("OK").endResponseMessage()
                .to(DIRECT_TO_ACTIVEMQ)
        ;

        from(DIRECT_TO_ACTIVEMQ).routeId("send-to-activemq")
                .wireTap("activemq:queue:" + queue)
                .setBody(simple("OK"))
        ;
    }
}
