package com.wix.gempuzzle.client;

import com.wix.gempuzzle.api.Move;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DefaultUserInputTest {
    Supplier<String> input = mock(Supplier.class);
    Supplier<Optional<Move>> moveInput = new DefaultUserInput(input);

    @Test
    public void shouldReturnUpForW() throws Exception {
        when(input.get()).thenReturn(DefaultUserInput.UP_CONSTANT_STR);

        Optional<Move> move = moveInput.get();
        assertThat(move).isPresent().isEqualTo(Optional.of(Move.UP));
    }

    @Test
    public void shouldReturnDownForS() throws Exception {
        when(input.get()).thenReturn(DefaultUserInput.DOWN_ACTION_STR);

        Optional<Move> move = moveInput.get();
        assertThat(move).isPresent().isEqualTo(Optional.of(Move.DOWN));
    }

    @Test
    public void shouldReturnLeftForA() throws Exception {
        when(input.get()).thenReturn(DefaultUserInput.LEFT_ACTION_STR);

        Optional<Move> move = moveInput.get();
        assertThat(move).isPresent().isEqualTo(Optional.of(Move.LEFT));
    }

    @Test
    public void shouldReturnRightForD() throws Exception {
        when(input.get()).thenReturn(DefaultUserInput.RIGHT_ACTION_STR);

        Optional<Move> move = moveInput.get();
        assertThat(move).isPresent().isEqualTo(Optional.of(Move.RIGHT));
    }

    @Test
    public void shouldReturnEmptyOnOtherInput() throws Exception {
        when(input.get()).thenReturn("z");
        Optional<Move> move = moveInput.get();
        assertThat(move).isNotPresent();
    }
}