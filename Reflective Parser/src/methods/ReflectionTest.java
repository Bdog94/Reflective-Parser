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
		fail("Not yet implemented");
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

}
