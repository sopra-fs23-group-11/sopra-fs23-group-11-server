package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.WebSockets.Message.ChatMessage;
import ch.uzh.ifi.hase.soprafs23.WebSockets.Message.ShotMessage;
import ch.uzh.ifi.hase.soprafs23.entity.*;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically
 * transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g.,
 * UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for
 * creating information (POST).
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);


    default ShipPlayerGetDTO convertEntityToShipPlayerGetDTO(ShipPlayer shipPlayer){
        ShipPlayerGetDTO shipPlayerGetDTO= new ShipPlayerGetDTO();
        shipPlayerGetDTO.setId(shipPlayer.getId());
        shipPlayerGetDTO.setPlayerId(shipPlayer.getPlayer().getId());
        shipPlayerGetDTO.setShipId(shipPlayer.getShip().getId());
        shipPlayerGetDTO.setStartPosition(shipPlayer.getStartPosition());
        shipPlayerGetDTO.setEndPosition(shipPlayer.getEndPosition());
        return shipPlayerGetDTO;
    }

    default LobbyGetDTO convertEntityToLobbyGetDTO(Lobby lobby){
        LobbyGetDTO lobbyGetDTO= new LobbyGetDTO();
        lobbyGetDTO.setLobbyCode(lobby.getLobbyCode());
        lobbyGetDTO.setHostId(lobby.getHost().getId());
        lobbyGetDTO.setHostName(lobby.getHost().getUsername());
        if (lobby.getJoiner()==null){
            lobbyGetDTO.setJoinerId(-1L);
            lobbyGetDTO.setJoinerName(null);
        }else {
            lobbyGetDTO.setJoinerId(lobby.getJoiner().getId());
            lobbyGetDTO.setJoinerName(lobby.getJoiner().getUsername());

        }
        return lobbyGetDTO;
    }

    default GameGetDTO convertEntityToGameGetDTO (Game game){
        GameGetDTO gameGetDTO= new GameGetDTO();
        gameGetDTO.setAttackerName(game.getPlayer1().getUser().getUsername());
        gameGetDTO.setDefenderName(game.getPlayer2().getUser().getUsername());
        gameGetDTO.setLobbyCode(game.getId());
        return gameGetDTO;
    }

    default ShotMessage convertEntityToShotMessage (Shot shot){
        ShotMessage shotMessage= new ShotMessage();
        shotMessage.setAttackerId(shot.getAttacker().getId());
        shotMessage.setDefenderId(shot.getDefender().getId());
        shotMessage.setPosOfShot(shot.getPosition());
        shotMessage.setHit(shot.isHit());
        return shotMessage;
    }


    @Mapping(source = "password", target = "password")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "avatar", target = "avatar")
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "totalWins", target = "totalWins")
    @Mapping(source = "avatar", target = "avatar")
    UserGetDTO convertEntityToUserGetDTO(User user);

    @Mapping(source = "lobbyCode", target = "lobbyCode")
    @Mapping(source = "content", target = "content")
    ChatMessage convertChatMessageDTOtoEntity(ChatMessageDTO chatMessage);



    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);




    @Mapping(source = "id", target = "id")
    @Mapping(source = "shipPlayers", target = "shipPlayers")
    @Mapping(source = "shotsAttack", target = "shotsAttack")
    @Mapping(source = "shotsDefend", target = "shotsDefend")
    @Mapping(source = "shipsRemaining", target = "shipsRemaining")
    PlayerGetDTO convertEntityToPlayerGetDTO(Player player);


    default CellGetDTO convertEntityToCellGetDTO (Cell cell){
        CellGetDTO cellGetDTO = new CellGetDTO();
        cellGetDTO.setOccupyingShip(cell.getOccupyingShip());
        cellGetDTO.setShotAt(cell.getIsShotAt());
        cellGetDTO.setId(cell.getId());
        cellGetDTO.setOwnerId(cell.getOwnerId());

        return cellGetDTO;
    }



}