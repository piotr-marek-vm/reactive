package pl.vm.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class GreetingsRestController {

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Greetings> sayHello() {

        return Mono.just(
                Greetings.builder()
                        .message("Hello, Spring!")
                        .build()

        );
    }
}
