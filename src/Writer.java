import java.io.*;
import javax.swing.JOptionPane;

/**
 * Writes a skeleton of a Java class given a set of information
 * 
 * @author Jason Mey, Matthew Levandowski
 * @version 1.70.3, 01/24/2010
 */
public class Writer {

	/** Used to allow the saving of the file */
	private File file;

	/** The path given by the user for the file */
	private String path;

	/** Whether the path has or has not been set by the user */
	private boolean pathNotSet;

	/** Whether the file needs a main method */
	private boolean needsMain;

	/** The name of the Java files being created */
	private String fileName;

	/** The name of the user */
	private String name;

	/** The current date */
	private String date;

	/** The name of the class being created */
	private String className;

	/** Number of fields in the class */
	private int fieldNum;

	/** Number of constructors in the class */
	private int conNum;

	/** If the field is static */
	private String[] stat;

	/** If the field is final */
	private String[] fin;

	/** Whether the field is public or private */
	private String[] pubPri;

	/** Stores the types of the fields for the class */
	private String[] fieldTypes;

	/** Stores the names of the fields for the class */
	private String[] fieldNames;

	/** Whether the field has a getter method */
	private boolean[] fieldGet;

	/** Whether the field has a setter method */
	private boolean[] fieldSet;


	/**
	 * Constructor for the Writer class
	 * 
	 * Takes in basic info about the user and the class to be made
	 */
	public Writer() {
		pathNotSet = true;
		needsMain = false;
	}


	/**
	 * Sets the arrays for the fields
	 * 
	 * @param num
	 *            the number of fields
	 */
	public void setFieldArrays(int num) {
		fieldNum = num;
		stat = new String[fieldNum];
		fin = new String[fieldNum];
		fieldGet = new boolean[fieldNum];
		fieldSet = new boolean[fieldNum];
		fieldTypes = new String[fieldNum];
		fieldNames = new String[fieldNum];
		pubPri = new String[fieldNum];
	}


	/**
	 * Capitalizes a String
	 * 
	 * @param s
	 *            - the String to be capitalized
	 * @return - the capitalized String
	 */
	public String capitalize(String s) {
		String capString = "";
		for (int i = 0; i < s.length(); i++) {
			String letter = "" + s.charAt(i);
			if (i == 0) {
				letter = letter.toUpperCase();
			}
			capString = capString + letter;
		}
		return capString;
	}


