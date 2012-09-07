package version4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sandeeprv
 * 
 *         Does the "ls" operation ( similar to one when ls is run on terminal )
 *         to know which operations have been implemented see
 *         LsImplementation.java
 * 
 */
public class LSMain {

	/**
	 * @param args
	 *            args are commands by the user. ex: "ls -a",
	 *            "ls -t ADirectoryName"
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		LsInterface ls = new LsImplementation();

		if (args.length < 1 || (args.length > 1 && !args[0].equals("ls"))) {
			System.out.println(" Provide ls command in the Command Prompt");
			System.exit(0);
		}

		ls.setAttributes(args);
		ls.setDirectories(args);

		List<String> toPrint = new ArrayList<String>();

		toPrint = ls.performOperation();

		for (String string : toPrint) {
			System.out.println(string);
		}
	}

}
