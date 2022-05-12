import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

//the vertex class
class Vertex
{
    int id;//the id of the vertex
    Vertex next;
    Vertex(int id_input)
    {
        id = id_input;
        next = null;
    }
}

//the graph class
class Graph
{
    private int[] color;//the array for storing the vertex colors during BFS
    private int[] pre; // the array for storing the predecessors during BFS
    private int[] dis;// the array for storing the shortest path lengths during BFS

    public int n;//the number of vertices, the ids of the vertices are from 0 to n-1
    public Vertex[] adj;//adj[i] is the head of the adjacency list of vertex i, for i from 0 to n-1

    //class constructor, initialize the graph by getting the number of vertices from n_input
    public Graph(int n_input)
    {
        n = n_input;
        adj = new Vertex [n];
        //initialize adj[i] to null
        for(int i = 0; i < n; i++)
            adj[i] = null;

        color = new int [n];//create the array for color information
        pre = new int [n];//create the array for predecessor information
        dis = new int [n];//create the array for shortest path distance information

    }

    //build the adjacency lists from the adjacency matrix adjM
    public void setAdjLists(int[][] adjM)
    {
        for(int i = 0; i < n; i++)
        {
            //create the i-th adj list adj[i], note that it scans the vertices in the reverse order from n-1 to 0 so that it can
            //construct the adjacent list of each vertex in the increasing index order because a new vertex is always inserted to the front of a list
            for(int j = n-1; j >= 0; j--)
            {
                if(adjM[i][j] == 1)
                {
                    //create a new node and add it to the front of adj[i]
                    Vertex v = new Vertex (j);
                    v.next = adj[i];
                    adj[i] = v;
                }
            }
        }
    }

    //print the adjacency lists of the graph
    public void printAdjLists()
    {
        for(int i = 0; i < n; i++)
        {
            System.out.print("Adj list of vertex " + i + ": ");
            Vertex v = adj[i];
            while(v != null)
            {
                if (v.next != null)
                    System.out.print(v.id + "->");
                else
                    System.out.print(v.id);
                v = v.next;
            }
            System.out.println();
        }
        System.out.println();
    }

    //the following two functions are for the BFS traversal, as we discussed in class, but you may choose to use different ways
    //BFS traversal, id is the source vertex
    public void BFS(int id) {
        for(int i =0; i <= n-1; i++ ) {
            color[i] = 0;
            pre[i] = n + 1;
            dis[i] = n + 1;
        }
        BFSVisit(id);
        for(int i = 0; i <= n-1; i++){
            if(color[i]==0){
                BFSVisit(id);
            }
        }
    }

    // the function that actually does BFS traversal on a particular connected component from vertex id
    public void BFSVisit(int s) {
        dis[s]=0;
        color[s]=1;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);    //enqueue
        while(queue.size()!=0){    //While queue is not empty
            int i = queue.pollFirst();
            System.out.print(i+ " ");
            Vertex v = adj[i];
            while(v!=null){
                //v = v.next;
                if(color[v.id] == 0){ //white
                    color[v.id] = 1;    // make it blue
                    dis[v.id] = dis[i] + 1;
                    pre[v.id] = i;
                    queue.add(v.id);
                }
                v = v.next;
            }
            color[i] = 2; // red
        }
    }

    //the following is the function I would use to print shortest path from the source to v, as discussed in class, again you may use different ways
    public void printSP(int source, int v) {
        if(v == source){
            System.out.print(v);
        }
        else if(v < n){
            printSP(source, pre[v]);
            //System.out.print(pre[v]);
            System.out.print("->" + v);
        }
    }
}

public class hw8_Q5 {
    public static void main(String[] args) throws IOException {
        //open files
        String inputFile = "hw8_Q5_input.txt"; // the name of the input file
        File myFile = new File(inputFile);
        Scanner input = new Scanner(myFile);

        //read the number in the first line, which is the number of vertices of the input graph
        int n;//the number of vertices of the graph
        n = input.nextInt();
        //System.out.println("The number of vertices is: " + n);

        //Next we read the adjacency matrix from the file to an two-dimensional array M
        int[][] M = new int[n][n];
        int i = 0;//row index for M
        int j = 0;//column index for M
        int value;
        while(input.hasNext())
        {
            value = input.nextInt();
            M[i][j] = value;
            j++;
            if(j == n)//j reaches the end of a row
            {
                i++;//start a new row
                j=0;//the column index becomes zero
            }
        }
        input.close();

        //uncomment the following piece of code if you want to see the adj matrix
//     	System.out.println("The following is the matrix:");
//	    for(i = 0; i < n; i++)
//	    {
//	        for(j = 0; j < n; j++)
//	            System.out.print(M[i][j] + " ");
//            System.out.println();
//	    }

     	//initialize the graph by passing n to it
        Graph graph = new Graph(n);

        //construct the adj lists from M by using the method setAdjLists()
        graph.setAdjLists(M);

        //uncomment the following line if you want to print the adj lists
        //graph.printAdjLists();


        //the following pieces of code is what I would use to output the information required by the assignment

        //do BFS the print the traversal order out, starting from vertex 0
        System.out.println("The following is the BFS traversal vertex order, staring from vertex 0:");
        graph.BFS(0);

        //the following prints shortest paths from 0 to each of the other vertices
        System.out.println("\nHere are the shortest paths from 0 to all other vertices:");
        for(i = 0; i < n; i++)
        {
            for(int p=0; p<n; p++) {
                System.out.print("A shortest path from vertex " + i + " to vertex " + p + ": ");
                graph.printSP(p, i);
                System.out.println();
            }
        }

    }
}