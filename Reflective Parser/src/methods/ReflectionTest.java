package methods;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;

import junit.framework.*;
import methods.ParseGrammer.Expr;
import methods.ParseGrammer.Funcall;
import methods.ParseGrammer.Value;


import org.junit.Test;

public class ReflectionTest extends TestCase{

	@Test
	public void test() {
		assertEquals(true,true);
	}
	
	@Test 
	public void testFuncallNotNull() throws InvalidValueException, InvalidFunctionCallException{
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		ParseGrammer p = new ParseGrammer();
		
		
		
		Funcall f = p.new Funcall("len", 1, new Expr[] { p.new Expr( p.new Value((Object) "x")) });
		Expr[] e = new Expr[] { p.new Expr( p.new Value((Object) "x")) };
		Value v = r.funCall(f, e);
		
		assertNotEquals(v, null);
		
		
	}
	
	@Test 
	public void testFuncallOneParam() throws InvalidValueException, InvalidFunctionCallException{
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		ParseGrammer p = new ParseGrammer();
		
		
		
		Funcall f = p.new Funcall("len", 1, new Expr[] { p.new Expr( p.new Value((Object) "x")) });
		Expr[] e = new Expr[] { p.new Expr( p.new Value((Object) "x")) };
		Value v = r.funCall(f, e);
		
		assertEquals(v.val_int, 1);
	}
	
	@Test
	public void testFuncallTwoParam() throws InvalidValueException, InvalidFunctionCallException{
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		ParseGrammer p = new ParseGrammer();
		
		
		
		Funcall f = p.new Funcall();
		f.ident = "add";
		Expr[] e = new Expr[] { p.new Expr(p.new Value((Object)(new Integer(1)))), p.new Expr(p.new Value(new Integer(1)))};
		Value v = r.funCall(f, e);
		
		assertEquals(v.val_int, 2);
	}
	
	@Test
	public void testSetupFuncallsNotNull()  {
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar", "Commands");
		
		
		r.setupFuncalls(r.o);
		
		assertNotEquals(r.func, null);
		
		}
	
	@Test 
	public void testFuncallWithaFuncallWithin() throws InvalidValueException, InvalidFunctionCallException{
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		ParseGrammer p = new ParseGrammer();
		
		Funcall g = p.new Funcall();
		g.ident = "len";
		Expr[] g_e = new Expr[] {p.new Expr(p.new Value("hi"))};
		g.expr_set = g_e;
		
		
		Funcall f = p.new Funcall();
		f.ident = "add";
		Expr[] e = new Expr[] { p.new Expr(p.new Value((Object)(new Integer(1)))), p.new Expr( g )};
		Value v = r.funCall(f, e);
		
		assertEquals(v.val_int, 3);
	
	}
	
	//@Test
	public void testIntegratingParsingAndReflection() throws InvalidFunctionCallException{
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		Parser p = new Parser();
		ParseTree pt = null;
		try {
			pt = p.parseLine("(add 1 1)");
		} catch (ParseException e) {
			assertTrue(false);
		}
		Node n = pt.head;
		
		Expr expr = (Expr) n.getExpression();
		
		Funcall f = expr.getFunCall();
		Expr[] args = f.expr_set;
		assertTrue(args != null);
		Value v = r.funCall(expr);
		assertEquals(v.getVal_int(), 2);
	}
	
	@Test
	public void testInvalidFuncall() throws InvalidValueException{
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		ParseGrammer p = new ParseGrammer();
		
		
		
		Funcall f = p.new Funcall("len", 1, new Expr[] { p.new Expr( p.new Value((Object) "x")) });
		Expr[] e = new Expr[] { p.new Expr( p.new Value((Object) "x")) };
		try{
		Value v = r.funCall(f, e);
		} catch (InvalidFunctionCallException e2){
			assertTrue(true);
		} catch (Exception e3){
			assertTrue(false);
		}
		
		//assertTrue(false);
		
	}
	
	@Test
	public void testDecFuncall() throws InvalidValueException, InvalidFunctionCallException 
	{
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		ParseGrammer p = new ParseGrammer();
		
		
		
		Funcall f = p.new Funcall("dec", 1, new Expr[] { p.new Expr( p.new Value(new Float(1.0))) });
		Expr[] e = new Expr[] { p.new Expr( p.new Value(new Float(1.0))) };
		Value v = r.funCall(f, e);
		
		assertEquals(v.val_float, 0, 0.001);
		
	}
	
	@Test
	public void testIncFuncall() throws InvalidValueException, InvalidFunctionCallException 
	{
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		ParseGrammer p = new ParseGrammer();
		
		
		
		Funcall f = p.new Funcall("inc", 1, new Expr[] { p.new Expr( p.new Value(new Integer(1))) });
		Expr[] e = new Expr[] { p.new Expr( p.new Value(new Integer(1))) };
		Value v = r.funCall(f, e);
		
		assertEquals(v.val_float, 0, 0.001);
		
	}
	
	@Test
	public void testFloatAddFuncall() throws InvalidValueException, InvalidFunctionCallException 
	{
		
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		ParseGrammer p = new ParseGrammer();
		
		
		
		Funcall f = p.new Funcall();
		f.ident = "add";
		Expr[] e = new Expr[] { p.new Expr(p.new Value((Object)(new Float(1.2)))), p.new Expr(p.new Value(new Float(1.9)))};
		Value v = r.funCall(f, e);
		
		assertEquals(v.val_float, 3.1, 0.0001);
		
	}
	
	@Test 
	public void testFuncallWithaFuncallWithinError() throws InvalidValueException{
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		ParseGrammer p = new ParseGrammer();
		
		Funcall g = p.new Funcall();
		g.ident = "len";
		Expr[] g_e = new Expr[] {p.new Expr(p.new Value(new Integer(1)))};
		g.expr_set = g_e;
		
		
		Funcall f = p.new Funcall();
		f.ident = "add";
		Expr[] e = new Expr[] { p.new Expr(p.new Value((Object)(new Integer(1)))), p.new Expr( g )};
		Value v;
		boolean test = false;
		try {
			v = r.funCall(f, e);
		} catch (InvalidFunctionCallException e1) {
			if (e1.f.ident.contains("len"))
				test = true;
		}
		assertTrue(test);
		
		//assertEquals(v.val_int, 3);
	
	}
	
	
	
	
	}
	


