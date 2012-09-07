package version1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author sandeeprv
 * 
 *         Implementing "ls" opertion The operations implemented are:
 * 
 *         "-A" : List all entries except for . and .. , "-a" : Include directory
 *         entries whose names begin with a dot(.). "-R" : Recursively list
 *         sub-directories encountered. "-S" : Sort files by size "-t" : Sort by
 *         time modified (most recently modified first) before sorting the
 *         operands by lexicographical order.
 * 
 */
public class LsOperation {

	/**
	 * @param args
	 *            asserts if the first argument is not "ls" supports single
	 *            attribute listing
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		FileWriter fstream = new FileWriter("out.txt");
		BufferedWriter out = new BufferedWriter(fstream);
		assert (args[0].compareTo("ls") == 0);
		if (args.length == 1) {
			File directory = new File(".");
			String[] contents = getContents(directory);
			printContents(contents, out);
		} else {

			if (args[1].charAt(0) == '-') {
				basedOnAttribute(args, out);
			} else {
				printContentsOfAllDirectories(args, out);
			}

		}

		out.close();
	}

	/**
	 * @param args
	 *            This method is called if no attribute is associated with the
	 *            arguments args are the names of directories
	 * @param out
	 * @throws IOException
	 */
	private static void printContentsOfAllDirectories(String[] args,
			BufferedWriter out) throws IOException {
		List<File> directories = new ArrayList<File>();
		/*
		 * 1 is the start index of the names of directories in input this would
		 * helpful if in future the program is used with multiple attributes it
		 * could be replaced with the start index of the names of directories
		 */
		directories = getValidDirectories(args, 1, out);

		for (File directory : directories) {
			if (args.length > 1 + 1) {
				System.out.println(directory.getName() + ":");
				out.write(directory.getName() + ":" + "\n");
			}
			String[] contents = getContents(directory);
			printContents(contents, out);
			System.out.println();
			out.write("\n");
		}
	}

	/**
	 * @param args
	 * @param start
	 *            invalid directory names in the input are reported and valid
	 *            directory names are added to the directories list
	 * @param out
	 * @return
	 * @throws IOException
	 */
	private static List<File> getValidDirectories(String[] args, int start,
			BufferedWriter out) throws IOException {
		List<File> directories = new ArrayList<File>();

		for (int i = start; i < args.length; i++) {
			File directory = new File(args[i]);
			if (directory.isDirectory())
				directories.add(directory);
			else {
				System.out.println("ls: " + args[i]
						+ ": No such file or directory");
				out.write("ls: " + args[i] + ": No such file or directory\n");
			}
		}
		// In the case of the input being just "ls" the current directory is the
		// directory
		if (start >= args.length) {
			File directory = new File(".");
			directories.add(directory);
		}
		return directories;
	}

	/**
	 * @param directory
	 *            prints the sub-files and folder names in a particular
	 *            directory
	 * @return
	 */
	private static String[] getContents(File directory) {
		String[] contents = directory.list();
		return contents;
	}

	/**
	 * @param contents
	 * @param out
	 * @throws IOException
	 *             prints the names of the non-hidden directories of the names
	 *             in contents
	 */
	private static void printContents(String[] contents, BufferedWriter out)
			throws IOException {
		for (String string : contents) {
			File subdirectory = new File(string);
			if (!subdirectory.isHidden()) {
				System.out.println(string);
				out.write(string + "\n");
			}
		}
	}

	/**
	 * is used to differentiate between "-a" and "-A"
	 */
	public enum TYPE {
		small, large;
	}

	/**
	 * @param args
	 * @param out
	 * @throws IOException
	 *             based on attribute chooses an appropriate function to perform
	 *             the operation
	 */
	private static void basedOnAttribute(String[] args, BufferedWriter out)
			throws IOException {
		TYPE state;
		// switch could be used here if in jdk7, since it supports switching
		// with
		// string

		// implement ls "-a" operation on directories
		if (args[1].compareTo("-a") == 0) {
			state = TYPE.small;
			printHiddenContentsAlong(args, state, out);

			// implement ls "-A" operation on directories
		} else if (args[1].compareTo("-A") == 0) {
			state = TYPE.large;
			printHiddenContentsAlong(args, state, out);

			// implement ls "-S" or "-t" all of them involves sorting
			// files and outputting, appropriate sorting is done later
		} else if ((args[1].compareTo("-S") == 0)
				|| (args[1].compareTo("-t") == 0)) {
			printBySortingFiles(args, out);

			// implement ls "-R" operation
		} else if (args[1].compareTo("-R") == 0) {
			RecursivelyListsubDirectories(args, out);

			// implement ls "-p" operation
		} else if (args[1].compareTo("-p") == 0) {
			endSlashForDirectories(args, out);
		}
	}

