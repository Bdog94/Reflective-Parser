package methods;

import static org.junit.Assert.*;
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
	public void testFuncallNotNull() throws InvalidValueException{
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		ParseGrammer p = new ParseGrammer();
		
		
		
		Funcall f = p.new Funcall("len", 1, new Expr[] { p.new Expr( p.new Value((Object) "x")) });
		Expr[] e = new Expr[] { p.new Expr( p.new Value((Object) "x")) };
		Value v = r.funCall(f, e);
		
		assertNotEquals(v, null);
		
		
	}
	
	@Test 
	public void testFuncallOneParam() throws InvalidValueException{
		Reflection r = new Reflection();
		r.setUpReflection("commands.jar","Commands");
		ParseGrammer p = new ParseGrammer();
		
		
		
		Funcall f = p.new Funcall("len", 1, new Expr[] { p.new Expr( p.new Value((Object) "x")) });
		Expr[] e = new Expr[] { p.new Expr( p.new Value((Object) "x")) };
		Value v = r.funCall(f, e);
		
		assertEquals(v.val_int, 1);
	}
	
	@Test
	public void testFuncallTwoParam() throws InvalidValueException{
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
	public void testFuncallWithaFuncallWithin() throws InvalidValueException {
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
	
	@Test
	public void testIntegratingParsingAndReflection(){
		Reflection r = new Reflection();
		Parser p = new Parser();
		ParseTree pt = null;
		try {
			pt = p.parseLine("(add 1 1)");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Node n = pt.head;
		
		Expr expr = (Expr) n.getExpression();
		
		Funcall f = expr.getFunCall();
		Expr[] args = f.expr_set;
		
		Value v = r.funCall(f,args);
		assertEquals(v.getVal_int(), 2);
	}
	

}
