package ch.uzh.ifi.hase.soprafs23.rest.mapper;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.entity.Player;
import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.PlayerPutDTO;
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
@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    @Mapping(source = "password", target = "password")
    @Mapping(source = "name", target = "name")
    Player convertPlayerPostDTOtoEntity(PlayerPostDTO playerPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "token", target = "token")
    PlayerGetDTO convertEntityToPlayerGetDTO(Player player);

    @Mapping(source = "password", target = "password")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "id", target = "id")
    Player convertPlayerPutDTOtoEntity(PlayerPutDTO playerPutDTO);

}
