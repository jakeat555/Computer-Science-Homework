public class SelectionSort {
    public static void main(String [] args){
        int[] array = {1,5,3,5,76,3,2,45,3};
        SelectionSort(array);
        for(int x: array){
            System.out.print(x+" ");
        }
    }

    public static void SelectionSort(int[] array){
        for(int n =0; n< array.length-1; n++) {
            int minIndex = n;
            for (int i = n+1; i < array.length; i++) {
                if (array[i] < array[minIndex]) {
                    minIndex = i;
                }
            }

            //swap
            int temp = array[minIndex];
            array[minIndex] = array[n];
            array[n] = temp;
        }
    }
}
