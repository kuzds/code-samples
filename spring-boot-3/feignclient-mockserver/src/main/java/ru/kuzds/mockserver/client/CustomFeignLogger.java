package ru.kuzds.mockserver.client;

import feign.Logger;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFeignLogger extends Logger {

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {

        log(configKey, "---> %s %s ", request.httpMethod().name(), request.url());
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) {
//        int status = response.status();
//        Request request = response.request();
//        log(configKey, "<--- %s %s %s (%sms) ", request.httpMethod().name(), request.url(), status, elapsedTime);
        return response;
    }


    @Override
    protected void log(String configKey, String format, Object... args) {
        log.info(format(configKey, format, args));
    }

    protected String format(String configKey, String format, Object... args) {
        return String.format(format, args);
    }
}
