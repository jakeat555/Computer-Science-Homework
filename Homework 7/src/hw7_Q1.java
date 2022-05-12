import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class hw7_Q1 {
    public static void main(String[] args) throws IOException {
        final int ARRAY_SIZE = 100; // the fixed size of an array we will use

        int[] A = new int[ARRAY_SIZE];

        String inputFile = "hw7_input.txt"; // the name of the input file
        File myFile = new File(inputFile);
        Scanner input = new Scanner(myFile);

        //read numbers from the input file, after the while loop all numbers are stored in A[0,...,n-1], i.e., n is the number of elements in A
        int n = 0;// the number of elements stored in A
        while(input.hasNext())
        {
            int value = input.nextInt();
            A[n] = value;
            n++;
        }
        input.close();//close the input file

        //call bubble sort function
        bubbleSort(A, n);

        System.out.println("The following is the list after bubble sort:");
        for(int i = 0; i < n; i++)
            System.out.print(A[i] + " ");
        System.out.println();
    }

    //please complete the following function
    //sort all numbers in A[0,...,n-1]
    private static void bubbleSort(int[] A, int n)
    {
        boolean isSwapped = true;
        while(isSwapped){
            isSwapped=false;
            for(int i =0; i<n-1; i++){
                if(A[i]>A[i+1]){
                    swap(A,i,i+1);
                    isSwapped=true;
                }
            }
        }
    }

    private static void swap(int[] A, int x, int y){
        int temp = A[x];
        A[x] = A[y];
        A[y]= temp;
    }
}
