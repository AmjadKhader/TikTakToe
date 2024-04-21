package challenge.TikTakToe.repo;

import challenge.TikTakToe.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
