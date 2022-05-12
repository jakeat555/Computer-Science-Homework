import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Element{
    int key;
    Element next;
    Element(int key_input){
        key = key_input;
        next = null;
    }
}

class HashTable  //separate chaining
{
    private int m; // the size of the table
    private Element[] T; //the hash table
    private int hash(int x) // the hash function
    {
        return x % m;
    }

    public HashTable(int size) // create an empty table
    {
        m = size;
        T = new Element[m];

        for(int i = 0; i <= m - 1; i++)
            T[i] = null;
    }
    public void printTable()//print out all elements of the table
    {
        System.out.println("The following is the current hash table:");
        for(int i = 0; i < m; i++) {
            System.out.print("Entry " + i + ":  ");
            Element p = T[i];
            while (p != null) {
                System.out.print(p.key);
                if (p.next != null)
                    System.out.print("->");
                p = p.next;
            }
            System.out.println();
        }
    }

    //please complete the following three functions
    public void insert(int x)// insert a new element with key x
    {
        int k = hash(x);
        Element q = new Element(x);
        q.next=T[k];
        T[k]=q;
    }
    public void remove(int x)// remove an element whose key is x
    {
        if(search(x)) {
            int k = hash(x);
            Element head = T[k];
            Element parent = T[k];
            Element pre = T[k];
            if(head.key==x){
                T[k]=head.next;
            }
            else {
                while (parent != null) {
                    if(parent.key==x){
                        pre.next=parent.next;
                    }
                    else{
                        pre=parent;
                    }
                    parent = parent.next;
                }
            }
        }
    }
    public boolean search(int x)// search an element whose key is x and return true if it is found
    {
        int k= hash(x);
        Element p =T[k];
        while (p!=null && p.key!=x){
            p=p.next;
        }
        if(p==null){
            return false;
        }
        else
            return true;
    }
}

public class hw5_Q1 {
    public static void main(String[] arg) throws IOException
    {
        String inputFile = "hw5_Q1_input.txt"; // input file

        //open the input file
        File myFile = new File(inputFile);
        Scanner input = new Scanner(myFile);

        //read the table size, which is the number in the first line of the input file
        Scanner nextLine = new Scanner(input.nextLine());
        int table_size = nextLine.nextInt();

        //create a hash table of size equal to table_size
        HashTable table = new HashTable(table_size);

        //read operations from the input file
        String op;
        int x;
        while(input.hasNext())
        {
            nextLine = new Scanner(input.nextLine());
            op = nextLine.next();

            if (op.equals("insert"))
            {
                x = nextLine.nextInt(); // read the value x for insert
                table.insert(x);
            }
            if (op.equals("remove"))
            {
                x = nextLine.nextInt(); // read the value x for remove
                table.remove(x);
            }
            if (op.equals("search"))
            {
                x = nextLine.nextInt();
                if (table.search(x) == true)
                    System.out.println("The key " + x + " is in the current hash table.");
                else// x is not in the hash table
                    System.out.println("The key " + x + " is not in the current hash table.");
            }
        }

        System.out.println();

        //print the table out on the console
        table.printTable();

        input.close();
    }
}
