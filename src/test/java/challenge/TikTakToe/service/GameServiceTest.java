package challenge.TikTakToe.service;

import challenge.TikTakToe.exception.InvalidMoveException;
import challenge.TikTakToe.exception.InvalidPlayerException;
import challenge.TikTakToe.model.Game;
import challenge.TikTakToe.model.Player;
import challenge.TikTakToe.repo.GameRepository;
import challenge.TikTakToe.repo.PlayerRepository;
import challenge.TikTakToe.response.GameResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void testCreateGame() {
        String playerName1 = "Player 1";
        String playerName2 = "Player 2";
        Player player1 = new Player(playerName1, 'X');
        Player player2 = new Player(playerName2, 'O');

        when(playerRepository.save(player1)).thenReturn(player1);
        when(playerRepository.save(player2)).thenReturn(player2);
        when(gameRepository.save(any())).thenAnswer(invocation -> {
            Game game = invocation.getArgument(0);
            game.setId(1L); // Mock the generated ID
            return game;
        });

        GameResponse createdGame = gameService.createGame(playerName1, 'X', playerName2, 'O');

        verify(playerRepository, times(2)).findByNameAndSymbol(any(), anyChar());
        verify(playerRepository, times(1)).save(player1);
        verify(playerRepository, times(1)).save(player2);
        verify(gameRepository, times(1)).save(any());
        assertEquals(player1, createdGame.getPlayer1());
        assertEquals(player2, createdGame.getPlayer2());
        assertEquals(player1, createdGame.getNext()); // Assuming player1 starts
        assertFalse(createdGame.isGameOver()); // Assuming game is not over initially
    }

    @Test
    void testGetGame() {
        Game game = new Game();
        when(gameRepository.findById(any())).thenReturn(Optional.of(game));

        GameResponse result = gameService.getGame(1L);
        assertEquals(new GameResponse(game), result);
    }

    @Test
    void testValidatePlayersDataSameSymbol() {
        assertThrows(
                InvalidPlayerException.class,
                () -> gameService.validatePlayersData("somename", 'O', "anothername", 'O')
        );
    }

    @Test
    void testValidatePlayersDataWrongSymbol() {
        assertThrows(
                InvalidPlayerException.class,
                () -> gameService.validatePlayersData("somename", 'A', "anothername", 'O')
        );
    }

    @Test
    void testValidatePlayersDataEmptyName() {
        assertThrows(
                InvalidPlayerException.class,
                () -> gameService.validatePlayersData("", 'X', "anothername", 'O')
        );
    }

    @Test
    void testValidateMoveWrongInput() {
        assertThrows(
                InvalidMoveException.class,
                () -> gameService.validateMove(new Game(), new Player("someone", 'X'), 3, 3, "someone")
        );
    }

    @Test
    void testValidateMoveWhenGameOver() {
        Game game = new Game();
        game.setGameOver(true);
        assertThrows(
                InvalidMoveException.class,
                () -> gameService.validateMove(game, new Player("someone", 'X'), 2, 2, "someone")
        );
    }

    @Test
    void testValidateMoveWrongTurn() {
        assertThrows(
                InvalidPlayerException.class,
                () -> gameService.validateMove(new Game(), new Player("someone", 'X'), 2, 2, "anotherone")
        );
    }

    @Test
    void testValidateMoveNullPlayer() {
        assertThrows(
                InvalidPlayerException.class,
                () -> gameService.validateMove(new Game(), null, 2, 2, "someone")
        );
    }

    @Test
    void testValidateMoveOccupiedCell() {
        Game game = new Game();
        game.setCurrentPlayer(new Player("someone", 'X'));
        game.updateBoard(0, 0, 'X');
        assertThrows(
                InvalidMoveException.class,
                () -> gameService.validateMove(game, new Player("someone", 'X'), 0, 0, "someone")
        );
    }

}
