package version3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecursiveLs extends BasedOnAttributes {
	
	void RecursivelyListsubDirectories(List<String> toPrint, List<File> validDirectories) {
		validDirectories = getValidDirectories(toPrint);

		for (File directory : validDirectories) {
			if (Directories.size() > 1) {
				toPrint.add(directory.getName() + ":");
			}
			printSubDirectories(directory, toPrint);

		}
	}

	/**
	 * @param directory
	 *            is an helper to RecursivelyListsubDirectories, it prints the
	 *            contents of the directory, forms a list of the sub-directories
	 *            in the directory and prints them recursively
	 * @param toPrint
	 */
	private static void printSubDirectories(File directory, List<String> toPrint) {

		File[] subFiles = directory.listFiles();
		List<File> ListOfSubDirectory = new ArrayList<File>();
		for (File file : subFiles) {

			if (!file.isHidden()) {
				toPrint.add(file.getName());
				if (file.isDirectory())
					ListOfSubDirectory.add(file);
			}
		}

		for (File file : ListOfSubDirectory) {
			toPrint.add("\n" + file.getPath() + ":");
			// recursive call to print sub-directories of the sub-directories
			printSubDirectories(file, toPrint);
		}

	}
	

}
