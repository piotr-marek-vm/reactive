package pl.vm.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Locale;

@Slf4j
@Component
public class GreetingsClient {

    private final WebClient webClient;

    public GreetingsClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

    @Scheduled(fixedDelay = 1000)
    public void great() {
        this.webClient.get()
                .uri("/languages")
                .retrieve()
                .bodyToFlux(Locale.class)
                .subscribe(this::getLokalizedGreetings);
    }

    private void getLokalizedGreetings(Locale language) {
        this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hello/{language}")
                        .build(language))
                .retrieve()
                .bodyToFlux(Greetings.class)
                .subscribe(this::messageConsumer);
    }

    private void messageConsumer(Greetings greetings) {
        log.info(greetings.message());
    }
}
