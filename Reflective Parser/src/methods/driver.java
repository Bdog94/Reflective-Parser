package methods;

public class driver {

	public static void main(String[] args) {
		
		//Constructors, etc.
		
		System.out.println("Hello World");

		node testNode = new node("test");
		testNode.setLeft(new node("left"));
		testNode.setRight(new node("right"));
		testNode.getLeft().setRight(new node("right node of left"));
		System.out.println(testNode.toString());
	}

}
