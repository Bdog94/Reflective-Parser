package methods;

import methods.ParseGrammer.Funcall;
import methods.ParseGrammer.Value;

public class InvalidFunctionCallException extends Exception{

	public Funcall f;
	public Value[] params;
	
	public InvalidFunctionCallException(Funcall f, Value[] params) {
		super();
		this.f = f;
		this.params = params;
		// TODO Auto-generated constructor stub
	}

	public InvalidFunctionCallException(String arg0, Funcall f, Value[] params) {
		super(arg0);
		this.f = f;
		this.params = params;
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public String getMethod()
	{ 
		
		String s =  "(" + f.ident + " ";
		for (Value v:params)
		{
			if (v.isContainFloat())
			{
				s += "float ";
			} else if (v.isContainInt())
			{
				s += "int ";
			} else if (v.isContainString())
			{
				s += "string ";
			}
		}
		s += ")";
		return s;
		
	}

}
