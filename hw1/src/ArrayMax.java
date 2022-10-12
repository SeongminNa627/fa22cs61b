public class ArrayMax {
    public static int max(int[] arr){
//     int i = 0;
//     int max = arr[i];
//     while (i < arr.length - 1){
//         int next = arr[i + 1];
//         if (max < next){
//             max = next;
//         }
//         i = i + 1;
//         }
//     return max;
        int max = arr[0];
        for (int i = 0; i < arr.length - 1; i = i + 1){
            if (max < arr[i + 1]){
                max = arr[i + 1];
            }
        }
        return max;
    }
    public static void main(String[] args){
        int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
        System.out.println(max(numbers));
    }
}
