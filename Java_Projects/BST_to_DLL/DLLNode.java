
public class DLLNode {

	private DLLNode prev, next; // Holds link to the previous and next nodes
	private int data; // Holds data in each node
	
	// Creates a node with no connections but data
	public DLLNode(int num)
	{
		next = null;
		prev = null;
		data = num;
	}
	
	public void addPrev(DLLNode n)
	{
		prev = n;
	}
	
	public void addNext(DLLNode n)
	{
		next = n;
	}
	
	public DLLNode getPrev()
	{
		return prev;
	}
	
	public DLLNode getNext()
	{
		return next;
	}
	
	public int getData()
	{
		return data;
	}
	
	public void setData(int num)
	{
		data = num;
	}
}
