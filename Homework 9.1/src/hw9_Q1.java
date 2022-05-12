import java.io.File;
import java.io.IOException;
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

    //the method for performing topological sort
    public void topSort()
    {
        //use an array to store the indegrees of the vertices
        int[] indegree = new int [n];

        //initialize their indegrees to zero
        for(int i = 0; i < n; i++)
            indegree[i] = 0;

        //compute the indegree of each vertex
        for(int u = 0; u < n; u++)
        {
            Vertex v = adj[u];
            while(v != null)
            {
                indegree[v.id] ++;
                v = v.next;
            }
        }

        //use an array Q to implement a queue
        int[] Q = new int [n];
        int front, rear;
        front = rear = -1;//indicate Q is empty

        for(int i = 0; i < n; i++)
        {
            if(indegree[i] == 0)
            {
                //perform the enqueue operation
                if(front == -1)//the queue is empty
                {
                    front = rear = 0;
                    Q[0] = i;
                }
                else
                {
                    rear ++;
                    Q[rear] = i;
                }

            }
        }

        while(front != -1 && front <= rear)//the queue is not empty
        {
            //dequque
            int u = Q[front];
            front ++;

            //print the vertex
            System.out.print(u + " ");

            //check the adjacecy list of u
            Vertex v = adj[u];
            while(v != null)
            {
                indegree[v.id] --;
                if(indegree[v.id] == 0)
                {
                    //enqueue v
                    rear ++;
                    Q[rear] = v.id;
                }
                v = v.next;
            }
        }

        System.out.println();
    }
}

public class hw9_Q1 {
    public static void main(String[] args) throws IOException {
        //open files
        String inputFile = "hw9_Q1_input.txt"; // the name of the input file
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

        //uncomment the following piece of code if you want to check see the adj matrix
     	/*System.out.println("The following is the matrix:");
	    for(i = 0; i < n; i++)
	    {
	        for(j = 0; j < n; j++)
	            System.out.print(M[i][j] + " ");
            System.out.println();
	    }*/

        //initialize the graph by passing n to it
        Graph graph = new Graph(n);

        //construct the adj lists from M by using the method setAdjLists()
        graph.setAdjLists(M);

        //uncomment the following line if you want to print the adj lists
        //graph.printAdjLists();

        //compute the topological order of all vertices by calling the method topSort()
        System.out.println("The following is a topological order:");
        graph.topSort();
    }
}
