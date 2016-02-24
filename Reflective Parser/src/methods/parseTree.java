package methods;

public class parseTree {

	node head;

	public parseTree(node head) {
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
