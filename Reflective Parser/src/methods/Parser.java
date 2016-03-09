package methods;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import methods.ParseGrammer.Funcall;

/**
 * 
 * @author Kyle Perry
 *
 */
public class Parser {
	private String input;

	public static void main(String[] args) {
		String a = "";
		Scanner userIn = new Scanner(System.in);

		System.out.println("Input to Parse: ");
		a = userIn.nextLine();

		Parser p = new Parser();

		try {
			//p.convertToValue(a);
			
			 ParseTree t = p.parseLine(a);
			 System.out.println("Parsed input of \"" + a + "\": ");
			 System.out.println(t.toString());
			 }
			 catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public ParseTree parseLine(String input) throws Exception {
		ParseTree parsedInput = new ParseTree();
		this.input = input;
		// System.out.println(inputScan.delimiter().toString());
		// inputScan.useDelimiter("[{} ]");

		if (this.input.charAt(0) == '(') {
			this.input = this.input.substring(1);
			parsedInput.setHead(parseFuncall());
		} else {
			parsedInput.setHead(new Node(convertToValue(input)));
		}

		return parsedInput;
	}

	/**
	 * Called only when
	 * 
	 * @param inputScanner
	 * @return
	 * @throws Exception
	 */
	private Node parseFuncall() throws Exception {
		Node n = null;
		char nextChar = ' ';

		if (input.indexOf(' ') == input.indexOf(')'))
			throw new IOException("Improper function call syntax");
		if (input.indexOf(' ') > input.indexOf(')')) {
			n = new Node(convertToFuncall(input.substring(0, input.indexOf(')'))));
			input.substring(input.indexOf(')') + 1);

		} else {
			n = new Node(convertToFuncall(input.substring(0, input.indexOf(' '))));
			this.input = input.substring(input.indexOf(' ') + 1);
			for (;;) {
				System.out.println(input.charAt(0));
				switch (input.charAt(0)) {
				case ')':
					return n;
				case '(':
					input = input.substring(1);
					n.addExpression(parseFuncall());
					break;
				case ' ':
					input = input.substring(1);
					break;
				default:
					if (input.indexOf(' ') < input.indexOf(')')
							&& input.indexOf(' ') != -1
							&& input.indexOf(')') != -1) {
						n.addExpression(new Node(convertToValue((input.substring(0,
								input.indexOf(' '))))));
						this.input = input.substring(input.indexOf(' ') + 1);
					} else {
						n.addExpression(new Node(convertToValue(input.substring(0,
								input.indexOf(')')))));
						this.input = input.substring(input.indexOf(')'));
					}
					break;
				}
				if (input.length() == 0)
					throw new IOException(
							"Input line ended before the end of a function call was found");
			}
		}
		return null;
	}

	private ParseGrammer.Expr convertToFuncall(String target)
			throws IOException {
		ParseGrammer.Expr converted = null;

		if (target.toLowerCase().charAt(0) <= 'z'
				&& target.toLowerCase().charAt(0) >= 'a'
				|| target.charAt(0) == '_') {
			for (char c : target.toCharArray()) {
				if (isAlphanumeric(c))
					continue;
				throw new IOException("Invalid identifier format");
			}
			System.out.println("Success!");
			converted = new ParseGrammer().new Expr(new ParseGrammer().new Funcall(target));
		} else {
			throw new IOException("Invalid identifier format");
		}
		return converted;
	}

	private Boolean isAlphanumeric(char aChar) {
		if ((aChar >= 'a' && aChar <= 'z') || (aChar >= 'A' && aChar <= 'Z')
				|| (aChar >= '0' && aChar <= '9') || (aChar == '_'))
			return true;
		return false;
	}

	private ParseGrammer.Expr convertToValue(String target) throws Exception {
		ParseGrammer p = new ParseGrammer();
		ParseGrammer.Expr converted = null;
		ParseGrammer.Value val;

		char first = target.charAt(0);
		char second;
		if(target.length() > 1)
			second = target.charAt(1);
		else
			second = '\0';
		if (first == '"'
				&& target.charAt(target.length() - 1) == '"') {
			val = new ParseGrammer().new Value(target);
			converted = new ParseGrammer().new Expr(val);
		} else {
			Scanner converter = new Scanner(target);
			if (converter.hasNextInt()) {
				val = new ParseGrammer().new Value(converter.nextInt());
				converted = new ParseGrammer().new Expr(val);				
			} else if (converter.hasNextFloat()
					&& ((target.charAt(0) >= '0' && target.charAt(0) <= '9')
							|| target.charAt(0) == '+'
							&& (target.charAt(1) >= '0' && target.charAt(1) <= '9') || target.charAt(0) == '-'
							&& (target.charAt(1) >= '0' && target.charAt(1) <= '9'))) {
				val = new ParseGrammer().new Value(converter.nextFloat());
				converted = new ParseGrammer().new Expr(val);

			} else {
				converter.close();
				throw new IOException("Invalid value");
			}
		}
		return converted;
	}

	private void generateFuncallInfo(Node n)
	{
		if (n.getExpression().isFunCall)
		{
			n.getExpression().getFunCall().setNumOfExpr(n.numExpressions());
			n.getExpression().getFunCall().setExpr_set(n.getSubExpr().toArray(n.getExpression().getFunCall().getExpr_set()));
			for(Node sub: n.getSubExpr())
			{
				if(sub.getExpression().isFunCall)
					generateFuncallInfo(sub);
			}
		}
	}
	
}
