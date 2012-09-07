package version3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author sandeeprv
 * 
 *         Is a subClass of BasedOnAttributes. It sorts the files in the
 *         mentioned directory and returns a list of sortedFileNames sorting
 *         based on size,lastModifiedTime,lastAccessTime,CreationTime are the
 *         operations implemented in this version
 * 
 */
public class FileSorter extends BasedOnAttributes {

	public void sort(List<String> toPrint, char attribute,
			List<File> validDirectories) {

		for (File directory : validDirectories) {

			if (Directories.size() > 1) {
				toPrint.add(directory + ":");
			}
			File[] subFiles = directory.listFiles();

			switch (attribute) {
			case 'S':
				sortBySize(subFiles);
				break;

			case 't':
				sortByLastModifiedTime(subFiles);
				break;
			case 'u':
				sortByLastAccessTime(subFiles);
				break;
			case 'U':
				sortByCreatedTime(subFiles);
				break;
			/*
			 * further addition for sorting of files with any other attribute
			 * can be done here
			 */
			}

			// the sorted fileNames is to be printed

			for (File file : subFiles) {
				if (!file.isHidden()) {
					toPrint.add(file.getName());
				}
			}
			toPrint.add("");
		}

	}

	private void sortByCreatedTime(File[] subFiles) {
		// file Creation time is to be used for comparision
		Arrays.sort(subFiles, new Comparator<File>() {
			@Override
			public int compare(File file1, File file2) {
				if (file1.isHidden() || file2.isHidden()) {
					return 0;
				}
				try {
					Path file1path = Paths.get(file1.getAbsolutePath());

					BasicFileAttributes attrs1 = Files.readAttributes(
							file1path, BasicFileAttributes.class);

					Path file2path = Paths.get(file2.getAbsolutePath());

					BasicFileAttributes attrs2 = Files.readAttributes(
							file2path, BasicFileAttributes.class);
					if (attrs1.creationTime().compareTo(attrs2.creationTime()) > 0) {
						return 1;
					} else if (attrs1.creationTime().compareTo(
							attrs2.creationTime()) == 0) {
						return 0;
					} else
						return -1;
				} catch (IOException e) {
					e.printStackTrace();
				}
				return 0;
			}
		});
	}

	private void sortByLastAccessTime(File[] subFiles) {
		// file lastaccesstime is used for comparision
		Arrays.sort(subFiles, new Comparator<File>() {
			@Override
			public int compare(File file1, File file2) {
				// FIXME : io execptions for file not being a directory
				if (file1.isHidden() || file2.isHidden()) {
					return 0;
				}
				try {
					Path file1path = Paths.get(file1.getAbsolutePath());

					BasicFileAttributes attrs1 = Files.readAttributes(
							file1path, BasicFileAttributes.class);

					Path file2path = Paths.get(file2.getAbsolutePath());

					BasicFileAttributes attrs2 = Files.readAttributes(
							file2path, BasicFileAttributes.class);
					if (attrs1.lastAccessTime().compareTo(
							attrs2.lastAccessTime()) > 0) {
						return -1;
					} else if (attrs1.lastAccessTime().compareTo(
							attrs2.lastAccessTime()) == 0) {
						return 0;
					} else
						return 1;
				} catch (IOException e) {
					e.printStackTrace();
				}
				return 0;
			}

		});
	}

	private void sortByLastModifiedTime(File[] subFiles) {

		// lastmodifiedtime is used for comparision

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

	private void sortBySize(File[] subFiles) {
		// file size is used for comparison
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

}
