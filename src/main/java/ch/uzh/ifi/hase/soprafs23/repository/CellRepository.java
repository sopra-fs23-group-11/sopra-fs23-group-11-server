package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("cellRepository")
public interface CellRepository extends JpaRepository<Cell, Long> {
    List<Cell> findAllByOwnerId(Long ownerId);
}