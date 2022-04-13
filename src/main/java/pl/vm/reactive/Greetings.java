package pl.vm.reactive;

import lombok.Builder;

public record Greetings(
        String message
) {
    @Builder
    public Greetings {
        //used for builder
    }
}
