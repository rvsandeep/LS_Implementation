package version4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sandeeprv
 * 
 *         is a java interface to implement the "ls" command in terminal look
 *         for LsImplementation.java class to see the implementation of
 *         different operations
 */
public interface LsInterface {
	List<Character> Attributes = new ArrayList<Character>();
	
	
	
	List<String> Directories = new ArrayList<String>();

	/**
	 * @param arguments
	 * 
	 *            sets the Attributes to a list of attributes like
	 *            "-a","-A","-t","-u","-U" etc.. which are provided by the user
	 *            during run-time
	 */
	public void setAttributes(String[] arguments);

	/**
	 * @param arguments
	 * 
	 *            sets the Directories to a list of Directories for which the
	 *            user is requesting to show the contents. ( in a particular
	 *            format if attributes are provided )
	 */
	public void setDirectories(String[] arguments);

	/**
	 * @return toPrint does the operation as user requested for a attribute on a
	 *         directory and returns a list of Strings to be printed to the
	 *         screen
	 * @throws IOException 
	 */
	public List<String> performOperation() throws IOException;
}
