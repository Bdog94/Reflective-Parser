package methods;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import javax.swing.text.Position;

import methods.ParseGrammer.Funcall;

/**
 * Handles the parsing of Strings in ParseTrees
 * 
 * @author Kyle Perry
 *
 */
public class Parser {
	private String input;
	private int position;

	/**
	 * Takes a string and parses it into it's expressions
	 * 
	 * @param input
	 *            = a string to be parsed
	 * @return a ParseTree containing the parsed input
	 * @throws ParseException
	 *             if the input line is improperly formatted
	 */
	public ParseTree parseLine(String input) throws ParseException {
		ParseTree parsedInput = new ParseTree();
		this.input = input;
		this.position = 0;

		if (this.input.charAt(0) == '(') {
			this.position = 1;
			parsedInput.setHead(parseFuncall());
		} else {
			parsedInput.setHead(new Node(convertToValue(input), this.position));
		}

		generateFuncallInfo(parsedInput.head);
		return parsedInput;
	}

	/**
	 * Called when an expression to be parsed is a function call This method is
	 * call when the parser hits a '(' character in an input line(as this
	 * signals a function call)
	 * 
	 * @return the Node containing the function call, with its filled out
	 *         sub-expressions
	 * @throws ParseException
	 *             if the input is improperly formatted
	 */
	private Node parseFuncall() throws ParseException {
		Node n = null;
		char nextChar = ' ';

		if (input.indexOf(' ', this.position) == input.indexOf(')',
				this.position) || input.indexOf(')', this.position) < 0)
			throw new ParseException("Improper function call syntax at offset ",
					input.length());
		if (input.indexOf(' ', this.position) == -1
				&& input.indexOf(')', this.position) != -1) {
			if (input.substring(this.position,
					input.indexOf(')', this.position)).isEmpty())
				throw new ParseException("Empty function call found  at offset ", position);
			n = new Node(convertToFuncall(input.substring(this.position,
					input.indexOf(')', this.position))), this.position);
			this.position = input.indexOf(')', this.position) + 1;
			return n;
		} else {
			n = new Node(convertToFuncall(input.substring(this.position,
					input.indexOf(' ', this.position))), this.position);
			this.position = input.indexOf(' ', this.position) + 1;

			// Loops infinitely until one on the inner exit conditions is met
			for (;;) {
				switch (input.charAt(this.position)) {
				case ')':
					// Reached the end of the funcall, return the generated node
					this.position++;
					return n;
					// Found a sub-function call, call itself to set up the new
					// call
				case '(':
					this.position++;
					n.addExpression(parseFuncall());
					break;
				// Found a space, increment the position
				case ' ':
					this.position++;
					break;
				case '"':
					if (input.indexOf('"', this.position + 1) == -1)
					{		throw new ParseException(
								"Encountered end-of-input while reading string beginning at offset "
										+ this.position
										+ " and ending at offset "
										+ input.length(), input.length());
					}
					else 
					{
						n.addExpression(new Node(convertToValue((input
							.substring(this.position,
									input.indexOf('"', this.position + 1)+ 1))),
							this.position));
					this.position = input.indexOf('"', this.position + 1) + 1;
					}
					break;
				// A value was found, get it and convert it to an actual value
				default:
					// determine where the end of the value is (ends when a
					// space or a ')' is found)
					// if a space is found first
					if (input.indexOf(' ', this.position) < input.indexOf(')',
							this.position)
							&& input.indexOf(' ', this.position) != -1
							&& input.indexOf(')', this.position) != -1) {
						// add a node that contains the value of what was parsed
						n.addExpression(new Node(convertToValue((input
								.substring(this.position,
										input.indexOf(' ', this.position)))),
								this.position));
						// increment the position to be past the found value
						this.position = input.indexOf(' ', this.position) + 1;
					}
					// if a '(' is found first
					else {
						n.addExpression(new Node(convertToValue(input
								.substring(this.position,
										input.indexOf(')', this.position))),
								this.position));
						this.position = input.indexOf(')', this.position);
					}
					break;
				}
				// if the position reaches the end of the input string, then the
				// line ended without finding a ')' character
				// throw a ParseException as this is an improperly formatted
				// input line
				if (input.length() <= this.position)
					throw new ParseException(
							"Input line ended before the end of a function call was found at offset ",
							position);
			}
		}


	}

