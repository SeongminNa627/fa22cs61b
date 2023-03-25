package byow.Core;
import java.util.Random;
import java.util.*;

public class RoomQuadTree extends QuadTree<Room,Range[][]>{
    private static RandomUtils rand;
    private static Random random;
    private HashSet<Room> rooms = new HashSet<Room>();
    private HashSet<HallWay> connection = new HashSet<HallWay>();
    private HashSet<HallWay> randomConnection = new HashSet<HallWay>();
    public RoomQuadTree(){
        super();
    }
    public RoomQuadTree(Random random, int WIDTH, int HEIGHT, int maxRoomSize){
        this.random = random;
        int x = rand.uniform(random, 6, WIDTH - 6);
        int y = rand.uniform(random, 6, HEIGHT - 6);

        int E = rand.uniform(random, 1,  4);
        int W = rand.uniform(random, 1,  4);
        int N = rand.uniform(random, 1,  4);
        int S = rand.uniform(random, 1,  4);

        Room room = new Room(x,y,N, S, W, E);

        this.random = random;
        Range[] NERange = ((Range[]) new Range[2]);
        Range[] NWRange = ((Range[]) new Range[2]);
        Range[] SWRange = ((Range[]) new Range[2]);
        Range[] SERange = ((Range[]) new Range[2]);

        int NECornerX = room.x + room.E + 1, SECornerX = room.x + room.E + 1;
        int NECornerY = room.y + room.N + 1, NWCornerY = room.y + room.N + 1;
        int NWCornerX = room.x - room.W - 1, SWCornerX = room.x - room.W - 1;
        int SECornerY = room.y - room.S - 1, SWCornerY = room.y - room.S - 1;

        NERange[0] = new Range(NECornerX, WIDTH); NERange[1] = new Range(NECornerY, HEIGHT);
        NWRange[0] = new Range(0, NWCornerX); NWRange[1] = new Range(NWCornerY, HEIGHT);
        SWRange[0] = new Range(0, SWCornerX); SWRange[1] = new Range(0, SWCornerY);
        SERange[0] = new Range(SECornerX, WIDTH); SERange[1] = new Range(0, SECornerY);

        Range[][] range = new Range[5][2];
        range[1] = NERange;
        range[2] = NWRange;
        range[3] = SWRange;
        range[4] = SERange;

        size = 1;
        root = new Node(room, range);
        this.generateRooms(maxRoomSize);
        randomConnection = getRandomConnection();
    }
    public HashSet<HallWay> getConnection(){
        return connection;
    }
    public HashSet<HallWay> getRandomConnection() {
        Set<Room> rooms = this.keySet();
        Room[] roomShuffled = rooms.toArray(new Room[this.size()]);
        RandomUtils.shuffle(random, roomShuffled);
        for (int i = 0; i < roomShuffled.length - 1; i++) {
            randomConnection.add(new HallWay(roomShuffled[i], roomShuffled[i + 1], rand.uniform(random, 1, 3)));
        }
        return randomConnection;
    }
    public HashSet<Room> getRooms(){return rooms;}
    public void print(){
        System.out.println("------------------------------------");
        for (Object r: this){
            System.out.print("(" + ((Room) r).x + ", " + ((Room) r).y + ")");
            System.out.println("--> ("+ ((Room) r).W+", " + ((Room) r).E + ", " + ((Room) r).N + ", " + ((Room) r).S + ")");
//            testWorld[((Room) r).x][((Room) r).y] = Tileset.SAND;
        }

    }

