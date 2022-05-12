import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class hw7_Q2 {
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

        //call merge sort function
        mergeSort(A, 0, n-1);

        System.out.println("The following is the list after merge sort:");
        for(int i = 0; i < n; i++)
            System.out.print(A[i] + " ");
        System.out.println();
    }

    //please complete the following function
    //sort all numbers in A[low,...,high]
    private static void mergeSort(int[] A, int low, int high)
    {

        if(low  < high){
            int mid = (high+low)/2;
            mergeSort(A, low, mid);
            mergeSort(A,mid+1,high);
            merge(A,low,mid,high);
        }
    }

    //please complete the following function
    //merge the two sorted subarrays A[low,...,mid] and A[mid+1,...,high],
    private static void merge(int A[], int low, int mid, int high) {
        int m1 = mid - low +1;  // the size of array B
        int m2 = high - mid;       // the size of array C
        int [] B = new int [m1];    // create a dynamic array of size m1
        int [] C = new int [m2];    // create a dynamic array of size m2
        // copy A[low ... mid] to B[0...m1-1];
        for(int q=0; q<m1; ++q){
            B[q] = A[low + q];
        }

        // copy A[mid+1 ... high] to C[0...m2-1];
        for(int q= 0; q < m2; ++q){
            C[q]=A[q+mid+1];
        }

        int i=0;
        int j=0;
        int k=low;
        while(i < m1  &&  j<m2){//iteratively select the smaller front elements of B and C
            if( B[i] <= C[j]) {
                A[k] = B[i];
                i++;
            }
            else {
                A[k] = C[j];
                j++;
            }
            k++;
        }
        while(i<m1){
            A[k]=B[i];
            i++;
            k++;
        }
        while(j<m2){
            A[k]= C[j];
            j++;
            k++;
        }
    }
}