package methods;


import java.text.ParseException;
import java.util.Scanner;

import methods.ParseGrammer.Expr;
import methods.ParseGrammer.Funcall;
import methods.ParseGrammer.Value;

/**
 * This class is where the argument error checking is done and where the user enters his inputs and makes his choices of what to do within the program.
 * @author Arthur Iwaniszyn
 *
 */

public class CmdLine {
	
	
	String className;
	String fileName;
	Reflection r;
	
	
	public CmdLine(){
		r = new Reflection();
	}
	
	/**
	 *  This method will display the users options when running this compiler.
	 */
	public void startMessage() {
		System.out.println("q \t : Quit the program.\n"
				+ "v \t : Toggle verbose mode (stack traces).\n"
				+ "f \t : List all known functions.\n"
				+ "? \t : Print this helpful text.\n"
				+ "<expression>: Evaluate the expression."
				+ "Expressions can be integers, floats, strings (surrounded in double quotes) or function\n"
				+ "calls of the form '(identifier {expression}*)'");
	} 
    
	/**
	 * Prints the synopsis of what the program can do and what commands it may take.
	 */
	public static void printSynopsis(){
		System.out.println("");
		System.out.println("Synopsis:\n  methods\n  methods { -h | -? | --help }+\n  methods {-v --verbose}* <jar-file> [<class-name>]");
		System.out.println("Arguments:\n  <jar-file>:   The .jar file that contains the class to load (see next line).\n  <class-name>: The fully qualified class name containing public static command methods to call. [Default=\"Commands\"]");
		System.out.println("Qualifiers:\n  -v --verbose: Print out detailed errors, warnings, and tracking.\n  -h -? --help: Print out a detailed help message.");
		System.out.println("Single-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.");
	}
	
