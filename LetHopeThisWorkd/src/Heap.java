import com.sun.xml.internal.bind.AnyTypeAdapter;
import org.omg.CORBA.Any;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Heap<AnyType extends Comparable<AnyType>> {
    private static final int CAPACITY = 2;

    private int size;            // Number of elements in heap
    private AnyType[] heap;     // The heap array

    public Heap() {
        size = 0;
        heap = (AnyType[]) new Comparable[CAPACITY];
    }

    /**
     * Construct the binary heap given an array of items.
     */
    public Heap(AnyType[] array) {
        size = array.length;
        heap = (AnyType[]) new Comparable[array.length + 1];

        System.arraycopy(array, 0, heap, 1, array.length);//we do not use 0 index

        buildHeap();
    }

    /**
     * runs at O(size)
     */
    private void buildHeap() {
        for (int k = size / 2; k > 0; k--) {
            percolatingDown(k);
        }
    }

    private void percolatingDown(int k) {
        AnyType tmp = heap[k];
        int child;

        for (; 2 * k <= size; k = child) {
            child = 2 * k;

            if (child != size &&
                    heap[child].compareTo(heap[child + 1]) > 0) child++;

            if (tmp.compareTo(heap[child]) > 0) heap[k] = heap[child];
            else
                break;
        }
        heap[k] = tmp;
    }

    /**
     * Sorts a given array of items.
     */
    public void heapSort()
    {
        int n = size;
        for (int i = size; i > 0; i --) {
            Integer temp = (Integer) deleteMin();
            heap[i] =  (AnyType)  temp;
        }

        System.out.println();
        System.out.println("The following are the keys in the heap sorted in descending order:");

        for (int i = 1; i <= n; i++)
            System.out.print(heap[i] + " ");

        System.out.println();
    }

    /**
     * Deletes the top item
     */
    public int deleteMin() throws RuntimeException {
        if (size == 0) throw new RuntimeException();
        int min = (int) heap[1];
        heap[1] = heap[size--];
        percolatingDown(1);
        return min;
    }

    /**
     * Inserts a new item
     */
    public void insert(AnyType x) {
        if (size == heap.length - 1) doubleSize();

        //Insert a new item to the end of the array
        int pos = ++size;

        //Percolate up
        for (; pos > 1 && x.compareTo(heap[pos / 2]) < 0; pos = pos / 2)
            heap[pos] = heap[pos / 2];

        heap[pos] = x;
    }

    private void doubleSize() {
        AnyType[] old = heap;
        heap = (AnyType[]) new Comparable[heap.length * 2];
        System.arraycopy(old, 1, heap, 1, size);
    }

    public String toString() {
        String out = "";
        for (int k = 1; k <= size; k++) out += heap[k] + " ";
        return out;
    }

    public void printHeap() {
        System.out.println();
        System.out.println("The following are the keys in the heap:");

        for (int i = 1; i <= size; i++)
            System.out.print(heap[i] + " ");

        System.out.println();
    }

    public class hw6_Q1 {
        public void main(String[] args) throws IOException {
            int[] A = {24, 13, 18, 31, 65, 26, 19, 68, 21, 37};

            //build a heap using the 10 elements of array A
            Heap heap = new Heap<Integer>();

            for (int x : A) {
                heap.insert(x);
            }

            //open files
            String inputFile = "hw6_Q1_input.txt";
            //open the input file
            File myFile = new File(inputFile);
            Scanner input = new Scanner(myFile);

            String op;
            int x;

            //read operations from the input file
            while (input.hasNext()) {
                Scanner nextLine = new Scanner(input.nextLine());
                op = nextLine.next();
                if (op.equals("insert")) {
                    x = nextLine.nextInt(); // read the value x for insert
                    heap.insert(x);
                }
                if (op.equals("deleteMin")) {
                    int temp = heap.deleteMin();
                    System.out.println(temp);
                }
            }

            //print the heap
            heap.printHeap();

            //sort all keys in the heap in descending order and print the sorted list
            heapSort();

            //close the input file
            input.close();
        }
    }
}
