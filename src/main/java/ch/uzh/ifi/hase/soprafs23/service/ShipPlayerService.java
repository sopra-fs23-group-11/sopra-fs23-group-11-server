package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.repository.ShipPlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ShipPlayerService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final ShipPlayerRepository shipPlayerRepository;

    @Autowired
    public ShipPlayerService(ShipPlayerRepository shipPlayerRepository) {
        this.shipPlayerRepository = shipPlayerRepository;
    }

    //ToDo
    // check if position of a shot is between the start and end position
    public boolean isContained (String shoot){

        return false; }
}
