package challenge.TikTakToe.service;

import challenge.TikTakToe.exception.InvalidMoveException;
import challenge.TikTakToe.exception.InvalidPlayerException;
import challenge.TikTakToe.model.Game;
import challenge.TikTakToe.model.Player;
import challenge.TikTakToe.repo.GameRepository;
import challenge.TikTakToe.repo.PlayerRepository;
import challenge.TikTakToe.response.CurrentBoardResponse;
import challenge.TikTakToe.response.GameResponse;
import challenge.TikTakToe.response.MoveResponse;
import challenge.TikTakToe.response.WinnerResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    public GameService(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public synchronized GameResponse createGame(String player1Name, char player1Symbol, String player2Name, char player2Symbol) {

        validatePlayersData(player1Name, player1Symbol, player2Name, player2Symbol);

        // If the player is already created, get it, else create a new player ...
        Player player1 = playerRepository.findByNameAndSymbol(player1Name, player1Symbol).orElse(null);
        Player player2 = playerRepository.findByNameAndSymbol(player2Name, player2Symbol).orElse(null);

        if (Objects.isNull(player1)) {
            player1 = new Player(player1Name, player1Symbol);
            player1 = playerRepository.save(player1);
        }

        if (Objects.isNull(player2)) {
            player2 = new Player(player2Name, player2Symbol);
            player2 = playerRepository.save(player2);
        }

        Game game = new Game();
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setCurrentPlayer(player1);

        return new GameResponse(gameRepository.save(game));
    }

    @Transactional
    public synchronized MoveResponse makeMove(Long gameId, int row, int col, String playerName) {
        Game game = gameRepository.findById(gameId).orElseThrow(); // Get current game status
        Player expectedPlayer = game.getCurrentPlayer(); // Hold the expected next player data

        validateMove(game, expectedPlayer, row, col, playerName); // Is this move a valid move?

        game.updateBoard(row, col, expectedPlayer.getSymbol()); // commit move anc check for winner

        if (!game.isGameOver()) {
            game.setCurrentPlayer(game.getPlayer1() == (expectedPlayer) ? game.getPlayer2() : game.getPlayer1());

            gameRepository.save(game);
            return new CurrentBoardResponse(game.getBoard(), game.getCurrentPlayer());
        }

        gameRepository.save(game);
        return new WinnerResponse(game.getWinner(), game.getBoard());
    }

    public GameResponse getGame(Long gameId) {
        return new GameResponse(gameRepository.findById(gameId).orElseThrow());
    }

    // Public for testing ...
    public void validateMove(Game game, Player expectedPlayer, int row, int col, String playerName) {
        if (row > 2 || row < 0 || col < 0 || col > 2) {
            throw new InvalidMoveException("Invalid Cell index!");
        }
        if (game.isGameOver()) {
            throw new InvalidMoveException("Game is already over!");
        }
        if (expectedPlayer == null) {
            throw new InvalidPlayerException("Player name is not correct");
        }
        if (!expectedPlayer.getName().equals(playerName)) {
            throw new InvalidPlayerException("Not your turn!");
        }
        if (game.getBoard()[row][col] != 45) { // 45 = '-'
            throw new InvalidMoveException("Cell already occupied!");
        }
    }

    // Public for testing ...
    public void validatePlayersData(String player1Name, char player1Symbol, String player2Name, char player2Symbol) {
        player1Symbol = Character.toUpperCase(player1Symbol);
        player2Symbol = Character.toUpperCase(player2Symbol);

        if (player1Symbol == player2Symbol) {
            throw new InvalidPlayerException("Each player must have a unique symbol");
        }

        if (player1Name.equals(player2Name)) {
            throw new InvalidPlayerException("Player can't play with himself");
        }

        validatePlayer(player1Name, player1Symbol);
        validatePlayer(player2Name, player2Symbol);
    }

    private void validatePlayer(String playerName, char playerSymbol) {
        if (playerName.isBlank()) {
            throw new InvalidPlayerException("Player name can't be empty");
        }

        if (!(playerSymbol == 'X' || playerSymbol == 'O')) {
            throw new InvalidPlayerException("Player " + playerName + " Symbol is invalid");
        }
    }
}
