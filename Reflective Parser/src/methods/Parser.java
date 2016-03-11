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
	private int position;
	
	public static void main(String[] args) {
		String a = "";
		Scanner userIn = new Scanner(System.in);

		System.out.println("Input to Parse: ");
		a = userIn.nextLine();

		Parser p = new Parser();

		try{
			
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
		this.position = 0;
		// System.out.println(inputScan.delimiter().toString());
		// inputScan.useDelimiter("[{} ]");

		if (this.input.charAt(0) == '(') {
			this.position = 1;
			parsedInput.setHead(parseFuncall());
		} else {
			parsedInput.setHead(new Node(convertToValue(input), this.position));
		}

		//generateFuncallInfo(parsedInput.head);
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

		if (input.indexOf(' ', this.position) == input.indexOf(')', this.position))
			throw new IOException("Improper function call syntax");
		if (input.indexOf(' ', this.position) == -1 && input.indexOf(')', this.position) != -1) {
			n = new Node(convertToFuncall(input.substring(this.position, input.indexOf(')', this.position))), this.position);
			this.position = input.indexOf(')', this.position) + 1;
			return n;
		} 
		else {
			n = new Node(convertToFuncall(input.substring(this.position, input.indexOf(' ', this.position))), this.position);
			this.position = input.indexOf(' ', this.position) + 1;
			for (;;) {
				switch (input.charAt(this.position)) {
				case ')':
					return n;
				case '(':
					this.position++;
					n.addExpression(parseFuncall());
					break;
				case ' ':
					this.position++;
					break;
				default:
					if (input.indexOf(' ', this.position) < input.indexOf(')', this.position)
							&& input.indexOf(' ', this.position) != -1
							&& input.indexOf(')', this.position) != -1) {
						n.addExpression(new Node(convertToValue((input.substring(this.position,
								input.indexOf(' ', this.position)))), this.position));
						this.position = input.indexOf(' ', this.position) + 1;
					} else {
						n.addExpression(new Node(convertToValue(input.substring(this.position,
								input.indexOf(')', this.position))), this.position));
						this.position = input.indexOf(')', this.position);
					}
					break;
				}
				if (input.length() == 0)
					throw new IOException(
							"Input line ended before the end of a function call was found");
			}
		}

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
			ParseGrammer.Expr sub_expr[] = new ParseGrammer.Expr[n.numExpressions()]; 
			n.getExpression().getFunCall().setNumOfExpr(n.numExpressions());
			for(int i = 0; i < n.numExpressions(); i++)
			{
				if(n.getExpression(i).getExpression().isFunCall())
					generateFuncallInfo(n.getExpression(i));
				sub_expr[i] = n.getExpression(i).getExpression();
			}
			n.getExpression().funCall.setExpr_set(sub_expr);
		}
	}
	
}
