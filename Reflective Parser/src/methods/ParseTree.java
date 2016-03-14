package methods;

/** 
 * The tree created by parsing an input line 
 * @author Kyle Perry
 *
 */
public class ParseTree {

	Node head;

	/**
	 * Constructs a tree with the given head node
	 * @param head
	 */
	public ParseTree(Node head) {
		super();
		this.head = head;
	}
	
	/**
	 * Default constructor, does not have a head node, and it must be set
	 */
	public ParseTree(){
		this.head = null;
	}
	
	/**
	 * Set the head of the tree to the parameter given
	 * @param head the new head of the tree
	 */
	public void setHead(Node head) {
		this.head = head;
	}
	
	
	/**
	 * Calls toString on the head of the tree, and returns it
	 */
	public String toString()
	{
		return this.head.toString();
	}
	
	/**
	 * Gets the size of the tree
	 * @return the number of nodes in the tree
	 */
	public int getTreeSize()
	{
		if (head == null)
			return 0;
		else
			return head.getSize();
	}
	
}
