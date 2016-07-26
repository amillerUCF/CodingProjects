import java.util.Scanner;

/**
 * This program is an interview question from Microsoft where a
 * Binary Search Tree is converted to a double linked list. A
 * double linked list is one where each node that points to another, 
 * is also being pointed back to by that node. i.e. the connection 
 * between two nodes, can be described as the "if and only if"
 * symbol from Discrete Mathematics
 * 
 * What does it implement?
 * The program implements BSTs, Double Linked Lists, k-time testing, and conversion of BST to DLL
 * 
 * @author Austin
 *
 */

public class BST_to_DLL {

	static BST bst = new BST();
	static DLL dll;
	
	public static void main(String[] args)
	{
		int choice = 1;
		Scanner scan = new Scanner(System.in);
		
		while(choice < 5)
		{
			System.out.println("___Binary_Search_Tree_Operations___");
			System.out.println("1) Insert");		
			System.out.println("2) Delete");
			System.out.println("3) Check if empty");
			System.out.println("4) Convert BST to DLL");
			System.out.println("5) Exit");
			choice = scan.nextInt();

			switch(choice)
			{
			case 1: // Insert a node
				System.out.println("Enter number element to insert into BST");
				bst.Insert(scan.nextInt());
				break;
				
			case 2: // Delete a node
				System.out.println("Enter number element to delete from BST");
				int num = scan.nextInt();
				BSTNode node = bst.Search(num);
				BSTNode min = bst.FindMin(node.getRight());
				BSTNode parent = null;
				if(node.getRight() != null)
					parent = bst.getParent(bst.FindMin(node.getRight()));
				bst.Delete(num, min, parent);
				System.out.println("\nNode "+num+" was successfully deleted!\n");
				break;
				
			case 3: // Check if BST is empty
				boolean b = bst.isEmpty();
				if(b == true)
					System.out.println("\nBST is currently empty\n");
				else
					System.out.println("\nBST is currently not empty\n");
				break;
				
			case 4: // Convert the BST to a DLL
				convertToDLL();
				System.out.println("\nBST successfully converted to DLL\n");
				System.out.println("Head: "+dll.head.getData());
				System.out.println("Tail: "+dll.tail.getData());
				dll.iterateForward();
				dll.iterateBackward();
				break;
				
			case 5: // Exit program
				System.exit(0);
				
			default: // There was an invalid input
				System.out.println("\nYour entry was invalid\n");
				break;
			}
			
			
			// Displays contents of tree with the three traversal methods
			if(!bst.isEmpty())
			{
				System.out.printf("\nPre order: "); bst.preOrder();
				System.out.printf("\nIn order: "); bst.inOrder();
				System.out.printf("\nPost order: "); bst.postOrder();
				System.out.println("\n");
			}
		}
	}

	public static void convertToDLL()
	{
		dll = bst.extractValues();
	}
}
