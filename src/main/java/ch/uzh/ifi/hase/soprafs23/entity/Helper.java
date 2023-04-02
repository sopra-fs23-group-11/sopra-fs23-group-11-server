package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.entity.ships.Position;
import ch.uzh.ifi.hase.soprafs23.entity.ships.Ship;
import org.mapstruct.Mapping;

import java.util.Arrays;

import java.util.HashSet;
import java.util.Set;

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

    public static boolean isInsideGrid(int[] position){
        return position[0] <10 && position[1] < 10 && position[0] >=0 && position[1] >=0;
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

    public static boolean isValidPositionForShip(Ship ship, String[] positions){
        int[] start = convertToPos(positions[0]);
        int[] end = convertToPos(positions[1]);
        int length = 0;
        if(start[0] == end[0]){// horizontal ship
            length = Math.abs(start[1] - end[1]);
        } else if(start[1] == end[1]){ // vertical ship
            length = Math.abs(start[0] - end[0]);
        }

        if(length != ship.getLength()-1){
            System.out.println("Invalid positions for a "+ ship.getType());
            return false;
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

}
