package methods;

import methods.ParseGrammer.Funcall;

public class InvalidFunctionCallException extends Exception{

	public Funcall f;

	public InvalidFunctionCallException(Funcall f) {
		super();
		this.f = f;
		// TODO Auto-generated constructor stub
	}

	public InvalidFunctionCallException(String arg0, Funcall f) {
		super(arg0);
		this.f = f;
		// TODO Auto-generated constructor stub
	}
	
	public String toString()
	{
		return this.getMessage();
	}

}
