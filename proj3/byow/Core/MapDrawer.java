package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import java.util.*;


public class MapDrawer {
    private static int WIDTH = 80;
    private static int HEIGHT = 50;
    private static RandomUtils rand;
    private Random random;
    private TETile[][] world;
    private Room[] rooms;
    private RoomQuadTree roomTree;
    public MapDrawer(TETile[][] world, Random rand, RoomQuadTree qdTree){
        this.random = rand;
        this.world = world;
        this.roomTree = qdTree;
    }
    public TETile[][] getWorld(){
        return this.world;
    }

    public Room[] getRooms(){
        RandomUtils.shuffle(random, rooms);
        return rooms;
    }

    public void drawRooms(){
        for (Object r: this.roomTree){
            drawRoom((Room) r);
        }
        rooms = this.roomTree.keySet().toArray(new Room[this.roomTree.size()]);
    }
    private void drawRoom(Room r){

        for (int x = r.x - r.W; x <= r.x + r.E; x ++){
            for (int y = r.y - r.S; y <= r.y + r.N; y ++){
                this.world[x][y] = Tileset.FLOOR;
            }
        }
        for (int x = r.x - r.W - 1; x <= r.x + r.E + 1; x++){
            this.world[x][r.y + r.N + 1] = Tileset.WALL;
            this.world[x][r.y - r.S - 1] = Tileset.WALL;
        }
        for (int y = r.y - r.S - 1; y <= r.y + r.N + 1; y++){
            this.world[r.x + r.E + 1][y] = Tileset.WALL;
            this.world[r.x - r.W - 1][y] = Tileset.WALL;

        }
    }
    private void drawHallWay(Room r1, Room r2, int WIDTH){
        int cmp = r1.compareTo(r2);
        int xorient = 0; int yorient = 0;
        if (cmp == 1){
            xorient = 1; yorient = 1;
        } else if (cmp == 2){
            xorient = -1; yorient = 1;
        } else if (cmp == 3){
            xorient = -1; yorient = -1;
        } else if (cmp == 4){
            xorient = 1; yorient = -1;
        }
        //horizontal section
        for (int x = r1.x; (xorient) * x <= (xorient) * (r2.x); x = x + xorient){
            for (int i = 0; i < WIDTH; i ++){
                world[x][r1.y + i] = Tileset.FLOOR;
            }
            if (world[x][r1.y + WIDTH].equals(Tileset.NOTHING)){
                world[x][r1.y + WIDTH] = Tileset.WALL;
            }
            if (world[x][r1.y - 1].equals(Tileset.NOTHING)){
                world[x][r1.y - 1] = Tileset.WALL;}
        }
        //vertical section
        for (int y = r1.y; (yorient) * y <= (yorient) * (r2.y); y = y + yorient){
            for (int i = 0; i < WIDTH; i ++) {
                world[r2.x + i][y] = Tileset.FLOOR;
            }
            if (world[r2.x + WIDTH][y].equals(Tileset.NOTHING)){
                world[r2.x + WIDTH][y] = Tileset.WALL;
            }

            if (world[r2.x - 1][y].equals(Tileset.NOTHING)){
                world[r2.x - 1][y]= Tileset.WALL;
            }
        }
        //turning section
        for (int i = 0; i < WIDTH; i++){
            if (world[r2.x + xorient + i][r1.y - yorient].equals(Tileset.NOTHING)){
                world[r2.x + xorient + i][r1.y - yorient] = Tileset.WALL;
            }
        }
        for (int i = 0; i < WIDTH; i++){
            if (world[r2.x + xorient][r1.y - yorient + i].equals(Tileset.NOTHING)){
                world[r2.x + xorient][r1.y - yorient + i] = Tileset.WALL;
            }
        }

    }
    public void drawRandomHallWays(){
        Set<HallWay> edges = this.roomTree.getRandomConnection();
        for (HallWay edge: edges){
            drawHallWay(edge.from, edge.to, edge.width);
        }
    }
    public void drawHallWays(){
        Set<HallWay> edges = this.roomTree.getConnection();
        for (HallWay edge: edges){
            drawHallWay(edge.from, edge.to, edge.width);
        }
    }
}
