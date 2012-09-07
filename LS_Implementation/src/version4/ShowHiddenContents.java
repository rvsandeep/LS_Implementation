package version4;

import java.io.File;
import java.util.List;

public class ShowHiddenContents extends BasedOnAttributes {


	/**
	 * @param state
	 * @param filesToOperate
	 * @param subFiles
	 * 
	 *            performs "ls -a and ls -A" done on the terminal and returns a
	 *            list of strings which are file names contained in the
	 *            requested directory ( including hidden files )
	 */
	public void getHiddenContents(TYPE state, List<File> filesToOperate,
			File[] subFiles) {

		if (state == TYPE.small) {
			filesToOperate.add(new File("."));
			filesToOperate.add(new File(".."));
		}

		for (File files : subFiles) {
			filesToOperate.add(files);
		}

	}

}
