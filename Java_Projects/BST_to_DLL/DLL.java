// This class implements methods for the Doubly Linked List

public class DLL {

	public DLLNode head;
	public DLLNode tail;
	private int size;
	
	// Constructor
	public DLL()
	{
		head = null;
		tail = null;
		size = 0;
	}
	
	// Adds a node at the end of the double linked list
	public void addLast(int data) 
	{
        DLLNode temp = new DLLNode(data);
        DLLNode cursor = head;
        if(head == null)
        {
        	temp.addNext(null);
        	temp.addPrev(null);
        	head = temp;
        }
        else
        {
        	while(cursor.getNext() != null)
        	{
        		cursor = cursor.getNext(); // traverse
        	}
        	cursor.addNext(temp);
        	temp.addPrev(cursor);
        	tail = temp;
        }
    }
	
	// Prints the double linked list normally
	public void iterateForward()
	{
        System.out.printf("Iterating forwards: ");
        DLLNode temp = head;
        while(temp != null){
            System.out.printf(temp.getData() + " ");
            temp = temp.getNext();
        }
        System.out.println("");
    }
     

    // Prints the double linked list backwards to ensure there is a previous node linkage
    public void iterateBackward(){
         
        System.out.printf("Iterating backwards: ");
        DLLNode temp = tail;
        while(temp != null){
            System.out.printf(temp.getData() + " ");
            temp = temp.getPrev();
        }
        System.out.println("");
    }
}
