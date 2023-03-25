package byow.Core;

import java.util.TreeSet;

public class HallWay extends Edge<Room> implements Comparable<HallWay>{
    int width;

    public HallWay(Room from, Room to, int width){
        super(from, to, 0);
        this.w = Math.abs(from.x - to.x) + Math.abs(from.y - to.y) + width - 1;
        this.width = width;
    }

    @Override
    public int compareTo(HallWay that){
        return this.weight() - that.weight();
    }
    @Override
    public boolean equals(Object that){
        return (this.from.equals(((HallWay)that).from) && this.to.equals(((HallWay)that).to)) || (this.from.equals(((HallWay)that).to) && this.to.equals(((HallWay)that).from));
    }
    @Override
    public int hashCode(){
        TreeSet<Room> set = new TreeSet<Room>();
        set.add(from);
        set.add(to);
        return set.hashCode();
    }
}
