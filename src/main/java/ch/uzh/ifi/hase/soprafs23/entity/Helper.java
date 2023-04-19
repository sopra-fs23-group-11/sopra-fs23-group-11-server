package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.entity.ships.Position;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import ch.uzh.ifi.hase.soprafs23.entity.ships.ShipPlayer;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Helper {

    static Set<Character> numbers = new HashSet<>(Arrays.asList('0', '1', '2','3','4','5','6','7','8','9'));

    public static boolean isValidPosition(String start, String end){
        if(start.length()>2 || end.length() >2){
            System.out.println("Invalid position format! Start&End length should be 2, like 'A2'");
            return false;
        }
        if(start.charAt(0)>='A' && start.charAt(0)<='Z'&&end.charAt(0)>='A'&& end.charAt(0)<='Z'&&
                numbers.contains(start.charAt(1)) && numbers.contains(end.charAt(1))){
            return true;
        }
        System.out.println("Invalid position format! Start End should be correct!");
        return false;
    }


    public static boolean isInsideGrid(int x, int y){
        return x <10 && y < 10 && x >=0 && y >=0;
    }

    public static boolean isValidAttackPosition(Position pos){
        if(pos.length()>2){
            System.out.println("Limit the input length to 2, like 'A2'");
            return false;
        }
        if(pos.getX() == 99||pos.getY()==99){
            return false;
        }
        return true;
    }


    public static boolean isValidLengthForShip(ShipPlayer shipPlayer){
        Position start = new Position(shipPlayer.getStartPosition());
        Position end = new Position(shipPlayer.getEndPosition());

        int length = 0;
        if(start.getX() == end.getX()){// horizontal ship
            length = Math.abs(start.getY() - end.getY());
        } else{ // vertical ship
            length = Math.abs(start.getX() - end.getX());
        }

        if(length != shipPlayer.getShip().getLength()-1){
            System.out.println("Invalid positions for a "+  shipPlayer.getShip().getType());
            return false;
        }
        return true;
    }

    public static boolean shipIsWithinBoundary(ShipPlayer shipPlayer){
        Position start = new Position(shipPlayer.getStartPosition());
        Position end = new Position(shipPlayer.getEndPosition());
        Position[] positions = {start, end};

        for(Position position : positions){
            if(!isInsideGrid(position.getX(), position.getY())){
                return false;
            }
        }
        return true;
    }


    public static int[] convertToPos(String pos){
        int[] res = new int[2];
        res[0] = Character.getNumericValue(pos.charAt(1));
        res[1] = convertToNum(pos.charAt(0));
        return res;
    }

    public static int convertToNum(char ch){
        char num = Character.toLowerCase(ch);
        return (int)num - 97;
    }

    public static boolean isSplittable(String input){
        if(input.length() !=5){
            return false;
        }
        for (int i=0; i < input.length(); i++){
            if(input.charAt(i) == ',' & i !=2){
                return false;
            }
        }
        return input.charAt(2) ==',';
    }

    public static boolean isVertical(int[] start, int[] end){
        return start[1] == end[1];
    }

    public static boolean isContained(String shoot, ShipPlayer shipPlayer) {
        Position shootPos = new Position(shoot);
        Position start = new Position(shipPlayer.getStartPosition());
        Position end = new Position(shipPlayer.getEndPosition());

        if (shootPos.getX() >= start.getX() && shootPos.getX() <= end.getX() &&
                shootPos.getY() >= start.getY() && shootPos.getY() <= end.getY()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean allShipsPlaced(Player player){
        List<ShipPlayer> ships = player.getShipPlayers();
        if(ships.size() == 5){
            return true;
        }
        return false;
    }

    public static boolean shipsNotTouching(Player player){
        List<ShipPlayer> ships = player.getShipPlayers();
        List<Position> nonoSquare = new ArrayList<>();
        if(!ships.isEmpty()){
            for (ShipPlayer ship : ships){
                Position start = new Position(ship.getStartPosition());
                Position end = new Position(ship.getEndPosition());
                int xStartBoundary = start.getX();
                int yStartBoundary = start.getY();
                int xEndBoundary = end.getX();
                int yEndBoundary = end.getY();
                if (xStartBoundary != 0){
                    xStartBoundary--;
                }
                if (yStartBoundary != 0){
                    yStartBoundary--;
                }
                if (xEndBoundary!=10){
                    xEndBoundary++;
                }
                if (yEndBoundary!=10){
                    yEndBoundary++;
                }
                for(int x = xStartBoundary; x <= xEndBoundary; x++){
                    for (int y = yStartBoundary; y <= yEndBoundary; y++){
                        Position position = new Position(x, y);
                        if(nonoSquare.contains(position)){
                            return false;
                        }else{
                            nonoSquare.add(position);
                        }
                    }
                }
            }
        }
        return true;
    }

}
