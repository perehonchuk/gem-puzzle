package com.wix.gempuzzle;

import com.wix.gempuzzle.client.Client;
import com.wix.gempuzzle.client.DefaultClient;
import com.wix.gempuzzle.client.ui.UI;
import com.wix.gempuzzle.game.domain.BoardDto;
import com.wix.gempuzzle.game.*;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class MediumTest {
    UI ui = mock(UI.class);
    Supplier<String> userInput = mock(Supplier.class);
    Function<List<Integer>, List<Integer>> collectionsShuffler = mock(Function.class);
    BoardOperations boardOperations = new DefaultBoardOperations(collectionsShuffler);
    Game game = new DefaultGame(boardOperations);
    Client client = new DefaultClient(ui, game, userInput);

    @Test(timeout = 1000)
    public void shouldWonGame() throws Exception {
        when(collectionsShuffler.apply(anyList())).thenReturn(shuffle());
        when(userInput.get()).thenReturn("s", "z", "d", "d");

        client.playGame();

        verify(ui, times(4)).displayBoard(any(BoardDto.class));
        verify(ui, times(4)).displayInputMessage();
        verify(ui).displayWrongMoveMessage();
        verify(ui).displayUnknownMoveMessage();
        verify(ui).displayCongratulationMessage();
        verifyNoMoreInteractions(ui);
    }

    List<Integer> shuffle() {
        List<Integer> tileValues = IntStream.range(1, 17).boxed().collect(toList());
        tileValues.set(15, 15);
        tileValues.set(14, 14);
        tileValues.set(13, 0);
        return tileValues;
    }
}
