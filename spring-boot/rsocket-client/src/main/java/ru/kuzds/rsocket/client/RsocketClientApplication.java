package ru.kuzds.rsocket.client;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import ru.kuzds.rsocket.client.dto.Alert;
import ru.kuzds.rsocket.client.dto.GratuityIn;
import ru.kuzds.rsocket.client.dto.GratuityOut;
import ru.kuzds.rsocket.client.dto.StockQuote;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class RsocketClientApplication {

    private final RSocketRequester rSocketRequester;

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(RsocketClientApplication.class, args);
        Thread.sleep(10_000);
    }

    @PostConstruct
    void init() {
        rSocketRequester
                .route("greetings.{lang}", Locale.ENGLISH)
                .data("World").retrieveMono(String.class)
                .subscribe(greetings -> log.info("got: " + greetings));
        // ------------------------------------------------------------------------------------------------------------

        String who = "Craig";
        rSocketRequester
                .route("greetings/{name}", who)
                .data("Hello RSocket!")
                .retrieveMono(String.class)
                .subscribe(response -> log.info("Got a response: {}", response));
        // ------------------------------------------------------------------------------------------------------------

        String stockSymbol = "XYZ";
        rSocketRequester
                .route("stock/{symbol}", stockSymbol)
                .retrieveFlux(StockQuote.class)
                .doOnNext(stockQuote ->
                        log.info(
                                "Price of {} : {} (at {})",
                                stockQuote.getSymbol(),
                                stockQuote.getPrice(),
                                stockQuote.getTimestamp())
                )
                .subscribe();
        // ------------------------------------------------------------------------------------------------------------

        rSocketRequester
                .route("alert")
                .data(new Alert(
                        Alert.Level.RED, "Craig", Instant.now()))
                .send()
                .subscribe();
        log.info("Alert sent");
        // ------------------------------------------------------------------------------------------------------------

        Flux<GratuityIn> gratuityInFlux =
                Flux.fromArray(new GratuityIn[] {
                                new GratuityIn(BigDecimal.valueOf(35.50), 18),
                                new GratuityIn(BigDecimal.valueOf(10.00), 15),
                                new GratuityIn(BigDecimal.valueOf(23.25), 20),
                                new GratuityIn(BigDecimal.valueOf(52.75), 18),
                                new GratuityIn(BigDecimal.valueOf(80.00), 15)
                        })
                        .delayElements(Duration.ofSeconds(1));
        rSocketRequester
                .route("gratuity")
                .data(gratuityInFlux)
                .retrieveFlux(GratuityOut.class)
                .subscribe(out ->
                        log.info(out.getPercent() + "% gratuity on "
                                + out.getBillTotal() + " is "
                                + out.getGratuity()));
    }
}


