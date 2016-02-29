package methods;

import java.util.zip.ZipEntry;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.net.URL;
import java.util.jar.JarFile;
import java.net.URLClassLoader;


public class Driver {

	public static void main(String[] args) {
		
		argCheck(args);
		

	}
	
	
	
	
	/**
	 * Prints the synopsis of what the program can do and what commands it may take.
	 */
	public static void printSummary(){
		System.out.println("Synopsis:\n  methods\n  methods { -h | -? | --help }+\n  methods {-v --verbose}* <jar-file> [<class-name>]");
		System.out.println("Arguments:\n  <jar-file>:   The .jar file that contains the class to load (see next line).\n  <class-name>: The fully qualified class name containing public static command methods to call. [Default=\"Commands\"]");
		System.out.println("Qualifiers:\n  -v --verbose: Print out detailed errors, warnings, and tracking.\n  -h -? --help: Print out a detailed help message.");
		System.out.println("Single-char qualifiers may be grouped; long qualifiers may be truncated to unique prefixes and are not case sensitive.");
	}
	
	
	
	
	/**
	 * Checks if jar file is in the class path and is valid
	 * @param jarName is the name of the jar file
	 * @return true if jar file is acceptable, false otherwise
	 * Based on one of the answers from http://stackoverflow.com/questions/20152195/how-to-check-if-a-jar-file-is-valid
	 */
	public static boolean jarValid(String jarName) {
		try {
		        JarFile myJar = new JarFile(jarName); // create my jar file based on the string parameter
		        Enumeration<? extends ZipEntry> e = myJar.entries(); // Create my entries for the jar file
		        while(e.hasMoreElements()) { // Loop of entries in the jarfile
		            ZipEntry entry = e.nextElement();
		        }
		        return true; //Return true if no exception has been thrown
			} catch(Exception myException) {
		        return false; //If any exceptions have been thrown, we know that the jar file is not valid
			}
	}
	
	
	/**
	 * 
	 * @param args - System arguments given in command line
	 */
	
	public static void argCheck(String[] args){
		LoadedJar loader = new LoadedJar();
		Debug debugger = new Debug();
		
		//This block of code is embedded in a try catch to keep track of non-fatal errors
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
			//if calls for verbose mode + jar file (plus maybe) a class name
			} 
			else if (((args[0].equals("-v") || args[0].equals("--verbose")) && args[1].contains(".jar") && args.length == 3)
					|| (args[0].equals("-v") || args[0].equals("--verbose")) && args[1].contains(".jar") && args.length == 2){
				debugger.setIsVerbose(true);
				System.out.println("Running in verbose mode...");		
				if (jarValid(args[1])) { // Check if jar file is valid
					if (args.length == 2) {  // Check if defaulted "Commands" class is in the jar file
						if (isClassValid(args[1], "Commands")) {
							loader.loadJarClass(args[1], "Commands");
							//mainStuff.mainMenu();
						} 
						else {
							System.out.println("Could not find class: Commands");
						}
					} 
					else {
						if (isClassValid(args[1], args[2])) { // Check if class argument is in the jar file
							loader.loadJarClass(args[1], args[2]);
							//mainStuff.mainMenu();
						} 
						else {
							System.out.println("Could not find class: " + args[2]);
						}
					}
				} 
				else {
					System.out.println("Could not find jar file: " + args[1]);
				}
			//if calls for merely the jar file
			} 
			else if ((args[0].contains(".jar") && args.length == 2) || (args[0].contains(".jar") && args.length == 1)) {
				debugger.setIsVerbose(false);
				if (jarValid(args[0])) { // Check if jar file is valid
					if (args.length == 1) { // Check if defaulted "Commands" class is in the jar file
						if (isClassValid(args[0], "Commands")) {
							loader.loadJarClass(args[0], "Commands");
							//mainStuff.mainMenu();
						} 
						else {
							System.out.println("Could not find class: Commands");
						}
					} 
					else {
						if (isClassValid(args[0], args[1])) { // Check if class argument is in the jar file
							loader.loadJarClass(args[0], args[1]);
							//mainStuff.mainMenu();
						} 
						else {
							System.out.println("Could not find class: " + args[1]);
						}
					}
				} 
				else {
					System.out.println("Could not find jar file: " + args[0]);
				}
			//default output if none of the above apply
			} 
			else {
				inputAsArray = args[0].toCharArray();
				if (Character.valueOf(inputAsArray[0]) == '-') {
					System.out.println("Unrecognized qualifier '" + inputAsArray[1] + "' in '" + args[0] + "'");
					printSummary();
				} 
				else {
					System.out.println("Unrecognized qualifier '" + inputAsArray[0] + "' in '" + args[0] + "'");
					printSummary();
				}
			}
		} 
		catch (Throwable e1){
			//If verbose is on it will print ut the stack trace of the given error 
			if (Debug.isVerbose) { //error begins
				//Errors.beginError();
				//e1.printStackTrace(System.out);				//prints out specified stack trace from the given error
				//returns back to normal execution
				//mainStuff.mainMenu();
			}
			//returns back to normal execution if verbose mode is off 
			//mainStuff.mainMenu();
		}
		//otherwise returns back to normal execution
		//mainStuff.mainMenu();
		
    }
	
	
		
		
		/**
		 * Checks if class is in the jar file and is valid
		 * @param jarName is the name of the jar file, className is the name of the class
		 * @return true if class is acceptable, false otherwise
		 * Based on one of the answers from http://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
		 */
		public static boolean isClassValid(String jarName, String className) {
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

