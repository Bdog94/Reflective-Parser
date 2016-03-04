package methods;

public class ParseGrammer {

	 class Expr {
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

	 class Funcall {
		
		String ident;
		public String getIdent() {
			return ident;
		}
		public void setIdent(String ident) {
			this.ident = ident;
		}
		int numOfExpr;
		public int getNumOfExpr() {
			return numOfExpr;
		}
		public void setNumOfExpr(int numOfExpr) {
			this.numOfExpr = numOfExpr;
		}
		Expr[] expr_set;
		public Expr[] getExpr_set() {
			return expr_set;
		}
		public void setExpr_set(Expr[] expr_set) {
			this.expr_set = expr_set;
		}
		
		public Funcall(String ident, int numOfExpr, Expr[] expr_set) {
			super();
			this.ident = ident;
			this.numOfExpr = numOfExpr;
			this.expr_set = expr_set;
		}
		public Funcall()
		{
			this.ident = null;
			this.numOfExpr = 0;
			this.expr_set = null;
		}
		
		
		
		
	}
	class Value {
		/**
		 * This constructor allows for an Object class object to be cast
		 * used in the Value class
		 */
		public Value(Object obj) throws InvalidValueException {
			if (obj.getClass().getSimpleName().equals("String")){
				val_string = (String) obj;
				containInt = false;
				containFloat = false;
				containString = true;
			} else if (obj.getClass().getSimpleName().equals("Integer")){
				Integer tmpInt = (Integer) obj;
				val_int = tmpInt.intValue();
				containInt = true;
				containFloat = false;
				containString = false;
			} else if (obj.getClass().getSimpleName().equals("Float")){
				Float tmpFloat = (Float) obj;
				val_float = tmpFloat.floatValue();
				containInt = false;
				containFloat = true;
				containString = false;
			} else {
				throw new InvalidValueException();
			}
			
		}

		public Value() {
			val_int = 0;
			val_float = 0.0f;
			val_string = null;
			
			containInt = false;
			containFloat = false;
			containString = false;
			
		}

		int val_int;
		float val_float;
		String val_string;
		boolean containInt;
		boolean containFloat;
		boolean containString;
		
		
		public int getVal_int() {
			return val_int;
		}

		public void setVal_int(int val_int) {
			this.val_int = val_int;
		}

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

		public boolean isContainInt() {
			return containInt;
		}

		public void setContainInt(boolean containInt) {
			this.containInt = containInt;
			this.containFloat = false;
			this.containString = false;
		}

		public boolean isContainFloat() {
			return containFloat;
		}

		public void setContainFloat(boolean containFloat) {
			this.containFloat = containFloat;
			this.containInt = false;
			this.containString = false;
		}

		public boolean isContainString() {
			return containString;
		}

		public void setContainString(boolean containString) {
			this.containString = containString;
			this.containFloat = false;
			this.containInt = false;
		}
		
		}
	
	
		
	}