	/**
	 * Converts a parsed String into an Expression containing a function call
	 * with that identifier
	 * 
	 * @param target
	 *            the string to make into a funcall identifier
	 * @return the Expr that contains the funcall
	 * @throws ParseException
	 *             if the identifier is incorrectly formatted.
	 */
	private ParseGrammer.Expr convertToFuncall(String target)
			throws ParseException {
		ParseGrammer.Expr converted = null;

		// the first character must be [A..Z]|[a..z]|'_'
		// if not, throw an exception
		if (target.toLowerCase().charAt(0) <= 'z'
				&& target.toLowerCase().charAt(0) >= 'a'
				|| target.charAt(0) == '_') {
			for (char c : target.toCharArray()) {
				// all other characters must be [A..Z]|[a..z]|[0..9]|'_'
				// if not, throw an exception
				if (isAlphanumeric(c))
					continue;
				throw new ParseException("Unexpected symbol in function name at offset ", position);
			}
			converted = new ParseGrammer().new Expr(
					new ParseGrammer().new Funcall(target));
		} else {
			throw new ParseException("Unexpected symbol in function name at offset ", position);
		}
		return converted;
	}

	/**
	 * Checks if a character is either a letter, a digit, or an underscore.
	 * 
	 * @param aChar
	 *            the character to be checked
	 * @return True if it is a letter, a number, or an underscore. Otherwise,
	 *         returns false.
	 */
	private Boolean isAlphanumeric(char aChar) {
		if ((aChar >= 'a' && aChar <= 'z') || (aChar >= 'A' && aChar <= 'Z')
				|| (aChar >= '0' && aChar <= '9') || (aChar == '_'))
			return true;
		return false;
	}

	/**
	 * Converts a parsed String into an Expr with the value of what was parsed
	 * 
	 * @param target
	 *            the parsed String to convert
	 * @return an Expr containing the converted Value
	 * @throws ParseException
	 *             if the value isn't a recognized value type ("String",
	 *             (-/+)1234 integer, (-/+)X.123 float)
	 */
	private ParseGrammer.Expr convertToValue(String target)
			throws ParseException {
		ParseGrammer p = new ParseGrammer();
		ParseGrammer.Expr converted = null;
		ParseGrammer.Value val;
		
		char first = target.charAt(0);
		
		// if the input string is more than one character
		if (!(target.length() > 1 || (target.charAt(0) >= '0' && target.charAt(0) <= '9')))
			throw new ParseException("Invalid value at offset ", position);
		
		if (first == '"' && target.charAt(target.length()-1) == '"') 
		{
			
			val = new ParseGrammer().new Value(target.substring(1, target.length()-1));
			converted = new ParseGrammer().new Expr(val);
		}
		else {
			// make a Scanner to scan for ints and floats
			Scanner converter = new Scanner(target);
			// if there is an int, get it out and make the value with it
			if (converter.hasNextInt()) {
				val = new ParseGrammer().new Value(converter.nextInt());
				converted = new ParseGrammer().new Expr(val);
			}
			// if the scanner has a float, and the float doesn't start with the
			// decimal point (0.1 is acceptable, .1 is not)
			// pull the float out, turn it into a Value
			else if (converter.hasNextFloat()
					&& ((target.charAt(0) >= '0' && target.charAt(0) <= '9') || ((target
							.charAt(0) == '+' || target.charAt(0) == '-') && (target
							.charAt(1) >= '0' && target.charAt(1) <= '9')))) {
				val = new ParseGrammer().new Value(converter.nextFloat());
				converted = new ParseGrammer().new Expr(val);

			} else {
				converter.close();
				throw new ParseException("Invalid value at offset ", position);
			}
		}
		return converted;
	}

	/**
	 * Finishes the creation of all FunCalls by filling in the Expr_set and the
	 * numExpressions
	 * 
	 * @param n
	 *            a node in the tree, which will be traversed
	 */
	private void generateFuncallInfo(Node n) {
		if (n.getExpression().isFunCall) {
			ParseGrammer.Expr sub_expr[] = new ParseGrammer.Expr[n
					.numExpressions()];
			n.getExpression().getFunCall().setNumOfExpr(n.numExpressions());
			for (int i = 0; i < n.numExpressions(); i++) {
				if (n.findSubExpr(i).getExpression().isFunCall())
					// generate the funcall info for a sub-function call
					generateFuncallInfo(n.findSubExpr(i));
				sub_expr[i] = n.findSubExpr(i).getExpression();
			}
			n.getExpression().funCall.setExpr_set(sub_expr);
		}
	}

}
