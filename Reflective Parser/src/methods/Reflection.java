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
		
		
		if (!f.exists()){
			System.err.print("Could not load jar file: " + fileName);
			System.exit(-5);
		}
		
		
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
			System.err.print("Could not load jar file: " + fileName);
			System.exit(-5);
		} catch (IllegalArgumentException e) {
			System.err.print("Could not load jar file: " + fileName);
			System.exit(-5);
		} catch (InvocationTargetException e) {
			System.err.print("Could not load jar file: " + fileName);
			System.exit(-5);
		}
		
		//Loads the class into c
		try {
			
			c = sysloader.loadClass(className);
		
			
		} catch (ClassNotFoundException e) {
			System.err.print("Could not find class:" + className );
			System.exit(-6);
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
					if (parameterTypes[k].getName().toLowerCase().contains("string")){
						f.expr_set[k] = p.new Expr(p.new Value(new String()));
					} else if (parameterTypes[k].getName().toLowerCase().contains("float")){
						f.expr_set[k] = p.new Expr(p.new Value(new Float(0.0)));
					} else {
						f.expr_set[k] = p.new Expr(p.new Value(new Integer(0)));
					}
						
				} catch (InvalidValueException e) {
					e.printStackTrace();
				} 
			  }
			funCallArrayList.add(f);
			
			}
		}
		func = new Funcall[funCallArrayList.size()];
		int i = 0;
		for (Funcall f:funCallArrayList){
			func[i] = f;
			i++;
			}
		}
	
	private boolean doesIdentifierExist(String ident) 
	{
		for (Funcall f:func)
		{
			if (f.ident.equals(ident)){
				return true;
			}
		}
		return false;
		
	}
	
	//Gets the jth paramater for for the function with the specified identifier
	private Class getClassType(String ident, String type, int j)
	{
		type.toLowerCase();
		Method[] methods = c.getMethods();
		for (int i = 0; i < methods.length; i++)
		{
			String methodString = methods[i].getName();	//Name of the ith method
			int modifier = methods[i].getModifiers();	//Modifier of the method, ie is it final, public, private etc
			if (Modifier.isStatic(modifier) && methodString.equals(ident)){
				Class[] parameterTypes = methods[i].getParameterTypes();
				if (parameterTypes[0].getClass().getSimpleName().toLowerCase().contains(type)){
					return parameterTypes[j].getClass();
				}
				
			}
		}
		return null;
		
		
	}
				
	
	
	/**
	 * 
	 * @param o
	 */
	public void printFuncalls(Object o){
		Class c = o.getClass();		//Get the class from the object passed in
		Method[] methods = c.getMethods();	//Get all of the methods for the class passed in
		for (int i = 0; i < methods.length; i++){
			
			String methodString = methods[i].getName();	//Name of the ith method
			int modifier = methods[i].getModifiers();	//Modifier of the method, ie is it final, public, private etc
			
			//This method is only concerned with static methods
			if (Modifier.isStatic(modifier)){
			System.out.print("(" + methodString + " ");
			
			
			//Get the return type of the method
			String returnString = methods[i].getReturnType().getName();
			
			Class[] parameterTypes = methods[i].getParameterTypes();
			for (int k = 0; k < parameterTypes.length; k++ ){
				
				String parameterString = parameterTypes[k].getName();
				if (parameterString.equals("java.lang.String"))
					parameterString = "string";
				else if (parameterString.equals("java.lang.Float"))
					parameterString = "float";
				else if (parameterString.equals("java.lang.Integer"))
					parameterString = "int";
				
				System.out.print(parameterString + " ");
			}
			
			if (returnString.equals("java.lang.String"))
				returnString = "string";
			else if (returnString.equals("java.lang.Float"))
				returnString = "float";
			else if (returnString.equals("java.lang.Integer"))
				returnString = "int";
			
			System.out.println("): " + returnString);
			}
		}
	}
	
	public Value funCall(Expr e ) throws InvalidFunctionCallException{
		if (e.isFunCall){
			return funCall(e.getFunCall(), e.getFunCall().getExpr_set());
		}
		ParseGrammer p = new ParseGrammer();
		return p.new Value("s");
	}
	
	public Value funCall(Funcall f, Expr[] elem_set)
			throws InvalidFunctionCallException  {
		

		String identifier = f.ident;
		Value[] vals = new Value[elem_set.length];
		int i = 0;
			for (Expr e:elem_set){
				if (e.isValue()){
					vals[i] = e.value;
					i++;
				} else {
					vals[i] = funCall(e.funCall, e.funCall.expr_set);
					i++;
				}
			}
		//set up the ParseGrammar
		
		ParseGrammer p = new ParseGrammer();
		
		//Create the Objects...
		Object[] arguments = new Object[elem_set.length];
		Class[] parameters = new Class[elem_set.length];
		String type = null;
		int j = 0;
		for (Value v:vals){
			if(v!=null)
			{
			if (v.isContainFloat()){
				arguments[j] =new Float( v.val_float);
				parameters[j] = float.class;
				type = "float";
				j++;
			} else if (v.isContainInt()){
				arguments[j] = new Integer(v.val_int);
				parameters[j] = int.class;
				type = "int";
				j++;
			} else {
				arguments[j] = v.val_string;
				parameters[j] = String.class;
				j++;
			}
			}
		}
		Method method = null;
		Class c = o.getClass();
		Value result = null;
		try{
			method =  c.getMethod(identifier, parameters);
		}
		catch( NoSuchMethodException e){

			Class[] parameters2 = new Class[parameters.length];
			int i2 = 0;
			for (Class p_2:parameters)
			{
				
				if(type != null)
				{
				if (type.equals("float")){
					parameters2[i2] = Float.class;
				} else if (type.equals("int")){
					parameters2[i2] = Integer.class;
				} else {
					parameters2[i2] = String.class;
				}
				i2++;
				}
			}
			try 
			{			

				method = c.getMethod(identifier, parameters2);
			} catch (Exception e_1){
			throw new InvalidFunctionCallException("Invalid method called at offset ",f);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		try {
			result =  p.new Value(method.invoke(o, arguments));
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
								
		//Invoke the class here
		return result;
		
	}
	
	
	
	
	
	
	
	}
		
		
		
	


