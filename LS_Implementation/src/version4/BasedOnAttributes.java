package version4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BasedOnAttributes extends LsImplementation {

	/**
	 * is used to differentiate between "-a" and "-A"
	 */
	public enum TYPE {
		small, large;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see version4.LsImplementation#performOperation() does the operation of
	 * selecting Directories and operating on them based on the attributes
	 */
	public List<String> performOperation() throws IOException {

		List<String> toPrint = new ArrayList<String>();
		List<File> validDirectories = getValidDirectories(toPrint);
		for (File directory : validDirectories) {
			if (Attributes.size() == 0) {
				List<File> filesToOperate = new ArrayList<File>();
				File[] subFiles = directory.listFiles();

				if (Directories.size() > 1)
					toPrint.add(directory.getPath() + ":");

				filesToOperate = removeHiddenFiles(subFiles);

				for (File eachFile : filesToOperate) {
					toPrint.add(eachFile.getName());
				}
				toPrint.add("");
			} else if (Attributes.get(0) == 'R') {
				
				operateRecursively(toPrint, directory);
				
			} else {
				List<File> filesToOperate = new ArrayList<File>();

				if (Directories.size() > 1)
					toPrint.add(directory.getPath() + ":");
				File[] subFiles = directory.listFiles();

				if (!(Attributes.contains('a') || Attributes.contains('A'))) {
					filesToOperate = removeHiddenFiles(subFiles);
				}
				onSubFiles(toPrint, subFiles, filesToOperate);
				toPrint.add("");
			}

		}

		return toPrint;
	}

	/**
	 * @param toPrint
	 * @param subDirectories
	 * @throws IOException
	 * 
	 *             operates recursively on the subdirectories of the directories
	 */
	private void operateRecursively(List<String> toPrint, File subDirectories)
			throws IOException {
		if (!subDirectories.getPath().equals("."))
			toPrint.add(subDirectories.getPath() + ":");
		List<File> filesToOperate = new ArrayList<File>();

		File[] subFiles = subDirectories.listFiles();

		if (!(Attributes.contains('a') || Attributes.contains('A'))) {
			filesToOperate = removeHiddenFiles(subFiles);
		}
		onSubFiles(toPrint, subFiles, filesToOperate);
		toPrint.add("");
		for (File file : filesToOperate) {
			if (file.isDirectory() && (!file.getName().equals("."))
					&& (!file.getName().equals("..")))
				operateRecursively(toPrint, file);
		}

	}

	/**
	 * @param toPrint
	 * @param subFiles
	 * @param filesToOperate
	 * @throws IOException
	 *             performs the actual operation on subFiles
	 */
	private void onSubFiles(List<String> toPrint, File[] subFiles,
			List<File> filesToOperate) throws IOException {
		List<String> toEndWith = new ArrayList<String>();

		onAllAttributes(subFiles, filesToOperate, toEndWith);
		int i = 0;

		if (toEndWith.isEmpty()) {
			for (File eachFile : filesToOperate) {
				toPrint.add(eachFile.getName());
			}
		} else {
			for (File eachFile : filesToOperate) {
				toPrint.add(eachFile.getName() + toEndWith.get(i));
				i++;
			}
		}
	}

	/**
	 * @param subFiles
	 * @param filesToOperate
	 * @param toEndWith
	 * @throws IOException
	 * 
	 *             on the subFiles passed as parameter performs all the
	 *             operations specified in the input
	 */
	private void onAllAttributes(File[] subFiles, List<File> filesToOperate,
			List<String> toEndWith) throws IOException {
		TYPE state;

		int i = 0;
		if (Attributes.get(0) == 'R')
			i = 1;

		for (; i < Attributes.size(); i++) {

			state = TYPE.small;
			switch (Attributes.get(i)) {
			case 'A':
				state = TYPE.large;
				if (Attributes.contains('a'))
					break;
			case 'a':
				ShowHiddenContents getHidden = new ShowHiddenContents();
				getHidden.getHiddenContents(state, filesToOperate, subFiles);
				break;

			case 't':
			case 'u':
			case 'U':
				// Sort By Size 'S' overrides everyother sorting
				// operation
				// therfore no need to perform sorting on these
				// attributes if
				// 'S' is in the list
				if (Attributes.contains('S'))
					break;
			case 'S':

				// FileSorter is subClass of BasedOnAttributes to sort
				// the files
				// based on attributes like creation time, last modified
				// time,last access time, file size and so on ...

				FileSorter sortFiles = new FileSorter();

				sortFiles.sort(filesToOperate, Attributes.get(i));
				break;

			case 'p':
			case 'F':

				// directory names ending with Slash and some more
				EndingCharacters endWith = new EndingCharacters();
				endWith.endSlashForDirectories(Attributes.get(i),
						filesToOperate, toEndWith);
				break;
			}

		}
	}

	/**
	 * @param subFiles
	 * @return incase the input do not as attributes like "a" or "A" which tells
	 *         to print hidden files remove the hidden files given by
	 *         file.listFiles()
	 */
	private List<File> removeHiddenFiles(File[] subFiles) {
		List<File> filesToOperate = new ArrayList<File>();
		for (File file : subFiles) {
			if (!file.isHidden())
				filesToOperate.add(file);
		}
		return filesToOperate;
	}

	/**
	 * @param toPrint
	 * @return validDirectories all the invalid file and directory names are
	 *         ignored and suitable statement is added toPrint valid files and
	 *         directories are returned
	 */
	private static List<File> getValidDirectories(List<String> toPrint) {
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