	/**
	 * This function prints the synopsis with the additional information lines, this function is used when the user asks for help in
	 * one of his qualifiers
	 */
	public static void printSummary(){
		System.out.println("");
		System.out.println("Synopsis:\n  methods\n  methods { -h | -? | --help }+\n  methods {-v --verbose}* <jar-file> [<class-name>]");
		System.out.println("Arguments:\n  <jar-file>:   The .jar file that contains the class to load (see next line).\n  <class-name>: The fully qualified class name containing public static command methods to call. [Default=\"Commands\"]");
		System.out.println("Qualifiers:\n  -v --verbose: Print out detailed errors, warnings, and tracking.\n  -h -? --help: Print out a detailed help message.");
		System.out.println("Single-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.");
		System.out.println("");
		System.out.println("This program interprets commands of the format '(<method> {arg}*)' on the command line, finds corresponding");
		System.out.println("methods in <class-name>, and executes them, printing the result to sysout.");
	}
	
	
	/**
	 * This function will check to see what arguments the user has provided for the program, and decide what to do with those given arguments
	 * @param args - arguments that the user has decided to put inside of the command line when running program
	 */
	public void argChecker(String[] args){
		try{
			//initialize variables
			int i; // for loop counter
			int b = 0; // for loop counter copy 
			boolean askForHelp = false; //help flag
			boolean askForVerbose = false; //verbose flag
			String tempString; //temporary string for current argument
			String[] userArguments;

			if (args[0].startsWith("-")){ //if user has a qualifier, handle them
				for (i = 0; i < args.length; i++){ //loop through arguments
					if (args[i].startsWith("--") || args[i].startsWith("-")){ //if the arg is a qualifier
						tempString = args[i]; //create temporary string variable set it to arg
						if (checkValidQual(tempString)){ //check if valid
							if (checkVerbQual(tempString)){ //check if it asks for verbose
								askForVerbose = true; //set verbose flag to true
							}
							if(checkHelpQual(tempString)){//check if it asks for help
								askForHelp = true; //set help flag to true
							}
						}
						else{ //otherwise qualifier is not valid
							if (args[i].startsWith("--")){
								System.err.println("Unrecognized qualifier: " + args[i] + "."); //print this error if it starts with "--"
							}
							else{ //otherwise it must start with "-"
								String errorChar = findWrongQual(args[i]); //find the char that is causing the error
								System.err.println("Unrecognized qualifier " + errorChar + "in " + args[i] + "."); //print error statement
							}
							System.exit(-1); //exit with proper exit code
						}
						b = i; //set b copy to i
					}
					else{
						i = args.length + 1; //break out of loop
					}
				}
				b++; //b is now on the first argument that isnt a qualifier;
				
				if (args.length == b + 2){ //check if the length of args = b + 2
					userArguments = new String[] {args[b], args[b+1]}; //two arguments
				}
				else if (args.length == b + 1){//check if the length of args = b + 1
					userArguments = new String[] {args[b]}; //one argument
				}
				else if (args.length == b){//check if the length of args = b
					userArguments = new String[] {}; //no arguments
				}
				else{ //otherwise, user must have entered too many arguments
					userArguments = new String[] {}; // too many arguments, initialize anyway so we always have it initialized
					System.err.println("This program takes at most two command line arguments."); //print error statement
					printSynopsis(); //print synopsis
					System.exit(-2); //exit with proper code
				}
				
				if (askForHelp){ //check help flag
					if (userArguments.equals(null)){ //if the user entered no other arguments
						printSummary(); //print summary 
						System.exit(0);//quit
					}
					else{//otherwise the user must have entered an argument with the help qualifier, this causes an error
						System.err.println("Qualifier --help (-h, -?) should not appear with any command-line arguments."); //print error
						printSynopsis(); //print synopsis
						System.exit(-4); //exit with proper code
					}
				}
				if (askForVerbose){ //check verbose flag
					Debug.setIsVerbose(true); //set Verbose to true
					System.out.println("Verbose on"); //print verbose on
				}

			}
			else{ //this else happens if user does not enter any qualifiers
				if (args.length == 0){ //no arguments given
					userArguments = new String[] {}; //initialize userArguments as empty
				}
				else if (args.length == 1){ //1 arg
					userArguments = new String[] {args[0]}; //initialize to one arg array
				}
				else if (args.length == 2){ // 2 arg
					userArguments = new String[] {args[0], args[1]};//initialize to two arg array
				}
				else{ //too many arguments
					userArguments = new String[] {}; //initialize userArguments array anyway so that it is always defined in every case, I know, its dumb.
					System.err.println("This program takes at most two command line arguments."); //print error
					printSynopsis(); //print synopsis
					System.exit(-2); //exit with proper code
				}
			}
			
			if (userArguments.length == 0){ //if the arguments length is 0
				printSynopsis(); //print the synopsis and then exit with no special code
				System.exit(0); 
			}
			else if (userArguments.length == 1){ //check if arguments length is 1
				if (userArguments[0].contains(".jar")){ //if it is, we check if that one arg inside of it is a mandatory jar file
					r.setUpReflection(userArguments[0], "Commands"); //send args to reflection
					mainMenu(); // go to mainMenu
				}
				else{//otherwise the user did not enter a jar file
					System.err.println("This program requires a jar file as the first command line argument (after any qualifiers).");//print error message
					printSynopsis();//print program info
					System.exit(-3);//exit with proper code
				}
			}
			else{
				r.setUpReflection(userArguments[0], userArguments[1]);//otherwise, user must have entered 2 arguments (Too many arguments has already been handled))
				mainMenu(); //go to main menu
			}

		}
		catch(Exception e){ //catch exception
			if (Debug.isVerbose) { //check if verbose is set
				e.printStackTrace();			//prints out specified stack trace from the given error
				mainMenu();//returns back to main menu
			}
			mainMenu();//if we reach here, verbose is off, so we just go back to the main menu normally
		}
		mainMenu(); //go to main menu
	}
	
	
	
	/**
	 * This function will check to see if a qualifier from the command line is valid
	 * @param qualifier
	 * @return true if valid qualifier, false otherwise
	 */
	public static boolean checkValidQual(String qualifier){
		if (qualifier.toLowerCase().equals("--verbose")){ //check if arg == "--verbose"
			return true; //if the qualifier is --verbose, this is valid
		}
		else if (qualifier.toLowerCase().equals("--help")){ //check if qualifier is --help
			return true;// return true if it is --help
		}
		String validChars = "?vh-"; //initialize string of valid chars that you can have in a qualifier
		for (int i = 0; i < qualifier.length(); i++){ //go through each character inside of the qualifier
			char indexChar = qualifier.charAt(i); //current char inside of the qualifier
			String indexString = Character.toString(indexChar); //convert char to a 1 element string
			if (!validChars.contains(indexString)){ //check if the validChars array doesnt have the indexString
				return false; //if so, invalid
			}
		}
		return true; //otherwise return true;
	}
	
