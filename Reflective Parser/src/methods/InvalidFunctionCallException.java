package methods;

import methods.ParseGrammer.Funcall;

public class InvalidFunctionCallException extends Exception {
	Funcall f;
	
	public InvalidFunctionCallException(Funcall aFuncall)
	{
		this.f = aFuncall;
	}

}
