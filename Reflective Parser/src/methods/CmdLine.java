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
	public static void printSummary(){
		System.out.println("");
		System.out.println("Synopsis:\n  methods\n  methods { -h | -? | --help }+\n  methods {-v --verbose}* <jar-file> [<class-name>]");
		System.out.println("Arguments:\n  <jar-file>:   The .jar file that contains the class to load (see next line).\n  <class-name>: The fully qualified class name containing public static command methods to call. [Default=\"Commands\"]");
		System.out.println("Qualifiers:\n  -v --verbose: Print out detailed errors, warnings, and tracking.\n  -h -? --help: Print out a detailed help message.");
		System.out.println("Single-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.");
	}
	
	
	/**
	 * This function will check to see what arguments the user has provided for the program, and decide what to do with those given arguments
	 * @param args - arguments that the user has decided to put inside of the command line when running program
	 */
	public void argChecker(String[] args){
		try{
			if (args.length == 0){
				printSummary();
			}
			if (args.length == 1){ //check if length of arg is 1
				if (!checkHelp(args) && !checkVerb(args)){ //if the length is 1 and the first argument doesnt ask for help or verbose.
					if (!args[0].contains(".jar")){ //check if the argument doesnt contains ".jar"
						System.err.println("This program requires a jar file as the first command line argument (after any qualifiers)."); 
						printSummary();
						System.exit(-3);
					}
					else{
						r.setUpReflection(args[1], "Commands"); //if we reach here, we know that ".jar" is in the file, so we set it up with commands
						mainMenu(); 
					}
				}
			}
			if ((args.length == 2) && args[1].contains(".jar")){ //if length of args is 2 and the 2nd arg contains ".jar"
				if (checkVerb(args)){ //check if user asks for verbose
					Debug.setIsVerbose(true); //set verbose to true if he/she does
				}
				if (checkHelp(args)){ //check if user asks for help
					printSummary(); //print summary if the user does ask for help
				}
				r.setUpReflection(args[1], "Commands"); //set reflection up with "Commands.java" as default class.
				mainMenu();
			
			}
			if ((args.length == 2 && !args[1].contains(".jar"))){ //chec kif the args has length 2 and it the 2nd arg doesnt contain ".jar"
				System.err.println("This program requires a jar file as the first command line argument (after any qualifiers).");
				printSummary();
				System.exit(-3);
			}
			if (args.length > 3){//if user enters more than 3 arguments
				System.err.print("This program takes at most two command line arguments.");
				printSummary();
				System.exit(-2);
			}
			if (checkValid(args) == false){ //if the first argument that the user entered is invalid
				if (args[0].startsWith("--")){ //check to see if the first arg starts with "--"
					System.err.println("Unrecognized qualifier: " + args[0]); 
					printSummary();
					System.exit(-1);
				}
				else{ //if we reach here, the first arg doesnt start with "--"
					String errorChar = findWrongQual(args[0]);
					System.err.println("Unrecognized qualifier '" + errorChar + "' in '" + args[0] + "'");
					printSummary();
					System.exit(-1);
				}
			}
			if(checkHelp(args) && args.length != 1){ //check to see if the user asks for help and it the arg length is not 1
				System.err.println("Qualifier --help (-h, -?) should not appear with any comand-line arguments.");
				printSummary();
				System.exit(-4);
			}
			if(checkHelp(args)){ //check to see if the user asks for  help
				printSummary(); //if user asks for help, give info
				System.exit(0);
			}
			if (checkVerb(args)){ //check to see if the user asks for verbose mode
				if (args.length == 3){ //if the args.length is 3, we can set up the reflection 
					r.setUpReflection(args[1], args[2]);
				}
				else{ //otherwise it is invalid
					System.err.println("Invalid"); //print invalid
					System.exit(0); //no special exit code
				}
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
 * This method will take the list of arguments given to the program and determine of they are valid, the arguments are valid if the first 
 * argument contains only v, V, h, H, or -. 	
 * @param args arguments user used when running program
 * @return true if first argument is valid, false otherwise
 */
	public static boolean checkValid(String[] args){
		if (args[0].startsWith("--")){
			if ((args[0].equals("--verbose")) || (args[0].equals("--help"))){
				return true;
			}
			else{
				return false;
			}
		}
		String validChars = "vVhH-";
		for (int i = 0; i < args[0].length(); i++){
			char indexChar = args[0].charAt(i);
			String indexString = Character.toString(indexChar);
			if (!validChars.contains(indexString)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This function will check to see if the user has entered "--help", "?" or "-h" as the first command line argument
	 * @param args arguments user has entered with the program in the command line
	 * @return true if user has asked for help, false otherwise
	 */
	public static boolean checkHelp(String[] myArg){
		if (myArg[0].equals("--help")){
			return true;
		}
		else if (myArg[0].equals("?")){
			return true;
		}
		else if((myArg[0].contains("h")) || (myArg[0].contains("H"))){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * This method will check to see if the user has v as 
	 * @param args arguments user has entered in the command line 
	 * @return true if user asks for verbose, false otherwise
	 */
	
	public static boolean checkVerb(String[] args){
		if (args[0].toLowerCase().equals("--verbose")){
			return true;
		}
		if (args[0].contains("v") || args[0].contains("V")){
			return true;
		}
		else{
			return false;
		}
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
				} 
				else {
					Debug.setIsVerbose(false);			//otherwise, verbose must be on, so we just turn it off
				}
			} 
			else if (userIn.equals("f")) {		//If user input is f, we display the functions
				r.printFuncalls(r.o); //print function list for the given jar and class
				
				
			} 
			else {
					try{
						finalAnswer = p.parseLine(userIn); //pass user input int parseLine
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
					if (finalAnswer != null) {
						
						Node n = finalAnswer.head;
						
						Expr expr = (Expr) n.getExpression();
						
						Funcall f = expr.getFunCall();
						Expr[] args = f.expr_set;
						Value v = null;
						try {
							v = r.funCall(expr);
						} catch (InvalidFunctionCallException e) {
							System.out.println("err"); //CHANGE THIS BEFORE SUBMISSION
							// TODO Auto-generated catch block
							//e.printStackTrace();
						}
						if (v != null)
						{
							System.out.println(v);
						} else {
							System.out.println("Issue with the functioncall"); 	//CHANGE THIS BEFORE SENDING
						}
						
						//System.out.println(finalAnswer.toString()); //take final answer and print it while converting it to a string
					}
			}
		}
		keyboard.close(); //close scanner object
			
	}

/*	
	
	public String getClassName(){
		return className;
	}
	public String getFileName(){
		return fileName;
	}
	public void setClassName(String newClassName){
		className = newClassName;
	}
	public void setFileName(String newFileName){
		fileName = newFileName;
	}
	*/	
	
}
