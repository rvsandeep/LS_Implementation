package version3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author sandeeprv
 * 
 *         Implementing "ls" opertion The operations implemented are:
 * 
 *         "-A" : List all entries except for . and .. , "-a" : Include
 *         directory entries whose names begin with a dot(.). "-R" : Recursively
 *         list sub-directories encountered. "-S" : Sort files by size. "-t" :
 *         Sort by time modified (most recently modified first) before sorting
 *         the operands by lexicographical order. "-u" : Use time of last access
 *         for sorting. "-F" : Display a slash (`/') immediately after each
 *         pathname that is a directory, an asterisk (`*') after each that is
 *         executable, an at sign (`@') after each symbolic link. "-U" : Use
 *         time of file creation for sorting.
 * 
 *         All the operations are chosen based on the attributes implemented in
 *         BasedOnAttributes.java Class and Operations involving Sorting Files
 *         are implemented in FileSorted.java.
 */
public class LsImplementation implements LsInterface {

	class BitVector {

		private int bit, index;

		BitVector(int bit, int index) {
			this.setBit(bit);
			this.setIndex(index);
		}

		public int getBit() {
			return bit;
		}

		public void setBit(int bit) {
			this.bit = bit;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	List<BitVector> bitVector = new ArrayList<BitVector>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see version3.LsInterface#setAttributes(java.lang.String[])
	 * 
	 * initializes the Attributes list to all the attributes provided through
	 * the Command Prompt
	 */
	@Override
	public void setAttributes(String[] arguments) {
		int index = 0;
		for (int i = 1; (i < arguments.length)
				&& (arguments[i].charAt(0) == '-'); i++) {
			char[] attributes = arguments[i].toCharArray();
			for (int j = 1; j < attributes.length; j++) {
				add(attributes[j], index);
				Attributes.add(attributes[j]);
				index++;
			}
		}
		sortBitVector();
		resetAttribtues(new ArrayList<Character>(Attributes));

	}

	/**
	 * Resets the Attributes in order of the first preferred execution
	 * 
	 * @param attributes
	 *            , a copy of the Attributes
	 */
	private void resetAttribtues(List<Character> attributes) {
		Attributes.clear();
		for (int i = 0; i < bitVector.size(); i++) {
			Attributes.add(attributes.get(bitVector.get(i).index));
		}
	}

	private void sortBitVector() {
		Collections.sort(bitVector, new Comparator<BitVector>() {
			@Override
			public int compare(BitVector bitVector1, BitVector bitVector2) {
				if (bitVector1.bit < bitVector2.bit)
					return -1;
				else if (bitVector1.bit == bitVector2.bit)
					return 0;
				else
					return 1;
			}
		});
	}

	private void add(char c, int index) {
		int bit = getBit(c);
		bitVector.add(new BitVector(bit, index));
	}

	private int getBit(char c) {
		switch (c) {
		case 'R':
			return 1;

		case 'a':
		case 'A':
			return 2;

		case 'p':
		case 'F':
			return 3;

		case 'S':
		case 't':
		case 'u':
		case 'U':
			return 4;
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see version3.LsInterface#setDirectories(java.lang.String[])
	 * 
	 * initializes the Directories list to all the names of Directories provided
	 * through the Command Prompt.
	 */
	@Override
	public void setDirectories(String[] arguments) {
		int j;
		for (j = 1; (j < arguments.length) && (arguments[j].charAt(0) == '-'); j++)
			;

		for (int i = j; i < arguments.length; i++) {
			Directories.add(arguments[i]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see version3.LsInterface#performOperation()
	 * 
	 * Class BasedOnAttributes chooses the operation based on Attributes,
	 * performs them and returns a list of strings to be printed to the screen
	 */
	@Override
	public List<String> performOperation() throws IOException {
		List<String> toPrint = new ArrayList<String>();
		BasedOnAttributes Operation = new BasedOnAttributes();
		toPrint = Operation.perform();
		return toPrint;
	}
}
