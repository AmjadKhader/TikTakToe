package challenge.TikTakToe.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WinnerResponse extends MoveResponse {
    String winner;
    char[][] board;
}
