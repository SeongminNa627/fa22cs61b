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
public class PlusWorld {
    public static final int WIDTH = 60;
    public static final int HEIGHT = 40;
    private static final long SEED = 2873123;
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

        addPlus(world, 0,0, 4);
        ter.renderFrame(world);
    }
    public static void addPlus(TETile[][] world, int x, int y, int s){
        for (int i = x;  i < x + 3*s; i ++){
            for (int j = y; j < y + 3*s; j ++){
                if (i < 0 || i > HEIGHT){
                    continue;
                }
                if (j < 0 || j > WIDTH){
                    continue;
                }
                if (x + s <= i && i < x + 2*s){
                    world[i][j] = Tileset.FLOWER;
                }
                else if (y + s <= j && j < y + 2*s) {
                    world[i][j] = Tileset.FLOWER;
                }
            }
        }
    }
}
