package version3;

import java.io.File;
import java.util.List;

public class ShowHiddenContents extends BasedOnAttributes {
	
	
	/**
	 * @param state
	 * @param validDirectories2 
	 * @param toPrint 
	 * @return toPrint
	 * 
	 *         performs "ls -a and ls -A" done on the terminal and returns a
	 *         list of strings which are file names contained in the requested
	 *         directory ( including hidden files )
	 */

	public void getHiddenContents(TYPE state, List<String> toPrint, List<File> validDirectories) {


		for (File directory : validDirectories) {
			if (validDirectories.size() > 1) {
				toPrint.add(directory.getName() + ":");
			}
			// in the case of ls "-a" the current and parent directories are
			// printed by . and .. ,this can be ignored if "-A"
			if (state == TYPE.small) {
				toPrint.add(".");
				toPrint.add("..");
			}

			File[] contents = directory.listFiles();
			for (File string : contents) {
				toPrint.add(string.getName());
			}
			toPrint.add(""); // for a line separator between two different
								// directories
		}
	}

}