	/**
	 * This function will check to see if the user has entered "--help", "?" or "-h" as a qualifier command line argument
	 * @param args
	 * @return true if user has asked for help, false otherwise
	 */	
	public static boolean checkHelpQual(String qualifier){
		if (qualifier.equalsIgnoreCase("--help")){ //check if qualifier is --help
				return true; //return true
		}
		else if (qualifier.equals("?")){ //check if qualifier is ?
			return true; //return true 
		}
		else if(!qualifier.startsWith("--")){ //check if qualifier doesnt start with --
			if (qualifier.startsWith("-")){ //check if qualifier starts with - instead. 
				if (qualifier.contains("h")){ //check if qualifier has "h" inside of it
					return true; //return true if it does
				}
			}
			
		}
		return false; //otherwise we return false
	}
	
	
	/**
	 * This function will check to see if the user has entered "--verbose", or "-v" as a qualifier command line argument
	 * @param qualifier
	 * @return true if user has asked for verbose, false otherwise
	 */	
	public static boolean checkVerbQual(String qualifier){
		if (qualifier.equalsIgnoreCase("--verbose")){ //check if qualifier is --verbose
				return true; //return true
		}
		else if(qualifier.contains("v")){ //check if my qualifier contains v in it
			if (qualifier.startsWith("-")){ //if it contains v in it and it isnt "--verbose" (handled earlier), then it must start with "-"
				if(!qualifier.startsWith("--")){ //check if it doesnt start with --
					return true; //return true
				}
			}
		}
		return false; //otherwise return false

	}
	
	/**
	 * This function will find a an invalid character inside of the first user argument
	 * @param arg first argument that the user entered
	 * @return errorStr Invalid char the the user entered as an argument in string form
	 */
	public static String findWrongQual(String arg){
		String errChar = ""; //initialize errChar string to empty strng
		String validString = new String("-vh?"); //initialize validString 
		for (int x = 0; x < arg.length(); x++){ //loop through qualifier
			String myString = Character.toString(arg.charAt(x));//initialize myString to the current char within the qualifier
			if (!validString.contains(myString)){ //check if validStrings doesnt contain the character
				errChar = myString; //if so, that char is the error causing character
			}
		}
		return errChar; //return that errorString
	}
	
	
	/**
	 * This function is where the user will spend most of his time, this is where the user has different options to do things such as
	 * - printing function list
	 * -
	 */
	public void mainMenu(){
		Scanner keyboard = new Scanner(System.in); //create scanner
		Parser p = new Parser(); //create parser object
		ParseTree finalAnswer = null;//set finalAnswer to null
		Reflection ref = new Reflection(); //create reflection object
		boolean keepRunningParser = true; //loop boolean
		
		if (Debug.isVerbose) { //check if verbose is on
			System.out.println("<<< mainMenu() >>>"); //if verbose is on, let user know he/she is in mainMenu function by printing
		}
		
		startMessage();	// start up message
		while (keepRunningParser == true) { //while loop depends on the boolean
			System.out.print("> "); //print this to show that the user needs to provide an input
			String userIn = keyboard.nextLine(); //get input
			if (userIn.equals("q")) {			//quit if user input is q
				keepRunningParser = false; //turn keepRunningParser off so that we quit program and exit this main menu loop.
			}
			else if (userIn.equals("v")) {	//If user input is v, we toggle verbose
				if (Debug.isVerbose() == false) {	// check if verbose is off
					Debug.setIsVerbose(true);		//if verbose is off, we toggle it by turning it on
					System.out.println("Verbose on");
				} 
				else {
					Debug.setIsVerbose(false);			//otherwise, verbose must be on, so we just turn it off
					System.out.println("Verbose off");
				}
			} 
			else if (userIn.equals("f")) {		//If user input is f, we display the functions
				r.printFuncalls(r.o); //print function list for the given jar and class
				
				
			} 
			else if (userIn.equals("?")){
				startMessage();	
			}
			else {
					try{
						finalAnswer = p.parseLine(userIn); //pass user input int parseLine
					
						Node n = finalAnswer.head;
						
						Expr expr = (Expr) n.getExpression();
						
						Funcall f = expr.getFunCall();
						Expr[] args = f.expr_set;
						Value v = null;
						try {
							v = r.funCall(expr);
							if (v != null)
							{
								System.out.println(v);
							}
							else {
								System.out.println("Issue with the functioncall"); 	//CHANGE THIS BEFORE SENDING
							}
						} catch (InvalidFunctionCallException e) {
							int pos = (finalAnswer.head.findExpression(new ParseGrammer().new Expr(f)));
							System.out.println(e.toString() + pos);
							System.out.println(userIn);
							for(int i = 0; i < pos; i++)
								System.out.print('-');
							System.out.println('^');
							// TODO Auto-generated catch block
							if(Debug.isVerbose)
							{
								e.printStackTrace();
							}
						}
						 
						
						//System.out.println(finalAnswer.toString()); //take final answer and print it while converting it to a string

					}
					catch(ParseException e){ //exception handling
						System.out.println(e.getMessage());
						System.out.println(userIn);
						for (int i = 0; i < e.getErrorOffset(); i++){
							System.out.print("-");
						}
						System.out.println("^");
						if (Debug.isVerbose){
							e.printStackTrace();
						}
					}
					catch(Exception e2){
						
					}
					
			}
		}
		keyboard.close(); //close scanner object
			
	}

	
}
