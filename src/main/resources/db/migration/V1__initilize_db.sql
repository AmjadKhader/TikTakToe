CREATE TABLE IF NOT EXISTS players (
    id                  SERIAL PRIMARY KEY,
    name                VARCHAR(255),
    symbol              VARCHAR(1)
);

CREATE TABLE IF NOT EXISTS games (
    id                  SERIAL PRIMARY KEY,
    player1             SERIAL,
    player2             SERIAL,
    current_player      SERIAL,
    board               VARCHAR(9),
    game_over           BOOLEAN,
    winner              VARCHAR(255)
);

ALTER TABLE games
    ADD CONSTRAINT FK_player1        FOREIGN KEY (player1)        REFERENCES players(id),
    ADD CONSTRAINT FK_player2        FOREIGN KEY (player2)        REFERENCES players(id),
    ADD CONSTRAINT FK_current_player FOREIGN KEY (current_player) REFERENCES players(id);
