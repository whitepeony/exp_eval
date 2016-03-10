package expression.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import expression.main.SimpleExpressionEvalImpl;

public class ExpressionEvalTest {

	SimpleExpressionEvalImpl testExpEval; 
	@Before
	public void setUp()
	{
		testExpEval = new SimpleExpressionEvalImpl();
	}

	@After
	public void tearDown()
	{
		testExpEval = null;
	}

	@Test
	public void testSimpleAddExp() {
		String result = null;
		try {
			result = String.valueOf(testExpEval.evaluateExpression("add(1, 2)"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("3.0", result);
	}
	
	@Test
	public void testSimpleArithmeticExp() {
		String result = null;
		try {
			result = String.valueOf(testExpEval.evaluateExpression("add(1, mult(2, 3))"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("7.0", result);
	}
	
	@Test
	public void testMultiArithmeticExp() {
		String result = null;
		try {
			result = String.valueOf(testExpEval.evaluateExpression("mult(add(2, 2), div(9, 3))"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("12.0", result);
	}

	@Test
	public void testSimpleVariablesExp() {
		String result = null;
		try {
			result = String.valueOf(testExpEval.evaluateExpression("let(a, 5, add(a, a))"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("10.0", result);
	}

	@Test
	public void testMultiVariablesExp() {
		String result = null;
		try {
			result = String.valueOf(testExpEval.evaluateExpression("let(a, 5, let(b, mult(a, 10), add(b, a)))"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("55.0", result);
	}

	@Test
	public void testNestedVariableExp() {
		String result = null;
		try {
			result = String.valueOf(testExpEval.evaluateExpression("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("40.0", result);
	}

	@Test
	public void testInvalidExp() {
		boolean thrown = false;

		try {
			String.valueOf(testExpEval.evaluateExpression("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b))"));
		} catch (Exception e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
}
