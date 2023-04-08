package ch.uzh.ifi.hase.soprafs23.repository;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipRepository extends JpaRepository<Ship, Long> {
}
