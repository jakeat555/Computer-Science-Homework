import java.io.File;
import java.io.IOException;
import java.util.Scanner;


class Heap
{
    private int array_size;// the size of the array, index from 0 to array_size-1
    private int heap_size;// keys of the heap are in H[1...heap_size];
    private int[] H;
    private int left(int i) { return i * 2; } //the index of the left child of node i
    private int right(int i) { return 2 * i + 1; } //the index of the right child of node i
    private int parent(int i) { return i / 2; }//the index of the parent of node i

    //A is an array, num is the number of elements in A
    public Heap(int[] A, int num)
    {
        //define a constant variable as the array size
        final int ARRAY_SIZE = 100;

        array_size = ARRAY_SIZE;
        H = new int [array_size];
        for (int i = 1; i <= num; i++)
            H[i] = A[i-1]; //as the index of A starts from 0, so we use i-1 here for A

        heap_size = num;

        //call the function to construct a heap
        buildHeap();
    }

    //print out the elements of the entire heap, following their index order in the array A
    public void printHeap()
    {
        System.out.println();
        System.out.println("The following are the keys in the heap:");

        for (int i = 1; i <= heap_size; i++)
            System.out.print(H[i] + " ");

        System.out.println();
    }

    //sort all elements of the heap and still use H to store the sorted list in descending order
    public void heapSort()
    {
        int n = heap_size;
        for (int i = heap_size; i > 0; i --)
            H[i] = deleteMin();

        System.out.println();
        System.out.println("The following are the keys in the heap sorted in descending order:");

        for (int i = 1; i <= n; i++)
            System.out.print(H[i] + " ");

        System.out.println();
    }

    //please complete the following four functions
    //percolate down from H[i]
    private void percolateDown(int i)
    {
        int temp = H[i];
        int child;
        for(; 2*i <= heap_size; i = child)
        {
            child = left(i);

            if(child != heap_size && H[child]> H[child + 1])
                child++;

            if(temp>H[child])
                H[i] = H[child];
            else
                break;
        }
        H[i]=temp;
    }
    //return and remove the smallest key from the heap
    public int deleteMin()
    {
        int min = H[1];
        H[1] = H[heap_size--];
        percolateDown(1);
        return min;
    }
    //insert a new key x into the heap H
    public void insert(int x)
    {
        if(heap_size == H.length -1){
            int [] old = H;
            H =  new  int [H.length * 2];
            System.arraycopy(old, 1, H, 1, heap_size);
        }
        int pos = ++heap_size;

        //Percolate up
        for(; pos > 1 && x<H[pos/2]; pos = pos/2 )
            H[pos] = H[pos/2];

        H[pos] = x;
    }

    //build the heap for the elements in H[1...heap_size] and you can make use of the procedure percolateDown(int i), as discussed in class
    public void buildHeap()
    {
        for (int k = heap_size/2; k > 0; k--)
        {
            percolateDown(k);
        }
    }
}

public class hw6_Q1
{
    public static void main(String[] args) throws IOException
    {
        int[] A = {24, 13, 18, 31, 65, 26, 19, 68, 21, 37};

        //build a heap using the 10 elements of array A
        Heap heap = new Heap(A, 10);

        //open files
        String inputFile = "hw6_Q1_input.txt";
        //open the input file
        File myFile = new File(inputFile);
        Scanner input = new Scanner(myFile);

        String op;
        int x;

        //read operations from the input file
        while(input.hasNext())
        {
            Scanner nextLine = new Scanner(input.nextLine());
            op = nextLine.next();
            if (op.equals("insert"))
            {
                x = nextLine.nextInt(); // read the value x for insert
                heap.insert(x);
            }
            if (op.equals("deleteMin"))
            {
                int temp = heap.deleteMin();
                System.out.println(temp);
            }
        }

        //print the heap
        heap.printHeap();

        //sort all keys in the heap in descending order and print the sorted list
        heap.heapSort();

        //close the input file
        input.close();
    }
}
