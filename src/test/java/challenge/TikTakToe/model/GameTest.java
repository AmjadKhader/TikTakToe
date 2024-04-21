package challenge.TikTakToe.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

class GameTest {
    @Mock
    Player player1;
    @Mock
    Player player2;
    @Mock
    Player currentPlayer;
    @InjectMocks
    Game game;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateBoard() {
        when(player1.getName()).thenReturn("somename");
        when(player1.getSymbol()).thenReturn('X');
        when(player2.getName()).thenReturn("anothername");
        when(player2.getSymbol()).thenReturn('O');
        when(currentPlayer.getName()).thenReturn("somename");
        when(currentPlayer.getSymbol()).thenReturn('X');

        game.updateBoard(0, 0, 'X');

        Assertions.assertArrayEquals(new char[][]{{'X', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}}, game.getBoard());
    }

    @Test
    void testCheckGameOverRowWin() {
        when(player1.getName()).thenReturn("somename");
        when(player1.getSymbol()).thenReturn('X');
        when(player2.getName()).thenReturn("anothername");
        when(player2.getSymbol()).thenReturn('O');
        when(currentPlayer.getName()).thenReturn("somename");
        when(currentPlayer.getSymbol()).thenReturn('X');

        game.updateBoard(0, 0, 'X');
        game.updateBoard(0, 1, 'X');
        game.updateBoard(0, 2, 'X');

        Assertions.assertEquals("somename", game.getWinner());
    }

    @Test
    void testCheckGameOverColumnWin() {
        when(player1.getName()).thenReturn("somename");
        when(player1.getSymbol()).thenReturn('X');
        when(player2.getName()).thenReturn("anothername");
        when(player2.getSymbol()).thenReturn('O');
        when(currentPlayer.getName()).thenReturn("somename");
        when(currentPlayer.getSymbol()).thenReturn('X');

        game.updateBoard(0, 0, 'X');
        game.updateBoard(1, 0, 'X');
        game.updateBoard(2, 0, 'X');

        Assertions.assertEquals("somename", game.getWinner());
    }

    @Test
    void testCheckGameOverDiagonalWin() {
        when(player1.getName()).thenReturn("somename");
        when(player1.getSymbol()).thenReturn('X');
        when(player2.getName()).thenReturn("anothername");
        when(player2.getSymbol()).thenReturn('O');
        when(currentPlayer.getName()).thenReturn("somename");
        when(currentPlayer.getSymbol()).thenReturn('X');

        game.updateBoard(0, 0, 'X');
        game.updateBoard(1, 1, 'X');
        game.updateBoard(2, 2, 'X');

        Assertions.assertEquals("somename", game.getWinner());
    }

    @Test
    void testCheckGameOverDraw() {
        when(player1.getName()).thenReturn("somename");
        when(player1.getSymbol()).thenReturn('X');
        when(player2.getName()).thenReturn("anothername");
        when(player2.getSymbol()).thenReturn('O');
        when(currentPlayer.getName()).thenReturn("somename");
        when(currentPlayer.getSymbol()).thenReturn('X');

        game.updateBoard(0, 0, 'X');
        game.updateBoard(0, 1, 'O');
        game.updateBoard(0, 2, 'X');

        game.updateBoard(1, 0, 'O');
        game.updateBoard(1, 1, 'X');
        game.updateBoard(1, 2, 'O');

        game.updateBoard(2, 0, 'X');
        game.updateBoard(2, 1, 'O');
        game.updateBoard(2, 2, 'O');

        Assertions.assertEquals("A Draw!", game.getWinner());
    }

    @Test
    void testGetBoard() {
        char[][] result = game.getBoard();
        Assertions.assertArrayEquals(new char[][]{{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}}, result);
    }
}