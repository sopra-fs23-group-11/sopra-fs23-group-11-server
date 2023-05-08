package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.Cell;
import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.rest.dto.CellGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPostDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.UserPutDTO;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.CellService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CellController {
    private final CellService cellService;

    CellController(CellService cellService) {
        this.cellService = cellService;
    }

    @GetMapping("/players/{id}/cells")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CellGetDTO> getCellsFromPlayer(@PathVariable("id") long id) {
        cellService.updateCells(id);
        List<Cell> cells = cellService.getCellsById(id);
        List<CellGetDTO> cellGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (Cell cell : cells) {
            cellGetDTOs.add(DTOMapper.INSTANCE.convertEntityToCellGetDTO(cell));
        }
        return cellGetDTOs;
    }
}

