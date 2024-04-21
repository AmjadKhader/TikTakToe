package challenge.TikTakToe.response;

import challenge.TikTakToe.model.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurrentBoardResponse extends MoveResponse {
    char[][] board;
    Player next;
}