    /**
     * @param parent
     * @param child
     * @return
     * Child's subspace
     */
    private Range[][] getSubspace(Node parent, Node child){
        //parent.value --> Range[5][2]
        /**
         * Range[][] value = {
         *                      0,
         *                      1 -> {Range(fromx, tox), Range(fromy, toy)}
         *                      2 -> {Range(fromx, tox), Range(fromy, toy)}
         *                      3 -> {Range(fromx, tox), Range(fromy, toy)}
         *                      4 -> {Range(fromx, tox), Range(fromy, toy)}
         *                    }
         */

        Room pRoom = parent.key;
        Room cRoom = child.key;

        Range[][] subspace =  new Range[5][2];

        int cmp = pRoom.compareTo(cRoom);
        Range[] NERange = new Range[2];
        Range[] NWRange = new Range[2];
        Range[] SWRange = new Range[2];
        Range[] SERange = new Range[2];
        //child is in the first quadrant with respect to the parent.
        int pFromx = parent.value[cmp][0].from; //fromx of the cmp quadrant of the parent
        int pTox = parent.value[cmp][0].to; //tox of the cmp quadrant of the parent
        int pFromy = parent.value[cmp][1].from; //fromy of the cmp quadrant of the parent
        int pToy = parent.value[cmp][1].to; //toy of the cmp quadrant of the parent

        int NECornerX = cRoom.x + cRoom.E + 1, SECornerX = cRoom.x + cRoom.E + 1;
        int NECornerY = cRoom.y + cRoom.N + 1, NWCornerY = cRoom.y + cRoom.N + 1;
        int NWCornerX = cRoom.x - cRoom.W - 1, SWCornerX = cRoom.x - cRoom.W - 1;
        int SECornerY = cRoom.y - cRoom.S - 1, SWCornerY = cRoom.y - cRoom.S - 1;

        NERange[0] = new Range(NECornerX, pTox); NERange[1] = new Range(NECornerY, pToy);
        NWRange[0] = new Range(pFromx,NWCornerX); NWRange[1] = new Range(NWCornerY, pToy);
        SWRange[0] = new Range(pFromx,SWCornerX); SWRange[1] = new Range(pFromy, SWCornerY);
        SERange[0] = new Range(SECornerX, pTox); SERange[1] = new Range(pFromy, SECornerY);

        subspace[1] = NERange;
        subspace[2] = NWRange;
        subspace[3] = SWRange;
        subspace[4] = SERange;
        return subspace;
    }
    private void generateRooms(int max){
        generateHelper(this.random, this.root, max);
    }
    private void generateHelper(Random random, Node curr, int max){
        if (curr == null){
            return;
        }
        if ((!(validRange(curr.value[1][0]) && validRange(curr.value[1][1]))) &&
                (!(validRange(curr.value[2][0]) && validRange(curr.value[2][1]))) &&
                (!(validRange(curr.value[3][0]) && validRange(curr.value[3][1]))) &&
                (!(validRange(curr.value[4][0]) && validRange(curr.value[4][1])))){
            return;
        }
        if (validRange(curr.value[1][0]) && validRange(curr.value[1][1])){
            Room NEroom = randomRoom(random, curr.value[1][0].from, curr.value[1][0].to, curr.value[1][1].from, curr.value[1][1].to , max);
            rooms.add(NEroom);
            Node NEnode = new Node(NEroom, null);
            Range[][] NErange = getSubspace(curr, NEnode);
            NEnode.value = NErange;
            curr.NE = NEnode;
            connection.add(new HallWay(curr.key, NEnode.key, rand.uniform(random, 1, 3)));
            size ++;
        }
        if (validRange(curr.value[2][0]) && validRange(curr.value[2][1])){
            Room NWroom = randomRoom(random, curr.value[2][0].from, curr.value[2][0].to, curr.value[2][1].from, curr.value[2][1].to , max);
            rooms.add(NWroom);
            Node NWnode = new Node(NWroom, null);
            Range[][] NWrange = getSubspace(curr, NWnode);
            NWnode.value = NWrange;
            curr.NW = NWnode;
            connection.add(new HallWay(curr.key, NWnode.key, rand.uniform(random, 1, 3)));
            size ++;
        }
        if (validRange(curr.value[3][0]) && validRange(curr.value[3][1])){
            Room SWroom = randomRoom(random, curr.value[3][0].from, curr.value[3][0].to, curr.value[3][1].from, curr.value[3][1].to ,max );
            rooms.add(SWroom);
            Node SWnode = new Node(SWroom, null);
            Range[][] SWrange = getSubspace(curr, SWnode);
            SWnode.value = SWrange;
            curr.SW = SWnode;
            connection.add(new HallWay(curr.key, SWnode.key, rand.uniform(random, 1, 3)));
            size ++;
        }
        if (validRange(curr.value[4][0]) && validRange(curr.value[4][1])){
            Room SEroom = randomRoom(random, curr.value[4][0].from, curr.value[4][0].to, curr.value[4][1].from, curr.value[4][1].to ,max);
            rooms.add(SEroom);
            Node SEnode = new Node(SEroom, null);
            Range[][] SErange = getSubspace(curr, SEnode);
            SEnode.value = SErange;
            curr.SE = SEnode;
            connection.add(new HallWay(curr.key, SEnode.key, rand.uniform(random, 1, 3)));
            size ++;
        }
        generateHelper(random, curr.NE, max);
        generateHelper(random, curr.NW, max);
        generateHelper(random, curr.SW, max);
        generateHelper(random, curr.SE, max);
    }
    private Room randomRoom(Random random, int fromx, int tox, int fromy, int toy, int max){
        int x = rand.uniform(random, fromx + 3, tox - 3 );
        int y = rand.uniform(random, fromy + 3, toy - 3 );
        int sideN = 1;
        int sideS = 1;
        int sideW = 1;
        int sideE = 1;
        if (toy - 2 - y != 1){
            sideN = rand.uniform(random, 1, toy - 2 - y);
            if (toy - 2 - y > max){
                sideN = rand.uniform(random, 1, max);
            }
        }
        if (y - 2 - fromy != 1){
            sideS = rand.uniform(random, 1, y - 2 - fromy);
            if (y - 2 - fromy > max){
                sideS = rand.uniform(random, 1, max);
            }
        }
        if (x - 2 - fromx != 1){
            sideW = rand.uniform(random, 1, x - 2 - fromx );
            if (x - 2 - fromx > max){
                sideW = rand.uniform(random, 1, max);
            }
        }
        if (tox - 2 - x != 1){
            sideE = rand.uniform(random, 1, tox - 2  - x);
            if (tox - 2 - x > max){
                sideE = rand.uniform(random, 1, max);
            }
        }
        return new Room(x, y, sideN, sideS, sideW, sideE);
    }
    private boolean validRange(Range range){
        if (range == null){
            return false;
        }
        return range.to - range.from > 6;
    }

}
