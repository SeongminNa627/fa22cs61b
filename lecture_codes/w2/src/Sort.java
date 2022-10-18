public class Sort {
    public static void sort(String[] x){
        // Selection Sort (pick the smallest and throw it in front of the array, repeat the process)
        sort(x,0);

    }
    public static void sort(String[] x, int k){
        if (k == x.length){
            return;
        }
        int smallest = smallest(x,k);
        swap(x, k, smallest);
        sort(x, k + 1);
    }
    // Returns index of the smallest string
    public static int smallest(String[] x, int k){
        int smallest = k;
        for (int i = k; i < x.length ; i = i + 1){
            int cmpResult = x[i].compareTo(x[smallest]);
            if (cmpResult < 0){
                smallest = i;
            }
        }
        return smallest;
    }
    public static void swap(String[] input, int a , int b){
        String temp = input[a];
        input[a] = input[b];
        input[b] = temp;
    }
}
