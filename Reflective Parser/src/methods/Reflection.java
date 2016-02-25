package methods;
import java.lang.reflect.*;
import java.awt.*;
public class Reflection {
	

	
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
		
	static void printFieldNames(Object o) {
		Class c = o.getClass();
		Field[] publicFields = c.getFields();
		
		for (int i = 0; i < publicFields.length; i++){
			String fieldName = publicFields[i].getName();
			Class typeClass = publicFields[i].getType();
			String fieldType = typeClass.getName();
			
		
		}
		
		}
	static void printFuncalls(Object o){
		Class c = o.getClass();
		Method[] methods = c.getMethods();
		
		for (int i = 0; i < methods.length; i++){
			String methodString = methods[i].getName();
			System.out.print("(" + methodString + " ");
			
			String returnString = methods[i].getReturnType().getName();
			
			Class[] parameterTypes = methods[i].getParameterTypes();
			for (int k = 0; k < parameterTypes.length; k++ ){
				
				String parameterString = parameterTypes[k].getName();
				System.out.print(parameterString + " ");
			}
			System.out.println(") : " + returnString);
			
			
		}
	}
	
	
	
	
	
	}
		
		
		
	


