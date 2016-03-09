package methods;

/*
When an error is found in the compiler, print the error and go back to the program.
*/

public class Error {
	
	//errors should continue with the program from Main
	//static CmdLine main = new CmdLine();
	//trees is needed to check if input is a funcall in order to get the offset line pointer 
	//static Trees trees = new Trees();

	static int offset = 0;
	static String func = null;
	
	//Here is where I have my getters and setters for the user input and the offset of the error
	
	/**
	 * This function will return the offset number of an error
	 * @return the offset that was set from a certain error
	 */
	public static int getOffset() {
		return offset;
	}
	
	/**
	 * This function will return the user input
	 * @return func (user input)
	 */
	public static String getInput() {
		return func;
	}
	
	
	/**
	 * This function sets the offset of a certain error
	 * @param newOffset new offset where a certain error has occured
	 */
	public static void setOffset(int newOffset) {
		offset = newOffset;
	}
	
	/**
	 * setting newInput when we find an error
	 * @param newInput the input that causes the error.
	 */
	public static void setInput(String newInput) {
		func = newInput;
	}

	
	/**
	 * This is the beginning message of the error handle. Printing out the input, matching with the corresponding offset
	 */
	public static void beginError (){		
		System.out.println("Matching function for '" + getInput() + "' not found at offset " + getOffset() + "\n"
				+ getInput());
		//offsetPoint prints out the corresponding offset line print
		offsetPoint();
	}

	/**
	 * This function splits the error input from the error message
	 */
	public static void offsetPoint(){
		//starts with a -, null was having errors but essentially this is the start of the offset pointer 
		String s = "-";	
		//this for loop creates a line of dash's (using the offset) to point to point of error
		for (int i=0; i<getOffset(); i++){ 
			s.concat("-"); // concat the the line
		}	
		s.concat("^"); // end off with a hat !
	}
}
