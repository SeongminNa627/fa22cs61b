package byow.Core;

import java.util.HashSet;
import java.util.HashMap;

public class Room implements Comparable{
    int x;
    int y;
    int N;
    int S;
    int W;
    int E;
    public Room(int pivotX, int pivotY, int N, int S, int W, int E){
        this.N = N;
        this.S = S;
        this.W = W;
        this.E = E;
        x = pivotX;
        y = pivotY;
    }

    @Override
    public int compareTo(Object o) {
        Room otherRoom = (Room) o;
        if((otherRoom.x > this.x) && (otherRoom.y >= this.y)){
            //The other room is on the first quadrant with respect to this room
            return 1;
        }
        if ((otherRoom.x <= this.x) && (otherRoom.y > this.y)){
            //The other room is on the first quadrant with respect to this room
            return 2;
        }
        if ((otherRoom.x < this.x) && (otherRoom.y <= this.y)){
            //The other room is on the first quadrant with respect to this room
            return 3;
        }
        if ((otherRoom.x >= this.x) && (otherRoom.y < this.y)){
            //The other room is on the first quadrant with respect to this room
            return 4;
        }
        else{
            return 0;
        }

    }
    @Override
    public boolean equals(Object that){
        return (x == ((Room) that).x) && (y == ((Room) that).y);
    }
    @Override
    public int hashCode(){
        HashMap<Integer, Integer> set = new HashMap();
        set.put(x,y);
        return set.hashCode();
    }

}
