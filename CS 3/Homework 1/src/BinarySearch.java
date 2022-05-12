public class BinarySearch {
    public static void main(String [] args){
        int[] array = {1,2,3,5,14,76,98,2013,3004,5043};
        System.out.println(BinarySearch(array,2342334));
        System.out.println(BinarySearch(array,3));
        System.out.println(BinarySearch(array,1002));
        System.out.println(BinarySearch(array,5043));
    }

    public  static int BinarySearch(int [] array, int search){
        return BinarySearch(array, search, 0, array.length-1);
    }

    public static int BinarySearch(int [] array, int search, int start, int end){
        if(start> end){
            return -1;
        }
        int mid = (start + end)/2;
        if(array[mid]== search){
            return mid;
        }
        else if(array[mid]>search){
            return BinarySearch(array,search,start,mid-1);
        }
        else{
            return BinarySearch(array,search, mid+1, end);
        }
    }
}
