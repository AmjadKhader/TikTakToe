package challenge.TikTakToe.controller;

import challenge.TikTakToe.exception.InvalidMoveException;
import challenge.TikTakToe.request.CreateGameRequest;
import challenge.TikTakToe.request.MakeMoveRequest;
import challenge.TikTakToe.response.GameResponse;
import challenge.TikTakToe.response.MoveResponse;
import challenge.TikTakToe.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tiktaktoe")
class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games")
    public ResponseEntity<GameResponse> createGame(@RequestBody CreateGameRequest request) {
        return new ResponseEntity<>(gameService.createGame(
                request.getPlayer1Name(),
                request.getPlayer1Symbol(),
                request.getPlayer2Name(),
                request.getPlayer2Symbol()), HttpStatus.OK);
    }

    @PostMapping("/games/{gameId}/moves")
    public ResponseEntity<MoveResponse> makeMove(@PathVariable Long gameId, @RequestBody MakeMoveRequest request) throws InvalidMoveException {
        return new ResponseEntity<>(gameService.makeMove(
                gameId,
                request.getRow(),
                request.getCol(),
                request.getPlayerName()), HttpStatus.OK);
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<GameResponse> getGameStatus(@PathVariable Long gameId) {
        return new ResponseEntity<>(gameService.getGame(gameId), HttpStatus.OK);
    }
}
