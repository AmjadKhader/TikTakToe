package challenge.TikTakToe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1")
    @Setter
    private Player player1;

    @ManyToOne
    @JoinColumn(name = "player2")
    @Setter
    private Player player2;

    private String board = "---------";

    @ManyToOne
    @JoinColumn(name = "current_player")
    @Setter
    private Player currentPlayer;
    @Setter
    private boolean gameOver;
    @Setter
    private String winner;

    public synchronized void updateBoard(int row, int column, char value) {
        char[][] currentBoard = getBoard(); // Convert to 2D board
        currentBoard[row][column] = value; // update the correct value
        updateBoard(currentBoard); // Update the board

        checkGameOver(); // Do we have a winner now?
    }

    public synchronized char[][] getBoard() {
        char[][] result = new char[3][3];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = board.charAt(index);
                index++;
            }
        }
        return result;
    }

    private void updateBoard(char[][] newBoard) {
        if (newBoard == null || newBoard.length != 3 || newBoard[0].length != 3) {
            throw new IllegalArgumentException("Invalid board format");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(newBoard[i][j]);
            }
        }
        board = sb.toString();
    }

    private void checkGameOver() {
        char playerSymbol = getCurrentPlayer().getSymbol();

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (getBoard()[i][0] == playerSymbol && getBoard()[i][1] == playerSymbol && getBoard()[i][2] == (playerSymbol)) {
                setGameOver(true);
                setWinner(getCurrentPlayer().getName());
                return;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (getBoard()[0][i] == (playerSymbol) && getBoard()[1][i] == playerSymbol && getBoard()[2][i] == (playerSymbol)) {
                setGameOver(true);
                setWinner(getCurrentPlayer().getName());
                return;
            }
        }

        // Check diagonals
        if (getBoard()[0][0] == playerSymbol && getBoard()[1][1] == playerSymbol && getBoard()[2][2] == playerSymbol) {
            setGameOver(true);
            setWinner(getCurrentPlayer().getName());
            return;
        }
        if (getBoard()[0][2] == playerSymbol && getBoard()[1][1] == playerSymbol && getBoard()[2][0] == playerSymbol) {
            setGameOver(true);
            setWinner(getCurrentPlayer().getName());
        }

        // Check draw
        if (!board.contains("-")) {
            // if no - which means that all places are occupied with no winner!
            setGameOver(true);
            setWinner("A Draw!");
        }
    }

}

