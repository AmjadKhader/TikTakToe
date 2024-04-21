package challenge.TikTakToe.repo;

import challenge.TikTakToe.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByNameAndSymbol(String name, char symbol);
}
