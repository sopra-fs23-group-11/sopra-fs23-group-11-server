package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.Grid.Grid;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.Shot;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
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
/*
    @Mapping(source = "ship", target = "ship")
    @Mapping(source = "player", target = "player")
    @Mapping(source = "shipPlayerShipId", target = "shipPlayerShipId")
    @Mapping(source = "shipPlayerPlayerId", target = "shipPlayerPlayerId")
    @Mapping(source = "startPosition", target = "startPosition")
    @Mapping(source = "endPosition", target = "endPosition")
    ShipPlayer convertShipPlayerPostDTOtoEntity(ShipPlayerPostDTO shipPlayerPostDTO);

 */

    @Mapping(source = "ship", target = "ship")
    @Mapping(source = "player", target = "player")
//    @Mapping(source = "startPosition", target = "startPosition")
//    @Mapping(source = "endPosition", target = "endPosition")
    ShipPlayerGetDTO convertEntityToShipPlayerGetDTO(ShipPlayer shipPlayer);

    //ToDo: lobbymapper
    /*
    @Mapping(source = "lobbyCode", target = "lobbyCode")
    @Mapping(source = "joiner", target = "joiner")
    Lobby convertLobbyPutDTOtoEntity(LobbyPutDTO lobbyPutDTO);

     */

    @Mapping(source = "lobbyCode", target = "lobbyCode")
    LobbyGetDTO convertEntityToLobbyGetDTO(Lobby lobby);

    //@Mapping(source = "hostId", target = "hostId")
    //Lobby convertLobbyPostDTOtoEntity(LobbyPostDTO lobbyPostDTO);

    @Mapping(source = "password", target = "password")
    @Mapping(source = "username", target = "username")
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "token", target = "token")
    UserGetDTO convertEntityToUserGetDTO(User user);


    @Mapping(source = "username", target = "username")
    User convertUserPutDTOtoEntity(UserPutDTO userPutDTO);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "shipPlayers", target = "shipPlayers")
    @Mapping(source = "shotsAttack", target = "shotsAttack")
    @Mapping(source = "shotsDefend", target = "shotsDefend")
    PlayerGetDTO convertEntityToPlayerGetDTO(Player player);


    //ToDo create mapper for ShotGetDTO
}