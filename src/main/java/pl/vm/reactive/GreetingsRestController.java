package pl.vm.reactive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Map;

@Slf4j
@RestController
public class GreetingsRestController {

    private final Map<Locale, String> salutations = Map.of(
            Locale.forLanguageTag("en"), "Hello",
            Locale.forLanguageTag("es"), "Hola",
            Locale.forLanguageTag("fr"), "Bonjour",
            Locale.forLanguageTag("de"), "Guten tag",
            Locale.forLanguageTag("it"), "Salve",
            Locale.forLanguageTag("zh"), "Nǐn hǎo",
            Locale.forLanguageTag("pt"), "Olá",
            Locale.forLanguageTag("ar"), "Asalaam alaikum",
            Locale.forLanguageTag("ja"), "Konnichiwa",
            Locale.forLanguageTag("ko"), "Anyoung haseyo"
    );

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Greetings> sayHello() {

        return Mono.just(
                Greetings.builder()
                        .message("Hello, Spring!")
                        .build()

        );
    }

    @GetMapping(path = "/hello/{language}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Greetings> sayHello(@PathVariable("language") Locale language) {
        return Mono.just(
                Greetings.builder()
                        .message(salutations.get(language) + ", Spring!")
                        .build()

        );
    }

    @GetMapping(path = "/languages", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Locale> getLanguages() {
        return Flux.fromIterable(salutations.keySet());
    }
}
