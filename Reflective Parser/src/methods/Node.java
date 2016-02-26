package methods;

import java.util.ArrayList;

public class Node {

	private ParseGrammer.Expr expression;
	private ArrayList <Node> sub_expr;
	
	public Node(ParseGrammer.Expr expression) {
		this.expression = expression;
		this.sub_expr = new ArrayList<Node>();
	}

	public Object getExpression() {
		return this.expression;
	}

	public void setFunCall(ParseGrammer.Expr expression) {
		this.expression = expression;
	}

	public int numExpressions ()
	{
		return sub_expr.size();
	}

	public void addExpression(Node newExpression)
	{
		this.sub_expr.add(newExpression);
	}
	
	public Node getExpression(int index)
	{
		if(sub_expr.isEmpty())
			return this.sub_expr.get(index);
		else return null;
	}
	
	public String toString ()
	{
		String nodeString = this.expression.toString();
		
		for(Node n: this.sub_expr)
			nodeString = nodeString + '\n' + n.toString(); 
			
		return nodeString; 
	}
	
	public int getSize()
	{
		int size = 1;
		for(Node n: this.sub_expr)
			size += n.getSize();
		return size;
	}
	
}
