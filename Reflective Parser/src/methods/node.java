package methods;

public class node {

	String data;
	node left;
	node right;
	
	public node(String data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}

	public node(String data, node left, node right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

	public Object getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public node getLeft() {
		return left;
	}

	public void setLeft(node left) {
		this.left = left;
	}

	public node getRight() {
		return right;
	}

	public void setRight(node right) {
		this.right = right;
	}
	
	public String toString ()
	{
		String nodeString = this.data.toString();
		
		if(this.left != null)
			nodeString += "\n(Left Node: " + this.left.toString() + ")";
		if(this.right != null)
			nodeString += "\n(Right Node: " + this.right.toString() + ")";

		return nodeString; 
	}
	
	public int getSize()
	{
		int size = 1;
		if (this.left != null)
			size += this.left.getSize();
		if (this.right != null)
			size += this.right.getSize();
		return size;
	}
	
}
