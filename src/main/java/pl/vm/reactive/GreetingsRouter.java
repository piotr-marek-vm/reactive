package pl.vm.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
@EnableScheduling
public class GreetingsRouter {

    @Bean
    public RouterFunction<ServerResponse> route(GreetingsHandler greetingsHandler) {
        return RouterFunctions.route(
                GET("/hello"),
                greetingsHandler::sayHello
        );
    }
}