	/**
	 * @param args
	 *            implements ls "-p" operation which puts "/" for directories
	 * @param out
	 * @throws IOException
	 */
	private static void endSlashForDirectories(String[] args, BufferedWriter out)
			throws IOException {
		List<File> directories = new ArrayList<File>();

		directories = getValidDirectories(args, 2, out);

		for (File directory : directories) {
			if (args.length > 2 + 1) {
				System.out.println(directory.getName() + ":");
				out.write(directory.getName() + ":" + "\n");
			}
			File[] subFiles = directory.listFiles();
			for (File file : subFiles) {

				if (!file.isHidden()) {
					if (file.isDirectory()) {
						out.write(file.getName() + "/" + "\n");
						System.out.println(file.getName() + "/");
					} else {
						out.write(file.getName() + "\n");
						System.out.println(file.getName());
					}
				}
			}
		}
	}

	/**
	 * @param args
	 *            implements ls "-R" which recursively lists sub-Directories
	 * @param out
	 * @throws IOException
	 */
	private static void RecursivelyListsubDirectories(String[] args,
			BufferedWriter out) throws IOException {
		List<File> directories = new ArrayList<File>();

		directories = getValidDirectories(args, 2, out);

		for (File directory : directories) {
			if (args.length > 2 + 1) {
				System.out.println(directory.getName() + ":");
				out.write(directory.getName() + ":" + "\n");
			}
			printSubDirectories(directory, out);

		}
	}

	/**
	 * @param directory
	 *            is an helper to RecursivelyListsubDirectories, it prints the
	 *            contents of the directory, forms a list of the sub-directories
	 *            in the directory and prints them recursively
	 * @param out
	 * @throws IOException
	 */
	private static void printSubDirectories(File directory, BufferedWriter out)
			throws IOException {

		File[] subFiles = directory.listFiles();
		List<File> ListOfSubDirectory = new ArrayList<File>();
		for (File file : subFiles) {

			if (!file.isHidden()) {
				System.out.println(file.getName());
				out.write(file.getName() + "\n");
				if (file.isDirectory())
					ListOfSubDirectory.add(file);
			}
		}

		for (File file : ListOfSubDirectory) {
			System.out.println("\n" + file.getPath() + ":");
			out.write("\n" + file.getPath() + ":" + "\n");
			// recursive call to print sub-directories of the sub-directories
			printSubDirectories(file, out);
		}

	}

	/**
	 * @param args
	 *            sorts the content of the directory by some attribute and then
	 *            prints the contents
	 * @param out
	 * @throws IOException
	 */
	private static void printBySortingFiles(String[] args, BufferedWriter out)
			throws IOException {
		List<File> directories = new ArrayList<File>();

		directories = getValidDirectories(args, 2, out);

		for (File directory : directories) {

			if (args.length > 2 + 1) {
				System.out.println(directory + ":");
				out.write(directory + ":" + "\n");
			}
			File[] subFiles = directory.listFiles();
			// Again switch could have been used here (only JDK 7 compatible)

			// the files are sorted by size in ascending order

			if (args[1].compareTo("-S") == 0) {
				sortBySize(subFiles);

				// the files are sorted by the last modified being first to
				// display
			} else if (args[1].compareTo("-t") == 0) {
				sortByLastModifiedTime(subFiles);
			}
			// further addition if sorting of files with any other key can be
			// added here

			// the sorted file is printed
			for (File file : subFiles) {
				if (!file.isHidden()) {
					System.out.println(file.getName());
					out.write(file.getName() + "\n");
				}

			}
			out.write("\n");
			System.out.println();
		}
	}

	/**
	 * @param subFiles
	 *            the last modified time is the key to compare files
	 */

	private static void sortByLastModifiedTime(File[] subFiles) {

		Arrays.sort(subFiles, new Comparator<File>() {
			@Override
			public int compare(File file1, File file2) {
				if (file1.lastModified() > file2.lastModified()) {
					return -1;
				} else if (file1.lastModified() == file2.lastModified()) {
					return 0;
				} else
					return 1;
			}

		});

	}

	/**
	 * @param subFiles
	 *            the size of the files are used as key to sort them
	 */
	private static void sortBySize(File[] subFiles) {

		Arrays.sort(subFiles, new Comparator<File>() {
			@Override
			public int compare(File file1, File file2) {
				if (file1.length() > file2.length()) {
					return -1;
				} else if (file1.length() == file2.length()) {
					return 0;
				} else
					return 1;
			}

		});
	}

	/**
	 * @param args
	 * @param state
	 *            implements ls "-a" and "-A"
	 * @param out
	 * @throws IOException
	 */
	private static void printHiddenContentsAlong(String[] args, TYPE state,
			BufferedWriter out) throws IOException {
		List<File> directories = new ArrayList<File>();

		directories = getValidDirectories(args, 2, out);

		for (File directory : directories) {
			if (directories.size() > 1) {
				out.write(directory.getName() + ":" + "\n");
				System.out.println(directory.getName() + ":");
			}
			// in the case of ls "-a" the current and parent directories are
			// printed by . and .. ,this can be ignored if "-A"
			if (state == TYPE.small) {
				System.out.println(".");
				out.write("." + "\n");
				System.out.println("..");
				out.write("..\n");
			}

			File[] contents = directory.listFiles();
			for (File string : contents) {
				System.out.println(string.getName());
				out.write(string.getName() + "\n");
			}
			out.write("\n");
			System.out.println();
		}
	}

}
