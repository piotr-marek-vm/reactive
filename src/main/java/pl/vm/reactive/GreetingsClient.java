package pl.vm.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class GreetingsClient {

    private final WebClient webClient;

    public GreetingsClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://localhost:8080").build();
    }

    @Scheduled(fixedDelay = 1000)
    public void printGreetings() {
        this.webClient.get()
                .uri("/hello")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Greetings.class)
                .subscribe(this::messageConsumer);
    }


    private void messageConsumer(Greetings greetings) {
        log.info(greetings.message());
    }
}
