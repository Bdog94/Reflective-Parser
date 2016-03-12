package methods;

import java.util.ArrayList;

/**
 * 
 * @author Kyle Perry
 *
 */
public class Node {

	private ParseGrammer.Expr expression;
	private ArrayList<Node> sub_expr;
	private int linePosition;

	public Node() {
		this.expression = null;
		this.sub_expr = new ArrayList<Node>();
	}

	/**
	 * 
	 * @param expression
	 */
	public Node(ParseGrammer.Expr expression, int position) {
		this.expression = expression;
		this.sub_expr = new ArrayList<Node>();
		this.linePosition = position;
	}

	/**
	 * 
	 * @return
	 */
	public ParseGrammer.Expr getExpression() {
		return this.expression;
	}

	/**
	 * 
	 * @return
	 */
	public int getLinePosition() {
		return linePosition;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Node> getSubExpr() {
		return this.sub_expr;
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
	public int numExpressions() {
		return sub_expr.size();
	}

	/**
	 * 
	 * @param newExpression
	 */
	public void addExpression(Node newExpression) {
		this.sub_expr.add(newExpression);
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public Node getExpression(int index) {
		if (!sub_expr.isEmpty())
			return this.sub_expr.get(index);
		else
			return null;
	}

	/**
	 * 
	 */
	public String toString() {
		String nodeString = this.expression.toString() + '<'
				+ this.linePosition + '>';

		if (this.sub_expr.size() > 0) {
			nodeString += ":{";
			for (Node n : this.sub_expr)
				nodeString = nodeString + n.toString() + ',';

			if (nodeString.endsWith(","))
				nodeString = nodeString.substring(0,
						nodeString.lastIndexOf(','));
			nodeString += "}";
		}
		return nodeString;
	}

	/**
	 * 
	 * @return
	 */
	public int getSize() {
		int size = 1;

		for (Node n : this.sub_expr)
			size += n.getSize();
		return size;
	}

	public int findExpression (ParseGrammer.Expr target)
	{
		int targetPosition = -1;
	
		if(this.expression.isFunCall())
		{
			if(this.expression.funCall.equals(target.funCall))
				targetPosition = this.linePosition;
			else
				{for(Node n: this.sub_expr)
					{targetPosition = n.findExpression(target);
					if(targetPosition != -1)
					break;
				}}}
		else
		{
			if(this.expression.getValue().equals(target.getValue()))
			targetPosition = this.linePosition;
		}
		return targetPosition;
	}
}
