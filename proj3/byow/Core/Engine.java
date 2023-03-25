package byow.Core;

import byow.InputDemo.KeyboardInputSource;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.*;
import java.awt.*;
import java.util.*;
import java.io.*;


public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;
    public Avatar a;
    public int seed;
    public Room start;
    public Room end;
    public Random random;
    public RoomQuadTree qdTree;
    public MapDrawer md;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */

    public void interactWithKeyboard() {
        drawMainMenu();
        KeyboardInputSource inputSource = new KeyboardInputSource();
        StringBuilder sb = new StringBuilder();
        char keyTyped = Character.toUpperCase(inputSource.getNextKey());
        while (keyTyped != 'Q') {
            TETile[][] finalWorldFrame = worldInitialize(this.WIDTH, this.HEIGHT);
            if (keyTyped == 'N') {
                while (keyTyped != 'S') {
                    sb.append(keyTyped);
                    drawFrame(sb.toString());
                    keyTyped = Character.toUpperCase(inputSource.getNextKey());
                }
                sb.append('S');
                finalWorldFrame = interactWithInputString(sb.toString());
                try {
                    File save = new File("saved.txt");
                    save.createNewFile();
                    FileWriter myWriter = new FileWriter(save, false);
                    myWriter.write(sb.toString());
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            else if (keyTyped == 'L') {
                StringBuilder source = new StringBuilder("L");
                try {
                    File saved = new File("saved.txt");
                    Scanner scanner = new Scanner(saved);
                    while (scanner.hasNext()){
                        source.append(scanner.nextLine());
                    }
                    scanner.close();
                } catch (Exception e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                System.out.println(source);
                finalWorldFrame = interactWithInputString(source.toString());
            }
            sb = new StringBuilder();
            while (true){
                ter.renderLightFrame(finalWorldFrame, a, keyTyped);
                if (StdDraw.hasNextKeyTyped()){
                    if (gameOver(a, end, keyTyped)){
                        break;
                    }
                    if (keyTyped == ':'){
                        if (Character.toUpperCase(StdDraw.nextKeyTyped()) == 'Q'){
                            sb.append('Q');
                            break;
                        }
                    }
                    keyTyped = Character.toUpperCase(StdDraw.nextKeyTyped());
                    keyProcessor(finalWorldFrame, keyTyped, a);
                    sb.append(keyTyped);
                }
            }
            try {
                File save = new File("saved.txt");
                FileWriter myWriter = new FileWriter(save, true);
                myWriter.write(sb.toString());
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            break;
        }

        System.out.println(sb);
        System.out.println();
        drawFrame("Great Job! You Made it! :)");


    }
    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, running both of these:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] interactWithInputString(String input) {
        TETile[][] finalWorldFrame = worldInitialize(WIDTH, HEIGHT);
        this.seed = getSeed(input);
        this.random = new Random(seed);
        this.qdTree = new RoomQuadTree(random, WIDTH, HEIGHT, 5);
        this.md = new MapDrawer(finalWorldFrame, random, qdTree);
        this.md.drawRooms();
        this.md.drawRandomHallWays();
        finalWorldFrame = md.getWorld();
        Room[] arrOfRooms = md.getRooms();
        this.start = arrOfRooms[0];
        this.end = arrOfRooms[1];
        this.a = new Avatar(start.x, start.y);
        finalWorldFrame[start.x][start.y] = Tileset.AVATAR;
        finalWorldFrame[end.x][end.y] = Tileset.UNLOCKED_DOOR;
        if (Character.toUpperCase(input.charAt(0)) == 'N'){
            return finalWorldFrame;
        }
        else if (Character.toUpperCase(input.charAt(0)) == 'L'){
            for (int i = Integer.toString(seed).length(); i < input.length() - 2; i++){
                keyProcessor(finalWorldFrame, input.charAt(i), a);
            }
            return finalWorldFrame;
        }
        return finalWorldFrame;
    }
    public  void keyProcessor(TETile[][] finalWorldFrame, char key, Avatar a){
        if (key == 'W' && validMove(finalWorldFrame, a, 'W')){
            finalWorldFrame[a.x][a.y] = Tileset.FLOOR;
            a.moveUp();
            finalWorldFrame[a.x][a.y] = Tileset.AVATAR;
        }
        else if (key == 'S' && validMove(finalWorldFrame,a, 'S')){
            finalWorldFrame[a.x][a.y] = Tileset.FLOOR;
            a.moveDown();
            finalWorldFrame[a.x][a.y] = Tileset.AVATAR;
        }
        else if (key == 'A' && validMove(finalWorldFrame,a, 'A')){
            finalWorldFrame[a.x][a.y] = Tileset.FLOOR;
            a.moveLeft();
            finalWorldFrame[a.x][a.y] = Tileset.AVATAR;
        }
        else if (key == 'D' && validMove(finalWorldFrame,a, 'D')){
            finalWorldFrame[a.x][a.y] = Tileset.FLOOR;
            a.moveRight();
            finalWorldFrame[a.x][a.y] = Tileset.AVATAR;
        }
//        else if (keyTyped == 'C'){
//            ter.renderFrame(finalWorldFrame);
//            return;
//        }
//        ter.renderLightFrame(finalWorldFrame, a);

    }
    private int getSeed(String input){
        StringBuilder sb = new StringBuilder("");
        int start;
        if (input.charAt(0) == 'L'){
            start = 2;
        }
        else{
            start = 1;
        }
        for (int i = start; i < input.length(); i++){
            if (Character.toUpperCase(input.charAt(i)) == 'S'){
                break;
            }
            sb.append(input.charAt(i));
            System.out.println(sb);
        }

        return Integer.parseInt(sb.toString());
    }
    public void drawFrame(String s) {
        /* Take the input string S and display it at the center of the screen,
         * with the pen settings given below. */
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        Font fontBig = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.WIDTH / 2, this.HEIGHT / 2, s);
        StdDraw.show();
    }
    private boolean validMove(TETile[][] world, Avatar a, char dir ){
        if (dir == 'W' && world[a.x][a.y + 1].equals(Tileset.FLOOR)){
            return true;
        }if (dir == 'S' && world[a.x][a.y - 1].equals(Tileset.FLOOR)){
            return true;
        }if (dir == 'A' && world[a.x - 1][a.y].equals(Tileset.FLOOR)){
            return true;
        }if (dir == 'D' && world[a.x + 1][a.y].equals(Tileset.FLOOR)){
            return true;
        }
        return false;
    }
    private void drawMainMenu(){
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);

        Font fontBig = new Font("Monaco", Font.BOLD, 30);

        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(fontBig);
        StdDraw.text(this.WIDTH/2, this.HEIGHT/2, "New Game (N)");
        StdDraw.text(this.WIDTH/2, this.HEIGHT/2 - 3, "Load Game (L)");
        StdDraw.text(this.WIDTH/2, this.HEIGHT/2 - 6, "Quit (Q)");
        StdDraw.show();
    }
    private TETile[][] worldInitialize(int WIDTH, int HEIGHT){
        this.ter.initialize(WIDTH, HEIGHT);
        TETile[][] finalWorldFrame;
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x ++){
            for (int y = 0; y < HEIGHT; y ++){
                finalWorldFrame[x][y] = Tileset.NOTHING;

            }
        }
        return finalWorldFrame;
    }
    private boolean gameOver(Avatar character, Room destination, char dir){
        if (dir == 'W'){
            return character.x == destination.x && character.y + 1 == destination.y;
        }
        if (dir == 'S'){
            return character.x == destination.x && character.y - 1 == destination.y;
        }
        if (dir == 'A'){
            return character.x - 1 == destination.x && character.y == destination.y;
        }
        if (dir == 'D'){
            return character.x + 1 == destination.x && character.y == destination.y;
        }
        return false;
    }
}
