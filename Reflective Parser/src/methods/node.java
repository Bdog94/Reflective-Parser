package methods;

import java.util.ArrayList;

public class node {

	private ParseGrammer.Expr expression;
	private ArrayList <node> sub_expr;
	
	public node(ParseGrammer.Expr expression) {
		this.expression = expression;
		this.sub_expr = new ArrayList<node>();
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

	public void addExpression(node newExpression)
	{
		this.sub_expr.add(newExpression);
	}
	
	public node getExpression(int index)
	{
		if(sub_expr.isEmpty())
			return this.sub_expr.get(index);
		else return null;
	}
	
	public String toString ()
	{
		String nodeString = this.expression.toString();
		
		for(node n: this.sub_expr)
			nodeString = nodeString + '\n' + n.toString(); 
			
		return nodeString; 
	}
	
	public int getSize()
	{
		int size = 1;
		for(node n: this.sub_expr)
			size += n.getSize();
		return size;
	}
	
}
