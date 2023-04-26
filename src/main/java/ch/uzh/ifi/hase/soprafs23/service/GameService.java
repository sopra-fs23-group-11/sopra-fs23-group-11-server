package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.*;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PlayerExcep;
import ch.uzh.ifi.hase.soprafs23.exceptions.PositionExcep;
import ch.uzh.ifi.hase.soprafs23.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final PlayerRepository playerRepository;
    private final ShotRepository shotRepository;
    private final ShipPlayerRepository shipPlayerRepository;
    private final ShipRepository shipRepository;
    private final LobbyRepository lobbyRepository;
    private final GameRepository gameRepository;

    @Autowired
    public GameService(PlayerRepository playerRepository, ShotRepository shotRepository, ShipPlayerRepository shipPlayerRepository, ShipRepository shipRepository, LobbyRepository lobbyRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.shotRepository = shotRepository;
        this.shipPlayerRepository = shipPlayerRepository;
        this.shipRepository = shipRepository;
        this.lobbyRepository = lobbyRepository;
        this.gameRepository = gameRepository;
    }

    public Shot attack(long attackerId, long defenderId, String posOfShot, String gameId) { //flush
        Optional<Player> attacker = playerRepository.findById(attackerId);
        Optional<Player> defender = playerRepository.findById(defenderId);
        if (attacker.isEmpty())
            throw new EntityNotFoundExcep( "attacker doesn't exist", gameId);
        if (defender.isEmpty())
            throw new EntityNotFoundExcep("defender doesn't exist", gameId);
        if (attackerId == defenderId)
            throw new PlayerExcep("players should differ", gameId);

        if (!isValidShot(posOfShot, defender.get()))
            throw new PositionExcep("not valid shot", gameId);
        ShipPlayer ship_hit = waterORship(posOfShot, defender.get());
        Shot shotPosition = new Shot();
        if (ship_hit != null) {
            shotPosition.setHit(true);
            ship_hit.setHitParts(ship_hit.getHitParts() + 1);
            if (ship_hit.getHitParts() == ship_hit.getShip().getLength()) {
                ship_hit.setSunk(true);
                defender.get().setShipsRemaining(defender.get().getShipsRemaining() -1 );
                playerRepository.save(defender.get());
            }
            shipPlayerRepository.save(ship_hit);
        }
        else {
            shotPosition.setHit(false);

        }
        shotPosition.setAttacker(attacker.get());
        shotPosition.setDefender(defender.get());
        shotPosition.setPosition(posOfShot);
        shotRepository.save(shotPosition);
        return shotPosition;
    }


    private boolean isValidShot(String shotPosition, Player defender) {
        Shot shot = shotRepository.findByPositionAndDefender(shotPosition, defender);
        if (shot != null)
            throw new PositionExcep("This field was shot at before",defender.getGame().getId());
        if (!shotPosition.matches("[A-J][0-9]"))
            throw new PositionExcep("enter a valid position", defender.getGame().getId() );
        return true;
    }

    public boolean looserAlert(long defenderId, String gameId){
        Optional<Player> optionalPlayer= playerRepository.findById(defenderId);
        System.out.println("sha3'le -1");
        if (optionalPlayer.isEmpty())
            throw new EntityNotFoundExcep("player does not exist", gameId);
        Player defender= optionalPlayer.get();
        System.out.println("defender = " + defender);
        return defender.getShipsRemaining()==0;
    }


    private ShipPlayer waterORship(String position, Player defender) {
        for (ShipPlayer shipPlayer : defender.getShipPlayers()) {
            if (Helper.isContained(position, shipPlayer)) {
                return shipPlayer;
            }
        }
        return null;
    }

    public List<Shot> getAttackersShot(long attackerId, String gameId) {
        Optional<Player> player = playerRepository.findById(attackerId);
        if (player.isEmpty())
            throw new EntityNotFoundExcep("player not found", gameId);
        Player attacker = player.get();
        return shotRepository.findAllByAttacker(attacker);
    }

    public List<Shot> getDefendersShot(long defenderId, String gameId) {
        Optional<Player> player = playerRepository.findById(defenderId);
        if (player.isEmpty())
            throw new EntityNotFoundExcep("player not found", gameId);

        Player defender = player.get();
        return shotRepository.findAllByDefender(defender);
    }

    public Game startGame(long hostId, String code) {
        Game game = new Game();
        Optional<Lobby> optionalLobby = Optional.ofNullable(lobbyRepository.findByLobbyCode(code));
        if (optionalLobby.isEmpty())
            throw new EntityNotFoundExcep("Lobby doesn't exist", "");
        Lobby lobby = optionalLobby.get();
        game.setId(lobby.getLobbyCode());
        if (lobby.getHost() == null)
            throw new EntityNotFoundExcep("host is not present", code);
        if (lobby.getJoiner() == null)
            throw new EntityNotFoundExcep("joiner is missing", code);
        if (lobby.getHost().getId()!=hostId)
            throw new PlayerExcep("only the host can start a game", code);
        gameRepository.save(game);
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setUser(lobby.getHost());
        player2.setUser(lobby.getJoiner());
        player2.setGamePlayer2(game);
        player1.setGamePlayer1(game);
        player1.setShipsRemaining(5);
        player2.setShipsRemaining(5);
        playerRepository.save(player1);
        playerRepository.save(player2);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        gameRepository.save(game);
        return game;
    }

}
