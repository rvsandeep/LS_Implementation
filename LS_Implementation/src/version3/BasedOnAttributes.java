package version3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sandeeprv
 * 
 *         Differentiates the operations to be performed based on the attributes
 *         requested by the user and does the operation. TODO: Currently single
 *         attribute operations are only supported, this class could be modified
 *         to support multiple attributes in future
 * 
 */
public class BasedOnAttributes extends LsImplementation {

	/**
	 * is used to differentiate between "-a" and "-A"
	 */
	public enum TYPE {
		small, large;
	}

	/**
	 * @returns toPrint
	 * 
	 *          performs the operation assuming it to be only single attribute
	 *          and returns a list of Statements to be printed to the screen
	 */
	/**
	 * @return
	 * @throws IOException
	 */
	public List<String> perform() throws IOException {
		List<String> toPrint = new ArrayList<String>();

		List<File> validDirectories = new ArrayList<File>();
		// Among the Directories mentioned it is convention to first print all
		// invalid directories and then do the operation
		validDirectories = getValidDirectories(toPrint);

		/*
		 * RecursiveLs getSubContents = new RecursiveLs();
		 * getSubContents.RecursivelyListsubDirectories(toPrint,validDirectories);
		 * 
		 */

		/*
		 * to work on: first get all the files to work on i.e do -R and -a or -A
		 * if mentioned adding the attributes is done second
		 * 
		 * and sort based on the file size,creation time , modified time and
		 * soo.. (sorting given last preference after everthing)
		 * 
		 * for: 1. -B, -b, -w, and -q 2. -c and -u all override each other and
		 * last one specified determines the format
		 */

		TYPE state;

		for (int i = 0; i < Attributes.size(); i++) {

			switch (Attributes.get(i)) {

			case 'a':
				ShowHiddenContents gethidden = new ShowHiddenContents();
				state = TYPE.small;
				gethidden.getHiddenContents(state, toPrint, validDirectories);
				break;

			case 'A':
				// Attribute 'a' overrides 'A' if happens together
				if (!Attributes.contains('a')) {
					ShowHiddenContents getHidden = new ShowHiddenContents();
					state = TYPE.large;
					getHidden.getHiddenContents(state, toPrint,
							validDirectories);
				}
				break;

			case 't':
			case 'u':
			case 'U':
				// Sort By Size 'S' overrides everyother sorting operation
				// therfore no need to perform sorting on these attributes if
				// 'S' is in the list
				if (Attributes.contains('S'))
					break;
			case 'S':

				// FileSorter is subClass of BasedOnAttributes to sort the files
				// based on attributes like creation time, last modified
				// time,last access time, file size and so on ...

				FileSorter sortFiles = new FileSorter();

				sortFiles.sort(toPrint, Attributes.get(i), validDirectories);
				break;

			case 'R':

				RecursiveLs getSubContents = new RecursiveLs();
				getSubContents.RecursivelyListsubDirectories(toPrint,
						validDirectories);
				break;

			case 'p':
			case 'F':

				// directory names ending with Slash and some more
				EndingCharacters endWith = new EndingCharacters();
				endWith.endSlashForDirectories(Attributes.get(i),
						validDirectories, toPrint);
				break;
			}
		}
		return toPrint;
	}

	/**
	 * @param toPrint
	 * @return validDirectories all the invalid file and directory names are
	 *         ignored and suitable statement is added toPrint valid files and
	 *         directories are returned
	 */
	protected static List<File> getValidDirectories(List<String> toPrint) {
		List<File> validDirectories = new ArrayList<File>();

		for (String name : Directories) {
			File directory = new File(name);
			if (directory.isDirectory())
				validDirectories.add(directory);
			else {
				toPrint.add("ls: " + name + ": No such file or directory");
			}
		}
		// In the case the user is referring only the current directory
		if (Directories.size() == 0) {
			File directory = new File(".");
			validDirectories.add(directory);
		}
		return validDirectories;
	}

}
