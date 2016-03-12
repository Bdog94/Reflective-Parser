package methods;

//import java.net.URL;
//import java.net.URLClassLoader;
//import java.util.Enumeration;
import java.util.Scanner;
//import java.util.jar.JarEntry;
//import java.util.jar.JarFile;
//import java.util.zip.ZipEntry;

public class CmdLine {
	
	
	String className;
	String fileName;
	Reflection r;
	
	
	public CmdLine(){
		Reflection r = new Reflection();
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
    
/*	
	
	/**
	 * This method will display the functions that the user may use in the command line
	 *
	public void functionList() {
		System.out.println("Function List:\n"
				+ "(add string string) : string\n"
				+ "(add float float) : float\n"
				+ "(add int int) : int\n"
				+ "(sub float float) : float\n"
				+ "(sub int int) : int\n"
				+ "(div int int) : int\n"
				+ "(div float float) : float\n"
				+ "(mul float float) : float\n"
				+ "(mul int int) : int\n"
				+ "(inc float) : float\n"
				+ "(inc int) : int\n"
				+ "(dec int) : int\n"
				+ "(dec float) : float\n"
				+ "(len string) : int\n");
	}
*/	
	
	/**
	 * Prints the synopsis of what the program can do and what commands it may take.
	 */
	public static void printSummary(){
		System.out.println("Synopsis:\n  methods\n  methods { -h | -? | --help }+\n  methods {-v --verbose}* <jar-file> [<class-name>]");
		System.out.println("Arguments:\n  <jar-file>:   The .jar file that contains the class to load (see next line).\n  <class-name>: The fully qualified class name containing public static command methods to call. [Default=\"Commands\"]");
		System.out.println("Qualifiers:\n  -v --verbose: Print out detailed errors, warnings, and tracking.\n  -h -? --help: Print out a detailed help message.");
		System.out.println("Single-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.");
	}
	
	
/*	
	/**
	 * This is the primary part of the compiler after correct commands have been called from the class. 
	 * This will loop infinitely until key 'q' has been pressed and it quits the function
	 * 
	 *
	public static void mainMenu() {
		Scanner keyboard = new Scanner(System.in);
		Parser p = new Parser();
		ParseTree finalAnswer = null;
		boolean keepRunningParser = true;
		//if verbose is on, track code
		if (Debug.isVerbose) {
			System.out.println("<<< mainMenu() >>>");
		}
		
		startMessage();	//runs the startup message
		while (keepRunningParser == true) {
			System.out.print("> ");
			String userIn = keyboard.nextLine();
			if (userIn.equals("q")) {			//quit the program if user input is 'q'
				keepRunningParser = false;
			} else if (userIn.equals("v")) {	//If user input = 'v', we will toggle verbose mode
				if (Debug.isVerbose() == false) {	// check to see if verbose mode is off
					Debug.setIsVerbose(true);		//if verbose mode is off, we toggle it and turn it on
				} else {
					Debug.setIsVerbose(false);			//otherwise just turn it off
				}
			} else if (userIn.equals("f")) {		//If the user inputs 'f', we display the function list
				//functionList();
				r.printFuncalls(r.o);
				
				
			} else {
				boolean validBrackets = checkBrackets(userIn); //first checks the brackets on the input
				boolean validInput = invalidFunc(userIn); //then checks if the input is valid at all
				if (validBrackets == false) {
					System.out.println("Matching braces error.");
				} else if (validInput == false) {
					System.out.println("Input to be calculated is invalid.");
				} else { //if both tests pass, then input will be processed through recursion tree
					try{
						finalAnswer = p.parseLine(userIn);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					if (finalAnswer != null) {
						System.out.println(finalAnswer);
						//Errors.setInput("userInput"); //gets input from user for error handling
					}
				}
			}
			
		}
	}
		
*/
	
	/*
		
		/**
		 * This method checks to see if the input that a user entered is valid
		 * This function will check many things, such as the balance of brackets
		 * or if the user has used too many brackets
		 * Eg. ((2+2)) is valid, but it has too many brackets and will return true
		 * @param userIn A string that the user inputed via the command line
		 * @return true if invalid, false if valid
		 *
	public static boolean invalidFunc(String userIn){
		//if verbose is on, track code
		if (Debug.isVerbose) {
			System.out.println("<<< invalidFunc() >>>");
		}
		String[] functionList = {"add", "sub", "mul", "div", "len", "dec"}; // List of strings containing out function names
		char[] userInAsArray = userIn.toCharArray(); //Convert userInput string into a char list to check bracket validity
		// Alphabet pattern to be matched against for condition check downstream (is a part of the input illegal after a left parenthesis?)
		char[] alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			
		// Simple case: check if one function call is balanced and has proper syntax
		if (checkBrackets(userIn) == true) /*&& (tree.funcall(userIn))* {
			return false;
		} 
			
		// Negative definition of a function call. If a function name from fList is not found at all, immediately return false
		for (String f : functionList) {
			if (!userIn.contains(f)) {
				return true;
				// Check if a number or alphabetical character comes after a left parenthesis and break the program
			} 
			else{
				for (int i = 0; i < Integer.MAX_VALUE; i++) {
					for (int j = 0; j < userInAsArray.length; j++ ) {
						if (userInAsArray[j] == '(' && (userInAsArray[j+1] == i || userInAsArray[j+1] == alphabet[j])) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
/*
 	
/*

	/**
	 * This function is to check the balance of brackets with a string, it checks to see if a right brace 
	 * is before a left brace, if that is the case, it will return false. Otherwise return true
	 * 
	 * @param userIn calculation user has entered 
	 * @return true if brackets in calculation are valid, false otherwise
	 *
	public static boolean checkBrackets (String userIn) {
		//if verbose is on, track code
		if (Debug.isVerbose) {
			System.out.println("<<< checkBrackets() >>>");
		}
		
		int balancedBraces = 0;
		char[] userIn_charArray = userIn.toCharArray();
		
		//for every ( add 1, for every ) subtract one. If it ever goes below 0, break and return false.
		for (int i = 0; i < userIn_charArray.length; i++){
			if (userIn_charArray[i] == '(') {
				balancedBraces++;
			} else if (userIn_charArray[i] == ')') {
				balancedBraces--;
				if (balancedBraces < 0) {
					return false;
				}
			}
		}
		if (balancedBraces != 0) {
			return false; //if by the end the variable is NOT 0, return false.
		}
		return true;
	}
/*

	/**
	 * 
	 * @param args - System arguments given in command line
	 *
	*/
	
	
	/*
	public void argCheck(String[] args){
		//LoadedJar loader = new LoadedJar();
		//This block of code is placed in a try and catch block to handle possible errors
		
		try {
			
			char[] inputAsArray = null;

			if (args.length == 0) {
				printSummary();
			}
			else if ((args[0].equals("-h") || args[0].equals("-?") || args[0].equals("--help")) && (args.length == 1)) {
				printSummary();
				System.out.println("\nThis program interprets commands of the format '(<method> {arg}*}' on the command line, finds corresponding methods in <class-name>, and executes them, printing the result to sysout. Terminate with ^D or \"exit\"");
			} 
			else if ((args[0].equals("-h") || args[0].equals("-?") || args[0].equals("--help")) && (args.length != 1)) {
				System.out.println("Qualifier --help (-h, -?) should not appear with any command-line arguments.");
				printSummary();
			} 
			else if (((args[0].equals("-v") || args[0].equals("--verbose")) && args[1].contains(".jar") && args.length == 3)
					|| (args[0].equals("-v") || args[0].equals("--verbose")) && args[1].contains(".jar") && args.length == 2){
				Debug.setIsVerbose(true); //set verbose to true if first argument is -v or --verbose, and check if a jar file is in the arguments.
				System.out.println("Running in verbose mode...");	
				
				
				if (jarValid(args[1])) { // Check if jar file is valid
					if (args.length == 2) {  // Check if defaulted "Commands" class is in the jar file
						if (classValid(args[1], "Commands")) {	//check if class has methods
								//call main menu 
								r.setUpReflection(args[1], "Commands");
								mainMenu();
						} 
						else {
							System.out.println("Could not find class: Commands"); //otherwise commands havnt been found
						}
					} 
					else {
						if (classValid(args[1], args[2])) { // Check if class argument is in the jar file
							r.setUpReflection(args[1], args[2]);
							mainMenu(); //call main menu
							// send kyle reflection object
						} 
						else {
							System.out.println("Could not find class: " + args[2]); //could not find class otherwise
						}
					}
				} 
				else {
					System.out.println("Could not find jar file: " + args[1]); // jar file not found 
				}
			//if calls for merely the jar file
			} 
			
			
			else if ((args[0].contains(".jar") && args.length == 2) || (args[0].contains(".jar") && args.length == 1)) {
				Debug.setIsVerbose(false); //verbose mode set to false
				if (jarValid(args[0])) { // Check if jar file is valid
					if (args.length == 1) { // Check if defaulted "Commands" class is in the jar file
						if (classValid(args[0], "Commands")) { 
							mainMenu(); //call main menu
						} 
						else {
							System.out.println("Could not find class: Commands"); //otherwise class "commands" not found
						}
					} 
					else {
						if (classValid(args[0], args[1])) { // Check if class argument is inside of the jar file
							mainMenu(); //call main menu
						} 
						else {
							System.out.println("Could not find class: " + args[1]); //Given class not found
						}
					}
				} 
				else {
					System.out.println("Could not find jar file: " + args[0]); // could not find given jar file
				}
			//If none of the above apply, do this
			} 
			else {
				inputAsArray = args[0].toCharArray(); // move argument into an an array of chars
				if (Character.valueOf(inputAsArray[0]) == '-') {
					System.out.println("Unrecognized qualifier '" + inputAsArray[1] + "' in '" + args[0] + "'"); //unrecognized qualifier
					printSummary(); // print summary of program
				} 
				else {
					System.out.println("Unrecognized qualifier '" + inputAsArray[0] + "' in '" + args[0] + "'");// unrecognized qualifier
					printSummary(); //print summary
				}
			}
		} 
		catch (Exception e){
			//If verbose is on it will print it the stack trace of the given error 
			if (Debug.isVerbose) { //error begins
				//Errors.beginError();
				e.printStackTrace(/*System.out*);				//prints out specified stack trace from the given error
				//returns back to normal execution
				mainMenu();
			}
			//returns back to normal execution if verbose mode is off 
			mainMenu();
		}
		//otherwise returns back to normal execution
		mainMenu();
		
    }

	*/
	
	/**
	 * This function will check to see what arguments the user has provided for the program, and decide what to do with those given arguments
	 * @param args - arguments that the user has decided to put inside of the command line when running program
	 */
	public void argChecker(String[] args){
		try{
			if ((args.length != 0) || (args.length != 2) || (args.length != 3)){
				printSummary();
			}
			if (checkHelp(args)){
				printSummary();
			}
			else if (args.length == 2){
				r.setUpReflection(args[0], args[1]);
				// send kyle reflection object
				mainMenu();
			}
			else if (checkVerb(args) && (args.length == 3)){
				Debug.setIsVerbose(true);
				System.out.println("Verbose is on");
				r.setUpReflection(args[1], args[2]);
				// send kyle reflection object
				mainMenu();
			}
			else{
				System.out.println("Invalid");
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
	}
	
	/**
	 * This function will check to see if the user has entered "--help", "?" or "-h" as the first command line argument
	 * @param args
	 * @return true if user has asked for help, false otherwise
	 */
	public static boolean checkHelp(String[] args){
		int index = args[0].indexOf('h');
		if (args[0] == "--help"){
			return true;
		}
		else if (args[0] == "?"){
			return true;
		}
		else if ((index != -1) && (args.length != 1)){
			System.out.println("Help argument should not come with any other arguments");
			return true;
		}
		else if ((index != -1) && (args.length == 1)){
			return true;
		}
		return false;
	}
	
	public static boolean checkVerb(String[] args){
		int index = args[0].indexOf('v');
		if (args[0] == "--verbose"){
			return true;
		}
		else if (index != -1){
			return true;
		}
		return false;
	}
	
	
	public void mainMenu(){
		Scanner keyboard = new Scanner(System.in);
		Parser p = new Parser();
		ParseTree finalAnswer = null;
		boolean keepRunningParser = true;
		//if verbose is on, track code
		if (Debug.isVerbose) {
			System.out.println("<<< mainMenu() >>>");
		}
		
		startMessage();	//runs the startup message
		while (keepRunningParser == true) {
			System.out.print("> ");
			String userIn = keyboard.nextLine(); //get user input
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
						finalAnswer = p.parseLine(userIn);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					if (finalAnswer != null) {
						System.out.println(finalAnswer);
						//Errors.setInput("userInput"); //gets input from user for error handling
					}
			}
		}
		keyboard.close();
			
	}
	

	
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
	
/*	
	
	/**
	 * Checks if jar file is in the class path and is valid
	 * @param jarName is the name of the jar file
	 * @return true if jar file is acceptable, false otherwise
	 * Based on one of the answers from http://stackoverflow.com/questions/20152195/how-to-check-if-a-jar-file-is-valid
	 *
	public static boolean jarValid(String jarName) {
		ZipEntry entry = null;
		try {
		        JarFile myJar = new JarFile(jarName); // create my jar file based on the string parameter
		        Enumeration<? extends ZipEntry> e = myJar.entries(); // Create my entries for the jar file
		        while(e.hasMoreElements()) { // Loop of entries in the jarfile
		            entry = e.nextElement();
		        }
		        return true; //Return true if no exception has been thrown
			} catch(Exception myException) {
		        return false; //If any exceptions have been thrown, we know that the jar file is not valid
			}
	}
	
	
	/**
	 * Checks if class is in the jar file and is valid
	 * @param jarName is the name of the jar file, className is the name of the class
	 * @return true if class is acceptable, false otherwise
	 * Based on one of the answers from http://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
	 *
	public static boolean classValid(String jarName, String className) {
		try {
			JarFile jarFile = new JarFile(jarName); // Creates jarfile from parameter
			Enumeration e = jarFile.entries(); // Creates entries from the jarfile
			URL[] urls = { new URL("jar:file:" + jarName + "!/") }; // Creates jarfile URL
			URLClassLoader cl = URLClassLoader.newInstance(urls); // Creates classloader
		    while (e.hasMoreElements()) { // Loop of all classes from the jarfile
		        JarEntry je = (JarEntry) e.nextElement(); // Jarfile entries
		        if(je.isDirectory() || !je.getName().endsWith(".class")){ // Checks only for classes
		            continue;
		        }
		        String classes = je.getName().substring(0,je.getName().length()-6); // Gets class name
		        classes = classes.replace('/', '.'); // Replaces slashes to periods to access class name
		        if(classes.equals(className)) { // Checks if the current class name is the same as the class name argument
		        	return true; // Returns true if argument class name exists
		        }
		    }
		    return false; // Otherwise we return false because class name argument is not found
		} catch(Exception ex) {
			return false; // If exceptions somehow occur we return false
		}
	}

	
	
*/	
	
}
