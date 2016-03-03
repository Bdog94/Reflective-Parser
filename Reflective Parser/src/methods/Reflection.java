package methods;
import java.lang.reflect.*;
import java.io.*;
import java.net.*;

import methods.ParseGrammer.Expr;
import methods.ParseGrammer.Funcall;
import methods.ParseGrammer.Identifier;
import methods.ParseGrammer.Value;
/**
 * 
 * @author Bernie Mayer
 *
 */
public class Reflection {
	Class c;
	
	public static void main(String[] args) {
		
		LoadedJar lj = new LoadedJar();
		try {
			lj.loadJarClass("Commands.java", "Commands");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Class<?> c = lj.getClassFromLoad();
	
		printFuncalls(c);
		
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
	
	public Value funCall(Funcall f, Expr[] elem_set){
		
		Identifier methodIdentifier = f.ident;
		String identifier = methodIdentifier.id;
		c = String.class;
		Value[] vals = new Value[elem_set.length];
		int i = 0;
		try {
			for (Expr e:elem_set){
				if (e.containValue()){
					vals[i] = e.value;
					i++;
				} else {
					vals[i] = funCall(e.funCall, e.funCall.expr_set);
					i++;
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		//set up the ParseGrammar
		
		ParseGrammer p = new ParseGrammer();
		
		//Create the Objects...
		Object[] arguments = new Object[elem_set.length];
		int j = 0;
		for (Value v:vals){
			if (v.isContainFloat()){
				arguments[j] = v.val_float;
				j++;
			} else if (v.isContainInt()){
				arguments[j] = v.val_int;
				j++;
			} else {
				arguments[j] = v.val_string;
				j++;
			}
		}
		Method method = null;
		Value result = null;
		try{
			method = c.getMethod(identifier, (Class[]) arguments);
			result =  p.new Value(method.invoke(c, arguments));
		} catch (Exception e){
			e.printStackTrace();
		}
		
		//Invoke the class here
		return result;
		
	}
	
	
	
	
	
	
	
	}
		
		
		
	


