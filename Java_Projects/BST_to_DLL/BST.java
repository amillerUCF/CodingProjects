// This class implements methods for the Binary Search Tree

public class BST {

	private static DLL dll;
	private static BSTNode root; // Root of the tree
	static int k = 0; // K time test variable
	private static int size = 0; // Keeps size of BST
	
	// Constructor creates an empty BST
	public BST()
	{
		root = null;
	}
	
	// Checks if BST is empty
	public boolean isEmpty()
	{
		return (root == null);
	}
	
	/**
	 * Methods to insert a node into the BST
	 * @param nKeyValue
	 */
	public void Insert(int data) 
    {
    	// kTimeTest before inserting new node
    	if(kTimeTest(data) == false)
    	{
    		System.out.println("Cannot input the number " + data);
    		return;
    	}
    	root = Insert(data, root);
    	System.out.println(data + " was successfully added to the tree");
    	size++;
    }    
    private BSTNode Insert(int data, BSTNode node) 
    {
    	// This node is null and simply needs to be allocated.
        if(node == null)
        {
        	node = new BSTNode(data);
        	return node;
        }
        
        // Here we need to walk left.
        else if(data < node.getData())
        	node.setLeft(Insert(data, node.getLeft()));
        
        // Here we need to walk right.
        else if(data > node.getData())
        	node.setRight(Insert(data, node.getRight()));
        
        return node;
    }
    
    
    /**
     * Methods to delete a node in the BST
     * @param num
     */
    public void Delete(int num, BSTNode min, BSTNode parent)
    {
    	BSTNode node = Search(num);
    	if(isEmpty())
    		System.out.println("\nThe tree is currently empty\n");
    	else if(node == null)
    		System.out.println("That node does not exist in the tree");
    	else
    		Delete(node, min, parent);
    }
    private BSTNode Delete(BSTNode node, BSTNode min, BSTNode parent)
    {
    	if(root == null) 
    		return null; 

    	// Case 1: No child
    	if(node.getLeft() == null && node.getRight() == null)
    	{
    		parent = getParent(node);
    		if(parent.getLeft() == node) // if the node is the left child
    			parent.setLeft(null);
    		else
    			parent.setRight(null);
    	}
    	
    	// Case 2: One child
    	else if(node.getLeft() == null) // Child is to the right
    	{
    		parent = getParent(node);
    		if(node.getData() < parent.getData())
    			parent.setLeft(node.getRight());
    		else
    			parent.setRight(node.getRight());
    	}

    	else if(node.getRight() == null) // Child is to the left
    	{
    		parent = getParent(node);
    		if(node.getData() < parent.getData())
    			parent.setLeft(node.getLeft());
    		else
    			parent.setRight(node.getLeft());
    	}
    	
    	// Case 3: Two children
    	else
    	{
    		if(node.getRight() == min) // If the first node is the min
    		{
    			node.setData(min.getData());
    			node.setRight(min.getRight());
    			return node;
    		}

    		node.setData(min.getData());
    		parent.setLeft(min.getRight());
    	}
    	return node;
    }

    
    /**
     * Methods to search the BST for a specified node
     * @param data
     * @return node if in BST, otherwise "null"
     */
    public static BSTNode Search(int data)
    {
        return Search(root, data);
    }
    private static BSTNode Search(BSTNode node, int data)
    {
    	if(node == null)
    		return null;
    	
    	int nThisKeyValue = node.getData();
    	
    	if(data < nThisKeyValue)
    		node = node.getLeft();
    	else if(data > nThisKeyValue)
    		node = node.getRight();
    	else
    		return node;
    	return Search(node, data);
	}
    
    
    /**
     * Methods for k-time testing
     * @param key
     * @return true if node is acceptable under the k-time test, otherwise false
     */
    public boolean kTimeTest(int key) 
    {
    	if(kTimeTest(key, root, 0) == -1)
    		return false;
    	else
    		return true;
    }
	private int kTimeTest(int key, BSTNode node, int test)
    {
    	if(node == null)
    		return 0;
    	if(java.lang.Math.abs(node.getData()-key) <= k)
    		return -1; // return false on k-test
    	if(key < node.getData())
    		test = kTimeTest(key, node.getLeft(), test);
    	else
    		test = kTimeTest(key, node.getRight(), test);
    	return test;
    }
	
	
	// Methods for preorder traversal
	public void preOrder()
	{
		preOrder(root);
	}
	private void preOrder(BSTNode node)
	{
		if(node != null)
		{
			System.out.printf(node.getData() + " ");
			preOrder(node.getLeft());
			preOrder(node.getRight());
		}
	}
	
	
	// Methods for inorder traversal
	public void inOrder()
	{
		inOrder(root);
	}
	private void inOrder(BSTNode node)
	{
		if(node != null)
		{
			inOrder(node.getLeft());
			System.out.printf(node.getData() + " ");
			inOrder(node.getRight());
		}
	}
	
	
	// Methods for postorder traversal
	public void postOrder()
	{
		postOrder(root);
	}
	private void postOrder(BSTNode node)
	{
		if(node != null)
		{
			postOrder(node.getLeft());
			postOrder(node.getRight());
			System.out.printf(node.getData() + " ");
		}
	}
	
	
	// Finds the minimum value in the given tree
	public BSTNode FindMin(BSTNode node)
	{
		if(node == null)
			return null;
		while(node.getLeft() != null)
			node = node.getLeft();
		return node;
	}
	
	
	/**
	 * Gets the parent of the selcted node
	 * @param node
	 * @return the parent of the selected node
	 */
	public BSTNode getParent(BSTNode node)
    {
        return getParent(root, null, node.getData());
    }
    private BSTNode getParent(BSTNode node, BSTNode ptr, int data)
    {
    	if(node == null)
    		return null;
    	
    	int nThisKeyValue = node.getData();
    	
    	if(data < nThisKeyValue)
    	{	
    		ptr = node;
    		node = node.getLeft();
    	}
    	else if(data > nThisKeyValue)
    	{
    		ptr = node;
    		node = node.getRight();
    	}
    	else
    		return ptr;
 
    	return getParent(node, ptr, data);
	}
	
    
	// Extracts values so they can be put into the DLL
    public DLL extractValues()
    {
    	dll = new DLL();
    	extractValues(root);
    	return dll;
    }
    private static void extractValues(BSTNode n) 
    {
        if (n.getLeft() != null) 
            extractValues(n.getLeft());
        
        dll.addLast(n.getData());
        
        if (n.getRight() != null) 
            extractValues(n.getRight());
    }	
}
