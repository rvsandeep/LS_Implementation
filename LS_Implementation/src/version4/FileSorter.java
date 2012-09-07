package version4;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
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

	public void sort(List<File> filesToSort, char attribute) {

		switch (attribute) {
		case 'S':
			sortBySize(filesToSort);
			break;

		case 't':
			sortByLastModifiedTime(filesToSort);
			break;
		case 'u':
			sortByLastAccessTime(filesToSort);
			break;
		case 'U':
			sortByCreatedTime(filesToSort);
			break;
		/*
		 * further addition for sorting of files with any other attribute can be
		 * done here
		 */
		}

		// the sorted fileNames is to be printed

	}

	private void sortByCreatedTime(List<File> subFiles) {
		// file Creation time is to be used for comparision
		Collections.sort(subFiles, new Comparator<File>() {
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

	private void sortByLastAccessTime(List<File> subFiles) {
		// file lastaccesstime is used for comparision
		Collections.sort(subFiles, new Comparator<File>() {
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

	private void sortByLastModifiedTime(List<File> subFiles) {

		// lastmodifiedtime is used for comparision

		Collections.sort(subFiles, new Comparator<File>() {
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

	private void sortBySize(List<File> subFiles) {
		// file size is used for comparison
		Collections.sort(subFiles, new Comparator<File>() {
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
