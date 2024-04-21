package challenge.TikTakToe.request;

import lombok.Data;

@Data
public class CreateGameRequest {
    private String player1Name;
    private char player1Symbol;
    private String player2Name;
    private char player2Symbol;
}
