package methods;

public class ParseTree {

	Node head;

	public ParseTree(Node head) {
		super();
		this.head = head;
	}
	
	public String toString()
	{
		return this.head.toString();
	}
	
	public int getTreeSize()
	{
		if (head == null)
			return 0;
		else
			return head.getSize();
	}
	
}
