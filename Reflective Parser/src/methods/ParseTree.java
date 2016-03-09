package methods;

/**
 * 
 * @author Kyle Perry`
 *
 */
public class ParseTree {

	Node head;

	/**
	 * 
	 * @param head
	 */
	public ParseTree(){
		this.head = null;
	}
	
	public void setHead(Node head) {
		this.head = head;
	}
	
	public ParseTree(Node head) {
		super();
		this.head = head;
	}
	
	/**
	 * 
	 */
	public String toString()
	{
		return this.head.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public int getTreeSize()
	{
		if (head == null)
			return 0;
		else
			return head.getSize();
	}
	
}
