package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipPlayerRepository extends JpaRepository<ShipPlayer, Long> {
    List<ShipPlayer> findAllByPlayer(Player player);
    ShipPlayer findByShipIdAndPlayerId(Long shipId, Long playerId);

}
