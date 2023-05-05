
package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import ch.uzh.ifi.hase.soprafs23.entity.Helper;
/*
@Service
@Transactional
public class PlayerService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final PlayerRepository playerRepository;
    private final Helper helper;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, Helper helper) {
        this.playerRepository = playerRepository;
        this.helper = helper;
    }

    public List<Player> getPlayers() {
        return this.playerRepository.findAll();
    }


package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import ch.uzh.ifi.hase.soprafs23.entity.Helper;

@Service
@Transactional
public class PlayerService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final PlayerRepository playerRepository;
    private final Helper helper;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, Helper helper) {
        this.playerRepository = playerRepository;
        this.helper = helper;
    }

    public List<Player> getPlayers() {
        return this.playerRepository.findAll();
    }


    public Player getField(long id) {
        Player playerById = playerRepository.getOne(id);
        return playerById;
    }

*/


