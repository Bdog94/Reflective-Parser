package methods;

public class ParseGrammer {

	public class Expr {
		Funcall funCall;
		Value value;
		
		public Funcall getFunCall() {
			return funCall;
		}
		public void setFunCall(Funcall funCall) {
			this.funCall = funCall;
		}
		public Value getValue() {
			return value;
		}
		public void setValue(Value value) {
			this.value = value;
		}
		
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
		
		
		
		public float getVal_float() {
			return val_float;
		}

		public void setVal_float(float val_float) {
			this.val_float = val_float;
		}

		public String getVal_string() {
			return val_string;
		}

		public void setVal_string(String val_string) {
			this.val_string = val_string;
		}
		
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





