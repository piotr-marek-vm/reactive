package pl.vm.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

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

        log.info("\n\nstart great\n\n");

        this.webClient.get()
                .uri("/languages")
                .retrieve()
                .bodyToFlux(Locale.class)
                .doOnNext(this::getLokalizedGreetings)
                .doOnError(e ->  log.error(" Following problem occurred \"" + e.getMessage()+"\""))
               // .onErrorContinue((throwable, locale) -> log.error("During processing: "+ locale+ " Following problem occurred \"" + throwable.getMessage()+"\""))
               // .onErrorReturn(Locale.ENGLISH)
//                .onErrorResume(e -> Flux.empty())
                .subscribe();
    }


    private void getLokalizedGreetings(Locale language) {

        if (Locale.GERMAN.equals(language)){
            throw new IllegalArgumentException("German is not allowed");
        }
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
