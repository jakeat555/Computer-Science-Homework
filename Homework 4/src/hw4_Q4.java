import java.util.Scanner;
import java.io.*;

class AvlNode {
    public int key;
    public AvlNode left;
    public AvlNode right;
    public int height;

    public AvlNode(int key_input)
    {
        key = key_input;
        height = 0;
        left = null;
        right = null;
    }
}

class AvlTree{
    private AvlNode root;
    public AvlTree(){root = null;}

    public void inOrder()
    {
        inOrder(root);
        System.out.println();
    }
    private void inOrder(AvlNode v)
    {
        if(v == null)
            return;
        inOrder(v.left);
        System.out.print(v.key + " ");
        inOrder(v.right);
    }

    public void preOrder()
    {
        preOrder(root);
        System.out.println();
    }
    private void preOrder(AvlNode v)
    {
        if(v == null)
            return;
        System.out.print(v.key + " ");
        preOrder(v.left);
        preOrder(v.right);
    }

    private int getHeight(AvlNode v){
        if(v == null)
            return -1;
        else
            return v.height;
    }

    public void insert(int x) { root = insert(root, x); }

    public void remove(int x) { root = remove(root, x); }
    
    //please complete the following seven functions
	private AvlNode insert(AvlNode v, int x)//this function is overloaded
	{
        if (v == null){
            return new AvlNode(x);
        }
        if (x == v.key){
            return v;
        }
        if (x < v.key){
            v.left = insert(v.left, x);
        }
        else {
            v.right = insert(v.right, x);
        }
        return balance(v);
    }

    private AvlNode remove(AvlNode v, int x)//this function is overloaded
    {
        if (v == null){
            return null;
        }
        if (x < v.key) {
            v.left = remove(v.left, x);
            return v;
        }
        if (x > v.key){
            v.right = remove(v.right, x);
            return v;
        } //x == v->key
        if  (v.left == null) { //v does not have left child
            return v.right;
        }
        if  (v.right == null) {//v does not have right child
            return v.left;

        } // x has two children, replace v by the smallest key in the right subtree of v
        AvlNode u = v.right;
        while (u.left != null) {
            u = u.left;
        }
        v.key = u.key;
        v.right = remove(v.right, u.key);
        return v;
    }

    private int max(int x, int y){
        if (x>y){
            return x;
        }
        else return y;
    }

    private AvlNode balance(AvlNode v)
    {
        if (v == null) {
            return null;
        }
        if (getHeight(v.left) - getHeight(v.right) > 1){
            if (getHeight(v.left.left) >= getHeight(v.left.right)){ //left-left
                v = rightRotate(v);//perform right rotation and return the new root of the subtree
            }
            else {//left-right case
                v = doubleLeftRightRotate(v);
            }
        }
        else if (getHeight(v.right) - getHeight(v.left) > 1) {
            if (getHeight(v.right.right) >= getHeight(v.right.left)){//right-right
                v = leftRotate(v);
            }
            else {//right-left case
                v = doubleRightLeftRotate(v);
            }
            v.height = 1 + max(getHeight(v.left), getHeight(v.right)) ;
        }
        return v;
    }

    private AvlNode rightRotate (AvlNode v)
    {
        AvlNode k1 = v.left.left;
        AvlNode k2 = v.left;
        v.left = k1;
        k2.right = k1.left;
        k1.right = k2;
        return k1;
    }

    private AvlNode leftRotate (AvlNode v)
    {
        AvlNode k1 = v.left;
        AvlNode k2 = k1.right;
        v.right = k2;
        k1.right = k2.left;
        k2.right = k1;
        return k2;
    }

    private AvlNode doubleLeftRightRotate (AvlNode v)
    {
        return rightRotate(leftRotate(v.left));
    }

    private AvlNode doubleRightLeftRotate (AvlNode v)
    {
        return leftRotate(rightRotate(v.right));
    }
}

public class hw4_Q4 {
    public static void main(String[] args) throws IOException
    {

        AvlTree tree = new AvlTree();

        String inputFile = "hw4_Q4_input.txt"; // input file with operations

        //open the input file
        File myFile = new File(inputFile);
        Scanner input = new Scanner(myFile);

        //read operations from the input file
        String op;
        int x;
        while(input.hasNext())
        {
            Scanner nextLine = new Scanner(input.nextLine());
            op = nextLine.next();

            if (op.equals("insert"))
            {
                x = nextLine.nextInt(); // read the value x for insert
                tree.insert(x);
            }
            if (op.equals("remove"))
            {
                x = nextLine.nextInt(); // read the value x for remove
                tree.remove(x);
            }
        }

        //print the pre-odrder traversal on the console/screen
        System.out.println("The pre-order traversal list is: ");
        tree.preOrder();

        //print the in-odrder traversal
        System.out.println("The in-order traversal list is: ");
        tree.inOrder();

        input.close();
    }
}
