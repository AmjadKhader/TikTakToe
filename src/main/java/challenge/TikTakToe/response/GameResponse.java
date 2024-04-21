package challenge.TikTakToe.response;

import challenge.TikTakToe.model.Game;
import challenge.TikTakToe.model.Player;
import lombok.Getter;

import java.util.Objects;

@Getter
public class GameResponse {
    private final Long id;
    private final Player player1;
    private final Player player2;
    private final char[][] board;
    private final Player next;
    private final boolean gameOver;
    private final String winner;

    public GameResponse(Game game) {
        this.id = game.getId();
        this.player1 = game.getPlayer1();
        this.player2 = game.getPlayer2();
        this.board = game.getBoard();
        this.next = game.getCurrentPlayer();
        this.gameOver = game.isGameOver();
        this.winner = game.getWinner();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameResponse that = (GameResponse) o;
        return isGameOver() == that.isGameOver() && Objects.equals(getPlayer1(), that.getPlayer1()) && Objects.equals(getPlayer2(), that.getPlayer2()) && Objects.deepEquals(getBoard(), that.getBoard()) && Objects.equals(getNext(), that.getNext()) && Objects.equals(getWinner(), that.getWinner());
    }
}
