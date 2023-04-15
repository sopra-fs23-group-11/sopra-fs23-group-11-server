package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShotRepository  extends JpaRepository<Shot, Long> {
    Shot findByPositionAndDefender(String shotPosition, Player defender);
    List<Shot> findAllByAttacker(Player attacker);
    List<Shot> findAllByDefender(Player defender);
}
