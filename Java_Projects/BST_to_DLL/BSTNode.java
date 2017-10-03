// This class implements methods for the nodes

public class BSTNode {

	//BST bst = new BST();
	private BSTNode left, right;
	private int data;
	
	// Constructor for the BST nodes
	public BSTNode(int num)
	{
		left = null;
		right = null;
		data = num;
	}
	
	// Sets the parents left child
	public void setLeft(BSTNode n)
	{
		left = n;
	}
	
	// Sets the parents right child
	public void setRight(BSTNode n)
	{
		right = n;
	}
	
	// Gets the parents left child
	public BSTNode getLeft()
	{
		return left;
	}
	
	// Gets the parents right child
	public BSTNode getRight()
	{
		return right;
	}
	
	// Gets the data for the selected node
	public int getData()
	{
		return data;
	}
	
	// Sets the data for the given node (Helps with Delete() in BST)
	public void setData(int num)
	{
		data = num;
	}
}
