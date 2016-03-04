package methods;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

/**
 * 
 * @author Kyle Perry
 *
 */
public class Parser {
	private String input;

	public static void main(String[] args)
	{
		String a = "";
		Scanner userIn = new Scanner(System.in);

		System.out.println("Input to Parse: ");
		a = userIn.nextLine();

		Parser p = new Parser();

		try
		{
			ParseTree t = p.parseLine(a);
			System.out.println("Parsed input of \""+ a + "\": " );
			System.out.println(t.toString());
		}
		catch(Exception e)
		{
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
	public ParseTree parseLine (String input) throws IOException
	{
		ParseTree parsedInput = new ParseTree();
		this.input = input;
		//System.out.println(inputScan.delimiter().toString());
		//inputScan.useDelimiter("[{} ]");

		if(this.input.charAt(0) == '('){
			this.input = this.input.substring(1);
			parsedInput.setHead(parseFuncall());
		}
		else
		{
			parsedInput.setHead(new Node(input));
		}

		return parsedInput;
	}

	/**
	 * Called only when 
	 * @param inputScanner
	 * @return
	 * @throws Exception
	 */
	private Node parseFuncall () throws IOException 
	{	
		Node n = null;
		char nextChar = ' ';


		if(input.indexOf(' ') == input.indexOf(')'))
			throw new IOException ("Improper function call syntax");
		if(input.indexOf(' ') > input.indexOf(')'))
		{
			n = new Node(input.substring(0, input.indexOf(')')));
			input.substring(input.indexOf(')') + 1);

		}
		else
		{
			n = new Node(input.substring(0, input.indexOf(' ')));
			this.input = input.substring(input.indexOf(' ') + 1);
			for(;;)
			{
				System.out.println(input.charAt(0));
				switch(input.charAt(0))
				{
				case ')':
					return n;
				case '(':
					n.addExpression(parseFuncall());
					break;
				default:
					if(input.indexOf(' ') < input.indexOf(')') && input.indexOf(' ') != -1 && input.indexOf(')') != -1)
					{
						n.addExpression(new Node(input.substring(0, input.indexOf(' ')) ));
						this.input = input.substring(input.indexOf(' ') + 1);
					}
					else
					{
						n.addExpression(new Node(input.substring(0, input.indexOf(')')) ));
						this.input = input.substring(input.indexOf(')'));
					}
					break;
				}
				if(input.length() == 0)
					throw new IOException("Input line ended before the end of a function call was found");
			}
		}
		return null;
	}
}

