public class DrawTriangle {
    public static void main(String[] args) {
//        String star = "*";
//        for (int i = 1; i<6;i = i + 1){
//            System.out.println(star);
//            star = star + "*";
//
//        }
        int row = 0;
        int SIZE = 5;
        while (row < SIZE) {
            int col = 0;
            System.out.print('*');
            while (col < row) {
                System.out.print('*');
                col = col + 1;
            }
            System.out.println();
            row = row + 1;
        }
    }
}


