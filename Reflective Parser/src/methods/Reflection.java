package methods;
import java.lang.reflect.*;
import java.io.*;
import java.net.*;
/**
 * 
 * @author Bernie Mayer
 *
 */
public class Reflection {
	
	public static void main(String[] args) {
		
		
		//loadClass("Commands.java");
		
	}
	/**
	 * 
	 * @param o 
	 */
	
	static void showConstructors(Object o)
	{
		Class c = o.getClass();
		Constructor[] theConstructors = c.getConstructors();
		for (int i = 0; i < theConstructors.length; i++) {
			System.out.println("( ");
			Class[] parameterTypes = 
					theConstructors[i].getParameterTypes();
			for (int k = 0; k < parameterTypes.length; k++){
				String parameterString = parameterTypes[k].getName();
				System.out.println(parameterString + " ");
			}
			System.out.println(")");
		}
	}
	
	/**
	 * 
	 * @param o
	 */
		
	static void printFieldNames(Object o) {
		Class c = o.getClass();
		Field[] publicFields = c.getFields();
		
		for (int i = 0; i < publicFields.length; i++){
			String fieldName = publicFields[i].getName();
			Class typeClass = publicFields[i].getType();
			String fieldType = typeClass.getName();
			
		
		}
		
		}
	/**
	 * 
	 * @param o
	 */
	static void printFuncalls(Object o){
		Class c = o.getClass();
		Method[] methods = c.getMethods();
		
		for (int i = 0; i < methods.length; i++){
			String methodString = methods[i].getName();
			int modifier = methods[i].getModifiers();
			System.out.print("(" + methodString + " ");
			
			if (Modifier.isStatic(modifier))
				System.out.print("Static");
			
			String returnString = methods[i].getReturnType().getName();
			
			Class[] parameterTypes = methods[i].getParameterTypes();
			for (int k = 0; k < parameterTypes.length; k++ ){
				
				String parameterString = parameterTypes[k].getName();
				System.out.print(parameterString + " ");
			}
			System.out.println("): " + returnString);
			
			
		}
	}
	/**
	 * 
	 * @param filename
	 */
	static void loadClass(String filename){
		try {
			//RandomAccessFile r = new RandomAccessFile(filename, "r");
			File f = new File(filename);
			URL url = f.toURI().toURL();
			URLClassLoader sysLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			Class<?> sysclass = URLClassLoader.class;
			Class<?> parameters = null;
			Method method = null;
			try {
				method = sysclass.getDeclaredMethod("addURL", parameters);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			method.setAccessible(true);
			Object u = null;
			method.invoke(sysLoader, new Object[]{ u });
			//printFuncalls(r);
			
		} catch (IOException e){
			System.out.println(e);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	}
		
		
		
	


