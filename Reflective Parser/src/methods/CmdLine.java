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
				for (i = 0; i < args.length; i++){
					if (args[i].startsWith("--") || args[i].startsWith("-")){
						tempString = args[i];
						if (checkValidQual(tempString)){
							if (checkVerbQual(tempString)){
								askForVerbose = true;
							}
							if(checkHelpQual(tempString)){
								askForHelp = true;
							}
						}
						else{
							if (args[i].startsWith("--")){
								//Unrecognized qualifier: --<longQualifier>.
								System.err.println("Unrecognized qualifier: " + args[i] + ".");
							}
							else{
								String errorChar = findWrongQual(args[i]);
								System.err.println("Unrecognized qualifier " + errorChar + "in " + args[i] + ".");
							}
							System.exit(-1);
						}
						b = i;		
					}
					else{
						i = args.length + 1; //break out of loop
					}
				}
				b++; //b is now on the first argument that isnt a qualifier;
				
				if (args.length == b + 2){
					userArguments = new String[] {args[b], args[b+1]}; //two arguments
				}
				else if (args.length == b + 1){
					userArguments = new String[] {args[b]}; //one argument
				}
				else if (args.length == b){
					userArguments = new String[] {}; //no arguments
				}
				else{
					userArguments = new String[] {}; // too many arguments, initialize anyway so we always have it initialized
					System.err.println("This program takes at most two command line arguments.");
					printSynopsis();
					System.exit(-2);
				}
				
				if (askForHelp){ //check flags
					if (userArguments.equals(null)){
						printSummary();
						System.exit(0);
					}
					else{
						System.err.println("Qualifier --help (-h, -?) should not appear with any comand-line arguments.");
						printSynopsis();
						System.exit(-4);
					}
				}
				if (askForVerbose){
					Debug.setIsVerbose(true);
					System.out.println("Verbose on");
				}

			}
			else{ //this else happens if user doesnt enter any qualifiers
				if (args.length == 0){
					userArguments = new String[] {};
				}
				else if (args.length == 1){
					userArguments = new String[] {args[0]};
				}
				else if (args.length == 2){
					userArguments = new String[] {args[0], args[1]};
				}
				else{ //too many arguments
					userArguments = new String[] {};
					System.err.println("This program takes at most two command line arguments.");
					printSynopsis();
					System.exit(-2);
				}
			}
			
			if (userArguments.length == 0){
				printSynopsis();
				System.exit(0); //Am I allowed?
			}
			else if (userArguments.length == 1){
				if (userArguments[0].contains(".jar")){
					r.setUpReflection(userArguments[0], "Commands");
					mainMenu();
				}
				else{
					System.err.println("This program requires a jar file as the first command line argument (after any qualifiers).");
					printSynopsis();
					System.exit(-3);
				}
			}
			else{
				r.setUpReflection(userArguments[0], userArguments[1]);
				mainMenu();
			}

		}
		catch(Exception e){
			//If verbose is on it will print it the stack trace of the given error 
			if (Debug.isVerbose) { //error begins
				e.printStackTrace();			//prints out specified stack trace from the given error
				//returns back to normal execution
				mainMenu();
			}
			//returns back to normal execution if verbose mode is off 
			mainMenu();
		}
		mainMenu();
	}
	
	
	
	/**
	 * This function will check to see if a qualifier from the command line is valid
	 * @param qualifier
	 * @return true if valid qualifier, false otherwise
	 */
	public static boolean checkValidQual(String qualifier){
		if (qualifier.toLowerCase().equals("--verbose")){
			return true;
		}
		else if (qualifier.toLowerCase().equals("--help")){
			return true;
		}
		String validChars = "?vVhH-";
		for (int i = 0; i < qualifier.length(); i++){
			char indexChar = qualifier.charAt(i);
			String indexString = Character.toString(indexChar);
			if (!validChars.contains(indexString)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This function will check to see if the user has entered "--help", "?" or "-h" as a qualifier command line argument
	 * @param args
	 * @return true if user has asked for help, false otherwise
	 */	
	public static boolean checkHelpQual(String qualifier){
		if (qualifier.equalsIgnoreCase("--help")){
				return true;
		}
		else if (qualifier.equals("?")){
			return true;
		}
		else if(!qualifier.startsWith("--")){
			if (qualifier.startsWith("-")){
				if (qualifier.toLowerCase().contains("h")){
					return true;
				}
			}
			
		}
		return false;
	}
	
	
	/**
	 * This function will check to see if the user has entered "--verbose", or "-v" as a qualifier command line argument
	 * @param qualifier
	 * @return true if user has asked for verbose, false otherwise
	 */	
	public static boolean checkVerbQual(String qualifier){
		if (qualifier.equalsIgnoreCase("--verbose")){
				return true;
		}
		else if(qualifier.toLowerCase().contains("v")){
			if (qualifier.startsWith("-")){
				if(!qualifier.startsWith("--")){
					return true;
				}
			}
		}
		return false;

	}
	
	/**
	 * This function will find a an invalid character inside of the first user argument
	 * @param arg first argument that the user entered
	 * @return errorStr Invalid char the the user entered as an argument in string form
	 */
	public static String findWrongQual(String arg){
		String errChar = "";
		String validString = new String("-vVhH");
		for (int x = 0; x < arg.length(); x++){
			String myString = Character.toString(arg.charAt(x));
			if (!validString.contains(myString)){
				errChar = myString;
			}
		}
		String errorStr = new String(errChar);
		return errorStr;
	}
	
	
	/**
	 * This function is where the user will spend most of his time, this is where the user has different options to do things such as
	 * - printing function list
	 * -
	 */
	public void mainMenu(){
		Scanner keyboard = new Scanner(System.in);
		Parser p = new Parser();
		ParseTree finalAnswer = null;
		Reflection ref = new Reflection();
		boolean keepRunningParser = true;
		if (Debug.isVerbose) { //track code if verbose is on
			System.out.println("<<< mainMenu() >>>"); //print main menu
		}
		
		startMessage();	// start up message
		while (keepRunningParser == true) { 
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
								System.out.println("Function call returned a null Value."); 	//CHANGE THIS BEFORE SENDING
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
						e2.printStackTrace();
					}
					
			}
		}
		keyboard.close(); //close scanner object
			
	}

	
}
