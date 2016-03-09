package methods;

import java.util.ArrayList;

/**
 * 
 * @author Kyle Perry
 *
 */
public class Node {

	private ParseGrammer.Expr expression;
	private ArrayList <Node> sub_expr;
	
	
	public Node() {
		this.expression = null;
		this.sub_expr = new ArrayList<Node>(); 
	}
	/**
	 * 
	 * @param expression
	 */
	public Node(ParseGrammer.Expr expression) {
		this.expression = expression;
		this.sub_expr = new ArrayList<Node>();
	}

	/**
	 * 
	 * @return
	 */
	public Object getExpression() {
		return this.expression;
	}

	/**
	 * 
	 * @param expression
	 */
	public void setFunCall(ParseGrammer.Expr expression) {
		this.expression = expression;
	}

	/**
	 * 
	 * @return
	 */
	public int numExpressions ()
	{
		return sub_expr.size();
	}

	/**
	 * 
	 * @param newExpression
	 */
	public void addExpression(Node newExpression)
	{
		this.sub_expr.add(newExpression);
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public Node getExpression(int index)
	{
		if(sub_expr.isEmpty())
			return this.sub_expr.get(index);
		else return null;
	}
	
	/**
	 * 
	 */
	public String toString ()
	{
		String nodeString = this.expression.toString();

		if(this.sub_expr.size() > 0)
		{
			nodeString += ":{";
		for(Node n: this.sub_expr)
			nodeString = nodeString + n.toString() + ',';
		
		if(nodeString.endsWith(","))
			nodeString = nodeString.substring(0, nodeString.lastIndexOf(','));
		nodeString += "}";
		}
		return nodeString; 
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSize()
	{
		int size = 1;

		for(Node n: this.sub_expr)
			size += n.getSize();
		return size;
	}
	
}
