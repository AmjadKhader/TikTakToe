package challenge.TikTakToe.request;

import lombok.Data;

@Data
public class MakeMoveRequest {
    private int row;
    private int col;
    private String playerName;
}
