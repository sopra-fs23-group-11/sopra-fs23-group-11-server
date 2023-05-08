package ch.uzh.ifi.hase.soprafs23.controller;
import ch.uzh.ifi.hase.soprafs23.entity.Cell;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.exceptions.EntityNotFoundExcep;
import ch.uzh.ifi.hase.soprafs23.rest.dto.CellGetDTO;
import ch.uzh.ifi.hase.soprafs23.service.CellService;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import ch.uzh.ifi.hase.soprafs23.service.ShipPlayerService;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CellController.class)
public class CellControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CellService cellService;
    @MockBean
    private ShipPlayerService shipPlayerService;

    @MockBean
    private GameService gameService;

    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;



    //F6
    @Test
    public void getCellsTest_InValidInput() throws Exception {
        List<Cell> cells= new ArrayList<>();
        Cell cell= new Cell();
        cell.setOccupyingShip(null);
        cell.setId("A1");
        cell.setIsShotAt(false);
        cells.add(cell);

        given(cellService.getCells()).willThrow(new EntityNotFoundExcep("Player not found","ID"));
        MockHttpServletRequestBuilder getRequest = get("/players/1/cells");
        mockMvc.perform(getRequest)
                .andExpect(status().isNotFound());
    }
//F7
    @Test
    public void getCellsTest_ValidInput() throws Exception {
        List<Cell> cells= new ArrayList<>();
        Cell cell= new Cell();
        cell.setOccupyingShip(null);
        cell.setId("A1");
        cell.setIsShotAt(false);
        cells.add(cell);

        List<CellGetDTO> cellGetDTOS=new ArrayList<>();
        CellGetDTO cellGetDTO=new CellGetDTO();
        cellGetDTO.setOwnerId(1L);
        cellGetDTO.setShotAt(false);
        cellGetDTO.setOccupyingShip(null);

        given(cellService.getCells()).willReturn(cells);

        MockHttpServletRequestBuilder getRequest = get("/players/1/cells");
        mockMvc.perform(getRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].occupyingShip", is(cellGetDTO.getOccupyingShip())))
                .andExpect(jsonPath("$[0].shotAt", is(cellGetDTO.isShotAt())));


    }
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("The request body could not be created.%s", e.toString()));
        }
    }
}

