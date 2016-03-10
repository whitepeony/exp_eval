package expression.main;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Pattern;

public class SimpleExpressionEvalImpl {

	private Pattern pattern = Pattern.compile("\\d+");
	private Hashtable<Object, Double> context_dict = new Hashtable<Object, Double>();
	
	private double evaluate(Object expList) {
	    if (expList instanceof String) {     
	        if (pattern.matcher(expList.toString()).matches()) {
	            return Integer.parseInt(expList.toString());
	        }else{
	        	return context_dict.get(expList.toString());
	        }
	    }
	    else {
	    	ArrayList<?> exp_list = (ArrayList<?>)expList;
	    	String operator = exp_list.get(0).toString();
	    	Object exp = exp_list.get(1);
	    	if (operator.equalsIgnoreCase("root")) {
	            return evaluate(exp);
	        }else if (operator.equalsIgnoreCase("add")) {
	            return evaluate(exp) + evaluate(exp_list.get(2));
	        }else if (operator.equalsIgnoreCase("mult")) {
	            return evaluate(exp) * evaluate(exp_list.get(2));
	        }else if (operator.equalsIgnoreCase("div")) {
	            return evaluate(exp) / evaluate(exp_list.get(2));
	        }else if (operator.equalsIgnoreCase("let")) {
	        	context_dict.put(exp, evaluate(exp_list.get(2)));
	    	    return evaluate(exp_list.get(3));
	        }
	    }
	    return 0;
	}	 
	
	private Object[] parse_exp_str(String exp_str, String func_name, int index) throws Exception {
	    ArrayList<Object> exp_list = new ArrayList<Object>();
	    exp_list.add(func_name);
	    String tmp_str = "";
	    try{
		    while(true) {
		    	char currentChar = exp_str.charAt(index);
		        if(currentChar == '(') {
		        	Object[] returned = parse_exp_str(exp_str, tmp_str, index+1);
		        	index = (int)returned[1];
		        	exp_list.add(returned[0]);
		        	tmp_str = "";
		        	if (func_name.equalsIgnoreCase("root")) break;
		        }else if (currentChar == ')') {
		        	if (tmp_str.length() > 0) exp_list.add(tmp_str);
		        	break;
		        }else if (currentChar == ',') {
		            if (tmp_str.length() > 0) exp_list.add(tmp_str);
		            tmp_str = "";
		        }else{
		            tmp_str += exp_str.charAt(index);
		        }
	        	++index;
		    }
	    }catch (Exception e) {
			// TODO: handle exception
	    	throw new InvalidExpressionException(exp_str, e);
		}

	    return new Object[]{exp_list, index};  
	}
	
	public double evaluateExpression(String expression) throws Exception{
		return evaluate(parse_exp_str(expression.replaceAll(" ", ""), "root", 0)[0]);
	}
	
	public static void main(String[] args) {
		String[] expressions = 
		{
            "add(1, 2)",   
            "add(1, mult(2, 3))",
            "mult(add(2, 2), div(9, 3))",
            "let(a, 5, add(a, a))",
            "let(a, 5, let(b, mult(a, 10), add(b, a)))",
            "let(a, 5, let(b, div(a, 10), add(b, a)))",
            "let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))"
		};
		
		if(args.length == 1) expressions = args;	

		SimpleExpressionEvalImpl impl = new SimpleExpressionEvalImpl();
		for(int i = 0; i < expressions.length; i++) {
			String temp = expressions[i];
			try{
				double result = impl.evaluateExpression(temp);
				System.out.println(temp + " ==> " + result);				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
