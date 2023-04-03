package ch.uzh.ifi.hase.soprafs23.rest.dto;

import ch.uzh.ifi.hase.soprafs23.Grid.Grid;

public class GridGetDTO {
    private Grid board;

    public Grid getGrid(){return board;}
    public void setGrid(Grid grid){this.board  = grid;}
}
