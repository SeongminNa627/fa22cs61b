public class DrawTriangleMethod {
    public static void main(String[] args) {
        drawTriangle(10);
    }
    public static void drawTriangle ( int N){
        int row = 0;
        int SIZE = N;
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