	/**
	 * A method that takes a multi-word field name and splits it into
	 * separate words broken up by spaces
	 */
	public String splitFieldName(String s) {
		String splitString = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			// If any two letters in a row are uppercase, return the
			// original String
			if (Character.isUpperCase(c)
					&& Character.isUpperCase(s.charAt(i + 1))) {
				return s;
			}
			if (i != 0 && Character.isUpperCase(c)) {
				splitString = splitString + " "
						+ Character.toLowerCase(c);
			} else if (i == 0 && Character.isUpperCase(c)) {
				splitString = splitString + Character.toLowerCase(c);
			} else {
				splitString = splitString + c;
			}
		}
		return splitString;
	}


	/**
	 * Tests if the file will write
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean testWrite() {
		file = new File(".");
		String filePath;
		boolean hasError = false;
		PrintWriter printer = null;
		if (pathNotSet) {
			filePath = file.getAbsolutePath();
			int length = filePath.length();
			filePath = filePath.substring(0, length - 1)
					+ filePath.substring(length);
		} else {
			filePath = path;
		}
		String filePath2 = filePath;
		filePath = filePath + className + ".java";
		try {
			printer = new PrintWriter(new FileWriter(filePath));
		} catch (Exception e) {
			hasError = true;
		}
		if (hasError) {
			JOptionPane.showMessageDialog(null, "Path " + filePath2
					+ " is not a writable location",
					"File Creation Failed", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (printer == null) {
			JOptionPane.showMessageDialog(null,
					"Unknown error has occurred", "Unknown Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		return true;
	}


	/**
	 * Writes the class to a file
	 */
	public void write() {
		// Sets up the file path
		file = new File(".");
		String filePath;
		if (pathNotSet) {
			filePath = file.getAbsolutePath();
			int length = filePath.length();
			filePath = filePath.substring(0, length - 1)
					+ filePath.substring(length);
		} else {
			filePath = path;
		}
		filePath = filePath + className + ".java";

		// Makes a PrintWriter to print the file
		PrintWriter printer = null;
		boolean hasError = false;
		try {
			printer = new PrintWriter(new FileWriter(filePath));
		} catch (Exception e) {
			hasError = true;
		}

		// Checks if any errors occurred
		if (hasError) {
			JOptionPane.showMessageDialog(null,
					"Could not create file at " + filePath,
					"File Creation Failed", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		if (printer == null) {
			JOptionPane.showMessageDialog(null,
					"Unknown error has occurred", "Unknown Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		// Prints the file
		// Start of the class
		printer.println("");
		printer.println("/**");
		printer.println(" * Write a description of your class here.");
		printer.println(" * ");
		printer.println(" * @author " + name);
		printer.println(" * @version " + date);
		printer.println(" */");
		printer.println("public class " + className + " {");
		printer.println("    ");

		// Fields
		for (int i = 0; i < fieldNum; i++) {
			if (fieldTypes[i].equals("boolean")) {
				printer.println("    /** Whether the "
						+ splitFieldName(className) + " "
						+ splitFieldName(fieldNames[i]) + "*/");
			} else {
				printer.println("    /** The "
						+ splitFieldName(fieldNames[i]) + " of the "
						+ splitFieldName(className) + " */");
			}
			printer.println("    " + pubPri[i] + stat[i] + fin[i]
					+ fieldTypes[i] + " " + fieldNames[i] + ";");
			printer.println("    ");
		}

		// Blank Constructor
		printer.println("    /**");
		printer.println("     * Constructor for " + className
				+ " objects");
		printer.println("     */");
		printer.println("    public " + className + "() {");
		printer.println("        ");
		printer.println("    }");
		printer.println("    ");

		// Full Constructor
		printer.println("    /**");
		printer.println("     * Constructor for " + className
				+ " objects");
		printer.println("     * ");
		for (int i = 0; i < fieldNames.length; i++) {
			if (fin[i].equals("final ")) {
				// Do nothing
			} else if (fieldTypes[i].equals("boolean")) {
				printer.println("     * @param p"
						+ capitalize(fieldNames[i]) + " whether the "
						+ splitFieldName(className) + " "
						+ splitFieldName(fieldNames[i]));
			} else {
				printer.println("     * @param p"
						+ capitalize(fieldNames[i]) + " the "
						+ splitFieldName(fieldNames[i]) + " of the "
						+ splitFieldName(className) + "");
			}
		}
		printer.println("     */");
		printer.print("    public " + className + "(");
		boolean hasBeenNonFinal = false;
		for (int i = 0; i < fieldNames.length; i++) {
			if (fin[i].equals("")) {
				hasBeenNonFinal = true;
			}
			if (fin[i].equals("final ")) {
				// Do nothing
			} else if (i > 0 && hasBeenNonFinal) {
				printer.print(", " + fieldTypes[i] + " p"
						+ capitalize(fieldNames[i]));
			} else {
				printer.print(fieldTypes[i] + " p"
						+ capitalize(fieldNames[i]));
			}
		}
		printer.println(") {");
		for (int i = 0; i < fieldNames.length; i++) {
			if (fin[i].equals("final ")) {
				// Do nothing
			} else {
				printer.println("        " + fieldNames[i] + " = p"
						+ capitalize(fieldNames[i]) + ";");
			}
		}
		printer.println("    }");
		printer.println("    ");

		// Main method
		if (needsMain) {
			printer.println("    /**");
			printer.println("     * The main method");
			printer.println("     */");
			printer
					.println("    public static void main(String[] args) {");
			printer.println("        ");
			printer.println("    }");
			printer.println("    ");
		}

		// Getter and Setter Methods
		for (int i = 0; i < fieldNum; i++) {
			if (fieldGet[i]) {
				printer.println("    /**");
				printer.println("     * Accessor method for the "
						+ fieldNames[i] + " field");
				printer.println("     * ");
				printer.println("     * @return gives the "
						+ splitFieldName(className) + "'s "
						+ splitFieldName(fieldNames[i]));
				printer.println("     */");
				printer.println("    public " + stat[i] + fieldTypes[i]
						+ " get" + capitalize(fieldNames[i]) + "(){");
				printer
						.println("        return " + fieldNames[i]
								+ ";");
				printer.println("    }");
				printer.println("    ");
			}
			if (fieldSet[i]) {
				printer.println("    /**");
				printer.println("     * Mutator method for the "
						+ fieldNames[i] + " field");
				printer.println("     * ");
				printer.println("     * @param p"
						+ capitalize(fieldNames[i]) + " takes a new "
						+ fieldTypes[i] + " for the "
						+ splitFieldName(className) + "'s "
						+ splitFieldName(fieldNames[i]));
				printer.println("     */");
				printer.println("    public " + stat[i] + "void set"
						+ capitalize(fieldNames[i]) + "("
						+ fieldTypes[i] + " p"
						+ capitalize(fieldNames[i]) + "){");
				printer.println("        " + fieldNames[i] + " = p"
						+ capitalize(fieldNames[i]) + ";");
				printer.println("    }");
				printer.println("    ");
			}
		}

		// End with bracket and close the file
		printer.println("}");
		printer.close();

		// Display success message
		JOptionPane.showMessageDialog(null,
				"File successfully created at " + filePath,
				"File Creation", JOptionPane.INFORMATION_MESSAGE);
	}


	/**
	 * Accessor method for the fileName field
	 * 
	 * @return gives the file name of the written file
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * Mutator method for the fileName field
	 * 
	 * @param pFileName
	 *            takes a new String for the file name
	 */
	public void setFileName(String pFileName) {
		fileName = pFileName;
	}


	/**
	 * Mutator method for the path field
	 * 
	 * @param pPath
	 *            takes a new String for the path
	 */
	public void setPath(String pPath) {
		path = pPath;
		// Check to make sure that the directory path ends with a slash
		if (System.getProperty("os.name").toLowerCase().contains(
				"windows")) {
			if (path.charAt(path.length() - 1) != '\\') {
				path = path + '\\';
			}
		} else {
			if (path.charAt(path.length() - 1) != '/') {
				path = path + '/';
			}
		}
		pathNotSet = false;
	}


	/**
	 * Accessor method for the name field
	 * 
	 * @return gives the user's name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Mutator method for the name field
	 * 
	 * @param pName
	 *            takes a new String for name
	 */
	public void setName(String pName) {
		name = pName;
	}


	/**
	 * Changes whether the file needs a main method
	 */
	public void needsMain() {
		if (needsMain) {
			needsMain = false;
		} else {
			needsMain = true;
		}
	}


	/**
	 * Accessor method for the date field
	 * 
	 * @return gives the current date
	 */
	public String getDate() {
		return date;
	}


	/**
	 * Mutator method for the date field
	 * 
	 * @param pDate
	 *            takes a new String for date
	 */
	public void setDate(String pDate) {
		date = pDate;
	}


	/**
	 * Accessor method for the className field
	 * 
	 * @return gives the class name
	 */
	public String getClassName() {
		return className;
	}


	/**
	 * Mutator method for the className field
	 * 
	 * @param pClassName
	 *            takes a new String for className
	 */
	public void setClassName(String pClassName) {
		className = pClassName;
	}


	/**
	 * Accessor method for the fieldNum field
	 * 
	 * @return gives the number of fields
	 */
	public int getFieldNum() {
		return fieldNum;
	}


	/**
	 * Accessor method for the conNum field
	 * 
	 * @return gives the number of constructors
	 */
	public int getConNum() {
		return conNum;
	}


	/**
	 * Mutator method for the conNum field
	 * 
	 * @param pConNum
	 *            takes a new int for conNum
	 */
	public void setConNum(int pConNum) {
		conNum = pConNum;
	}


	/**
	 * Makes the current field private or public
	 * 
	 * @param b
	 *            whether the field is private
	 * @param i
	 *            the field being set for
	 */
	public void setPubPri(boolean b, int i) {
		if (b) {
			pubPri[i] = "private ";
		} else {
			pubPri[i] = "public ";
		}
	}


	/**
	 * Makes the current field static
	 * 
	 * @param b
	 *            whether the field is static
	 * @param i
	 *            the field being set for
	 */
	public void setStatic(boolean b, int i) {
		if (b) {
			stat[i] = "static ";
		} else {
			stat[i] = "";
		}
	}


	/**
	 * Makes the current field final
	 * 
	 * @param b
	 *            whether the field is final
	 * @param i
	 *            the field being set for
	 */
	public void setFinal(boolean b, int i) {
		if (b) {
			fin[i] = "final ";
		} else {
			fin[i] = "";
		}
	}


	/**
	 * Accessor method for the pubPri field
	 * 
	 * @return gives the array of field types
	 */
	public String[] getPubPri() {
		return pubPri;
	}


	/**
	 * Accessor method for the fieldTypes field
	 * 
	 * @return gives the array of field types
	 */
	public String[] getFieldTypes() {
		return fieldTypes;
	}


	/**
	 * Accessor method for the fieldNames field
	 * 
	 * @return gives the array of field names
	 */
	public String[] getFieldNames() {
		return fieldNames;
	}


	/**
	 * Accessor method for the fieldGet field
	 * 
	 * @return gives the array of getter methods
	 */
	public boolean[] getFieldGet() {
		return fieldGet;
	}


	/**
	 * Accessor method for the fieldSet field
	 * 
	 * @return gives the array of setter methods
	 */
	public boolean[] getFieldSet() {
		return fieldSet;
	}
}