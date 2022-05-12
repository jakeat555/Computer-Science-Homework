import java.util.Scanner;
import java.io.*;

class TreeNode
{
    public int key;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int key_input, TreeNode left_input, TreeNode right_input)
    {
        key = key_input;
        left = left_input;
        right = right_input;
    }
}


class BST {
	private TreeNode root;

	public BST() {
		root = null;
	}

	//the pre-order traversal of the tree
	public void preOrder() {
		preOrder(root);//call the overloaded function
		System.out.println();
	}

	//the pre-order traversal overloaded function
	private void preOrder(TreeNode v) {
		if (v != null) {
			System.out.print(v.key + " ");
			preOrder(v.left);
			preOrder(v.right);
		}
	}

	//the in-order traversal of the tree
	public void inOrder() {
		inOrder(root);//call the overloaded function
		System.out.println();
	}

	//the in-order traversal overloaded function
	private void inOrder(TreeNode v) {
		if (v == null)
			return;
		inOrder(v.left);
		System.out.print(v.key + " ");
		inOrder(v.right);
	}

	//please complete the following function; you can overload it if you want
	public boolean search(int x) {
		if (search(root, x) == null) {
			return false;
		}
		return true;
	}

	private TreeNode search(TreeNode v, int x) {
		if (v == null) {
			return null;
		}
		if (v.key == x) {
			return v;
		} else if (v.key > x) {
			return search(v.left, x);
		} else { // v.key < x
			return search(v.right, x);
		}
	}

	//please complete the following function; you can overload it if you want
	public void insert(int x) {
		if (!search(x)) {
			root = insert(root, x);
		}
	}

	private TreeNode insert(TreeNode v, int x) {
		if (v == null) {
			return new TreeNode(x, null, null);
		}
		if (x < v.key) {
			v.left = insert(v.left, x);
		} else { // x > v.key
			v.right = insert(v.right, x);
		}
		return v;

	}

	//please complete the following function; you can overload it if you want
	public void remove(int x) {
		if (search(x)) {
			remove(root, x);
		}
	}

	private TreeNode remove(TreeNode v, int x) {
		if (v == null) {
			return v;
		}
		else if (x < v.key) {
			v.left = remove(v.left, x);
		}
		else if (x > v.key) {
			v.right = remove(v.right, x);
		}
		else {
			if (v.left == null && v.right == null) {//leaf node
				return null;
			}
			else if (v.left == null) { //has right leaf
				return v.right;
			}
			else if (v.right == null) { //has left leaf
				return v.left;
			}
			v.key = findMin(v.right);
			v.right = remove(v.right, v.key);
		}
		return v;
	}

	//please complete the following function; you can overload it if you want
	public int findMin()
	{
		return findMin(root);
	}

	private int findMin(TreeNode v){
		int min = v.key;
		while (v.left != null)
		{
			min = v.left.key;
			v = v.left;
		}
		return v.key;
	}
}


public class hw3_Q4
{
	public static void main(String[] args) throws IOException
	{

		BST tree = new BST();

		String inputFile = "hw3_Q4_input.txt"; // input file with operations 

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
			if (op.equals("search"))
			{
				x = nextLine.nextInt();
				if (tree.search(x) == true)
					System.out.println("The key " + x + " is in the current tree.");
				else// x is not in the tree
					System.out.println("The key " + x + " is not in the current tree.");
			}
			if (op.equals("findMin"))
				System.out.println("The smallest key in the current tree is " + tree.findMin());
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
