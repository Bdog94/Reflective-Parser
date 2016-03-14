package methods;

import java.util.ArrayList;

/**
 * The pieces that make a ParseTree
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
	 * Create a node using the given expression and position in a parsed line
	 * @param expression
	 */
	public Node(ParseGrammer.Expr expression, int position) {
		this.expression = expression;
		this.sub_expr = new ArrayList<Node>();
		this.linePosition = position;
	}

	/**
	 * 
	 * @return the expression contained in the node
	 */
	public ParseGrammer.Expr getExpression() {
		return this.expression;
	}

	/**
	 * 
	 * @return the position in the input line of the node
	 */
	public int getLinePosition() {
		return linePosition;
	}

	/**
	 * 
	 * @return the list of subexpressions of the node (or the children of it)
	 */
	public ArrayList<Node> getSubExpr() {
		return this.sub_expr;
	}

	/**
	 * Sets the expression of the node
	 * @param expression to be set
	 */
	public void setFunCall(ParseGrammer.Expr expression) {
		this.expression = expression;
	}

	/**
	 * Gets the number of subexpressions this node has
	 * @return the number of subexpressions
	 */
	public int numExpressions() {
		return sub_expr.size();
	}

	/**
	 * Add a new sub expression to the list of sub expressions
	 * @param newExpression the expression to add to the list
	 */
	public void addExpression(Node newExpression) {
		this.sub_expr.add(newExpression);
	}

	/**
	 * finds a subexpression in the list at the given index, or null if none exists
	 * @param index the index to find the element at
	 * @return null if no element with that index exists, or the Node at that index
	 */
	public Node findSubExpr(int index) {
		if (!sub_expr.isEmpty() && index < sub_expr.size())
			return this.sub_expr.get(index);
		else
			return null;
	}

	/**
	 * Converts a node to a string in the format:
	 * {thisnode<position>(if a funcall, then :subexpr1<pos1>,...,subexprn<posn>)}
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
	 * returns the size of the node, counting itself
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
