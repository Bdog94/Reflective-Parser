package methods;

public class ParseGrammer {

	public class Expr {
		Funcall funCall;
		Value value;
		
		/*
		 * Does not contain any functionality right now
		 */
		public boolean containFunCall() {
			return false;
			
		}
		/*
		 * No functionality currently
		 */
		public boolean containValue() {
			return false;
			
		}
		
	}

	public class Funcall {
		Identifier ident;
		int numOfExpr;
		Expr[] expr_set;
		
		
	}
	public class Value {
		int val_int;
		float val_float;
		String val_string;
		
		/*
		 * No functionality currently
		 */
		public boolean containInt(){
			return false;
			
		}
		
		/*
		 * No functionality currently
		 */
		public boolean containFloat(){
			return false;
		}
		
		/*
		 * No functionality currently
		 */
		public boolean containString(){
			return false;
			
		}
		
		}
	
	public class Identifier {
		String id;
	}
		
	}





