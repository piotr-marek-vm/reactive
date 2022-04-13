package pl.vm.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GreetingsRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testHello() {
        webTestClient
                .get().uri("/hello")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Greetings.class)
                .value(helloWorld ->
                        assertThat(helloWorld.message()).isEqualTo("Hello, Spring!")
                );
    }

}
