package ch.uzh.ifi.hase.soprafs23.entity.ships;

public class Position {
    private int x;
    private int y;
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Position(int x, int y) { // Position p = new Position(2,2);
        this.x = x;
        this.y = y;
    }

    public Position(String str) { // Position p = new Position("C2");
        if (str.length()!=2){
            this.x=99;
            this.y=99;
        } else{
        this.x = charToInt(str.substring(0, 1));
        this.y = charToInt(str.substring(1, 2));}
    }
    



    private int charToInt(String str) {
        return switch (str) {
            case "A", "0" -> 0;
            case "B", "1" -> 1;
            case "C", "2" -> 2;
            case "D", "3" -> 3;
            case "E", "4" -> 4;
            case "F", "5" -> 5;
            case "G", "6" -> 6;
            case "H", "7" -> 7;
            case "I", "8" -> 8;
            case "J", "9" -> 9;
            default ->
                    99;
        };
    }

    public int length() {
        String posStr = Integer.toString(x) + Integer.toString(y);
        return posStr.length();
    }


}
