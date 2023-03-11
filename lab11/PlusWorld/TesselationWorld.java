package PlusWorld;
import org.junit.Test;
import static org.junit.Assert.*;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class TesselationWorld {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 30;
    public static final int size = 2;
    private static final long SEED = 287313;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args){
        TERenderer ter = new TERenderer();
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                world[x][y] = Tileset.NOTHING;
            }
        }
        tesselate(world, 1);
        ter.renderFrame(world);
    }

    public static void addRandomPlus(TETile[][] world, int x, int y, int s){
        TETile tile = randomTile();
        for (int i = x;  i < x + 3*s; i ++){
            for (int j = y; j < y + 3*s; j ++){
                if (i < 0 || i >= WIDTH || j < 0 || j >= HEIGHT){
                    continue;
                }
                if (x + s <= i && i < x + 2*s){
                    world[i][j] = tile;
                }
                else if (y + s <= j && j < y + 2*s) {
                    world[i][j] = tile;
                }
            }
        }
    }

    public static void tesselate(TETile[][] world, int s){
        int[] factor = new int[]{0,2,-1,1,-2};
        for (int i = 0; i < factor.length; i++){
            for (int Y = 0 + i*s ; Y < HEIGHT; Y = Y + 5*s){
                for (int X = factor[i]*s; X < WIDTH; X = X + 5*s){
                    addRandomPlus(world, X, Y, s);
                }
            }
        }
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.FLOOR;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.WATER;
            case 4: return Tileset.TREE;
            case 5: return Tileset.SAND;
        }
        return Tileset.NOTHING;
    }
}
