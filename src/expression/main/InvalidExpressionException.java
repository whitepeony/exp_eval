package expression.main;

public class InvalidExpressionException extends Exception {
	private static final long serialVersionUID = 1231177609211154714L;

	public InvalidExpressionException() {
		super("Expression is invalid");
	}
	public InvalidExpressionException(String expression, Throwable t) {
		super(expression, t);
	}	
}
