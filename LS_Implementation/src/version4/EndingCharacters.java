package version4;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class EndingCharacters extends BasedOnAttributes {

	/**
	 * @param attribute
	 *            implements ls "-p" operation which puts "/" for directories
	 * @param toEndWith
	 * @param filesToOperate
	 * @param out
	 * @return
	 * @throws IOException
	 */
	public void endSlashForDirectories(char attribute, List<File> filesToOperate,
			List<String> toEndWith) throws IOException {

		for (File file : filesToOperate) {

			if (file.isDirectory()) {
				toEndWith.add("/");

				// in case of "-F" many other symbols for different
				// types of files is to be added

			} else if (attribute == 'F') {

				if (file.canExecute()) {
					toEndWith.add("*");
				} else if (FileUtils.isSymlink(file)) {
					toEndWith.add("@");
				} else {
					toEndWith.add("");
				}

			} else {
				toEndWith.add("");
			}
		}
	}
}
