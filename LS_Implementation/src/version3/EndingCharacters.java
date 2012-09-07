package version3;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class EndingCharacters extends BasedOnAttributes {

	
	/**
	 * @param attribute
	 *            implements ls "-p" operation which puts "/" for directories
	 * @param toPrint 
	 * @param validDirectories 
	 * @param out
	 * @return 
	 * @throws IOException 
	 */
	public void endSlashForDirectories(char attribute, List<File> validDirectories, List<String> toPrint) throws IOException{
		validDirectories = getValidDirectories(toPrint);
		for (File directory : validDirectories) {
			if (Directories.size()> 1) {
				toPrint.add(directory.getName() + ":");
			}
			File[] subFiles = directory.listFiles();
			for (File file : subFiles) {

				if (!file.isHidden()) {
					// for both -p and -F if file is directory "/" is added
					if (file.isDirectory()) {
						toPrint.add(file.getName() + "/");

						// in case of "-F" many other symbols for different
						// types of files is to be added

					} else if (attribute=='F') {

						if (file.canExecute()) {
							toPrint.add(file.getName() + "*");
						} else if (FileUtils.isSymlink(file)) {
							toPrint.add(file.getName() + "@");
						} else {
							toPrint.add(file.getName());
						}

					} else {
						toPrint.add(file.getName());
					}
				}
			}
		}
	}
}
