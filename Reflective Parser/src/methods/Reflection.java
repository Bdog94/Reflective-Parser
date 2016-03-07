package methods;
import java.lang.reflect.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import methods.ParseGrammer.Expr;
import methods.ParseGrammer.Funcall;
import methods.ParseGrammer.Value;
/**
 * 
 * @author Bernie Mayer
 *
 */
public class Reflection {
	Object o;
	Class  c;
	Funcall func[];
	
	public static void main(String[] args) {
		
	}
	//Set up a object
	public void setUpReflection(String fileName, String className) {
		o = dynamicallyLoadFile(fileName, className);
	}
	/**
	 * 
	 * @param fileName The name of the file that you want to dynamically load
	 * @param className Name of the class that you want to dynamically load
	 * @return This method will return and instance in Object form of the class 
	 * that you want to dynamically load
	 */
	
	public Object dynamicallyLoadFile(String fileName, String className){
		
		File f = new File(fileName);
		URL url = null;
		
		try {
			//Get the url of the File
			url = f.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		//Used to get the object and the class
		URLClassLoader sysloader =  (URLClassLoader)ClassLoader.getSystemClassLoader(); 
		
		//Get a class object for the URLClassLoader
		Class<?> sysclass = URLClassLoader.class;  
		
		
		//This block below aims to allow for access to a private method
	    Class[] parameters = new Class[1];	//The parameters of the method
	    parameters[0] = url.getClass();
		Method method = null;
		try {
			//Get the addURL method
			method = sysclass.getDeclaredMethod("addURL",new Class[]{URL.class});
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		method.setAccessible(true); 
		try {
			//Invoke the addUrl method with the url 
		   method.invoke(sysloader,new Object[]{ url });
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
		
		//Loads the class into c
		try {
			
			c = sysloader.loadClass(className);
		
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Sets up an object
		Object obj = null;
		try{
			
			Class classdef = Class.forName(c.getName());
			
			obj = classdef.newInstance();
		} catch (Exception e){
			e.printStackTrace();
		}
		//Return the object created dynamically at runtime
		return obj;
	}
	/**
	 * 
	 * @param o 
	 */
	
	public void showConstructors(Object o)
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
		
	public void printFieldNames(Object o) {
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
	 * @param o The object that you want the funCalls for
	 */
	
	public void setupFuncalls(Object o){
		Class c = o.getClass();
		
		Method[] methods = c.getMethods();
		ParseGrammer p = new ParseGrammer();
		
		ArrayList<Funcall> funCallArrayList = new ArrayList();
		for (int i = 0; i < methods.length; i++){
			String methodString = methods[i].getName();
			int modifier = methods[i].getModifiers();
			if (Modifier.isStatic(modifier)){
			Funcall f = p.new Funcall();
			f.setIdent(methodString);
			
			String returnString = methods[i].getReturnType().getName();
			
			Class[] parameterTypes = methods[i].getParameterTypes();
			f.setNumOfExpr(parameterTypes.length);
			f.expr_set = new Expr[f.numOfExpr];
			
			for (int k = 0; k < parameterTypes.length; k++ ){
				
				String parameterString = parameterTypes[k].getName();
				try {
					f.expr_set[k] = p.new Expr(p.new Value(parameterTypes[k].getClass()));
				} catch (InvalidValueException e) {
					e.printStackTrace();
				}
			  }
			funCallArrayList.add(f);
			
			}
		}
		func = (Funcall[]) funCallArrayList.toArray();
	}
	
	/**
	 * 
	 * @param o
	 */
	public void printFuncalls(Object o){
		Class c = o.getClass();
		System.out.println(c.getName());
		Method[] methods = c.getMethods();
		
		for (int i = 0; i < methods.length; i++){
			String methodString = methods[i].getName();
			int modifier = methods[i].getModifiers();
			System.out.print("(" + methodString + " ");
			
			
			
			String returnString = methods[i].getReturnType().getName();
			
			Class[] parameterTypes = methods[i].getParameterTypes();
			for (int k = 0; k < parameterTypes.length; k++ ){
				
				String parameterString = parameterTypes[k].getName();
				System.out.print(parameterString + " ");
			}
			System.out.println("): " + returnString);		
		}
	}
	
	
	public Value funCall(Funcall f, Expr[] elem_set){
		

		String identifier = f.ident;
		Value[] vals = new Value[elem_set.length];
		int i = 0;
		try {
			for (Expr e:elem_set){
				if (e.isValue()){
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
		Class[] parameters = new Class[elem_set.length];
		int j = 0;
		for (Value v:vals){
			if (v.isContainFloat()){
				arguments[j] = v.val_float;
				parameters[j] = Float.class;
				j++;
			} else if (v.isContainInt()){
				arguments[j] = v.val_int;
				parameters[j] = Integer.class;
				j++;
			} else {
				arguments[j] = v.val_string;
				parameters[j] = String.class;
				j++;
			}
		}
		Method method = null;
		Class c = o.getClass();
		Value result = null;
		try{
			method =  c.getMethod(identifier, parameters);
			result =  p.new Value(method.invoke(o, arguments));
		} catch (Exception e){
			e.printStackTrace();
		}
		
		//Invoke the class here
		return result;
		
	}
	
	
	
	
	
	
	
	}
		
		
		
	


