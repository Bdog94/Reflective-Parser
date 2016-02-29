package methods;

import java.net.URLClassLoader;
import java.net.MalformedURLException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class LoadedJar {
	
	private Class<?> classFromLoad;
	
	/**
	 * In this method, we load the jar file in order to be able to access the classes within it. 
	 * This method also loads the classes inside of the jar file, so that we can access the methods within the class.
	 * 
	 * @param nameOfFile - Name of the given jar 
	 * @param nameOfClass - Name of the class inside of the jar file
	 * 
	 * @throws ClassNotFoundException
	 * @throws MalformedURLException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void loadJarClass(String nameOfFile, String nameOfClass) throws ClassNotFoundException, 
	MalformedURLException, SecurityException, NoSuchMethodException, InvocationTargetException, 
	IllegalArgumentException, IllegalAccessException {
		 
		String className, fileName;
		
		fileName = nameOfFile;	//Set the fileName to the parameter nameOfFile
		File myFile = new File(fileName); 	//Create new file with nameOfFile as parameter (file name)
		
		URL pathOfJarFile = new URL("jar", "","file:" + myFile.getAbsolutePath()+"!/");   //create URL for the jar file path 

		className = nameOfClass; 	//Initialize the className string as the parameter nameOfClass
		
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {pathOfJarFile}); //create classLoader of type URLClass loader, with jar URL as parameters  
		classFromLoad = classLoader.loadClass(className);	//class from Load is our class that we use.
	}
	
	/**
	 * This method will return the class from the load
	 * @return methods for the class thats been loaded
	 */
	public Class<?> getClassFromLoad() {
		return classFromLoad;
	}

}
