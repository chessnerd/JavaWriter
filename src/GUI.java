
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
/**
 * GUI front-end for the Writer class
 * 
 * @author Jason Mey, Matthew Levandowski
 * @version 1.30.5, 01/24/2010
 */
public class GUI {
    
    /** The Writer object */
    private Writer w;
    
    /** The frame for the main window */
    private JFrame frame;
    
    /** The button to start writing fields */
    private JButton start;
    
    /** The text field for the user's name */
    private JTextField nameField;
    
    /** The text field for the version number or date */
    private JTextField verField;
    
    /** The text field for the class's name */
    private JTextField clNmField;
    
    /** The text field for the number of fields */
    private JTextField fNumField;
    
    /** The text field for the number of constructors */
    private JTextField cNumField;
    
    /** The text field for a field type */
    private JTextField fTypeField;
    
    /** The text field for a field name */
    private JTextField fNameField;
    
    /** The current field being worked on */
    private int currentField;
    
    /** Radio button for if the field is public */
    private JRadioButton publicRadio;
    
    /** Radio button for if the field is private */
    private JRadioButton privateRadio;
    
    /** Radio button for if the field is an instance field */
    private JRadioButton intRadio;
    
    /** Radio button for if the field is static */
    private JRadioButton staticRadio;
    
    /** Radio button for if the field is final */
    private JRadioButton finalRadio;
    
    /** Check box for whether the field has a getter method */
    private JCheckBox getter;
    
    /** Check box for whether the field has a setter method */
    private JCheckBox setter;
    
    /** The Choose Path menu item */
    private JMenuItem choosePath;
    
    /** The Exit menu item */
    private JMenuItem exit;
    
    /** The Add Main Method menu item */
    private JCheckBoxMenuItem addMainMethod;
    
    /**
     * The main method
     * 
     * @param args Command-line arguments
     */
    public static void main(String [] args) {
        new GUI();
    }
    
    /**
     * Constructor for objects of class GUI
     */
    public GUI() {
    	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	} catch (Exception e) {}
        w = new Writer();
        makeFrame();
    }
    
    /**
     * Makes the frame for the main window
     */
    public void makeFrame() {
        frame = new JFrame("Java Writer");
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
        frame.add(panel1);
        frame.getContentPane().add(panel2, BorderLayout.SOUTH);
        JLabel blank1 = new JLabel(" ");
        JLabel blank2 = new JLabel(" ");
        JLabel blank3 = new JLabel(" ");
        //JLabel blank4 = new JLabel(" ");
        JLabel nm = new JLabel("Your Name");
        JLabel ver = new JLabel("Version or Date");
        JLabel clNm = new JLabel("Class Name");
        JLabel fNum = new JLabel("Number of Fields");
        JLabel cNum = new JLabel("Number of Constructors");
        nameField = new JTextField(20);
        verField = new JTextField(20);
        clNmField = new JTextField(20);
        fNumField = new JTextField(20);
        cNumField = new JTextField(20);
        panel1.add(nm);
        panel1.add(nameField);
        panel1.add(blank1);
        panel1.add(ver);
        panel1.add(verField);
        panel1.add(blank2);
        panel1.add(clNm);
        panel1.add(clNmField);
        panel1.add(blank3);
        panel1.add(fNum);
        panel1.add(fNumField);
        //panel1.add(blank4);
        //panel1.add(cNum);
        //panel1.add(cNumField);
        cNumField.setText("0");
        nm.setAlignmentX(Component.CENTER_ALIGNMENT);
        ver.setAlignmentX(Component.CENTER_ALIGNMENT);
        clNm.setAlignmentX(Component.CENTER_ALIGNMENT);
        fNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        cNum.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        verField.setAlignmentX(Component.CENTER_ALIGNMENT);
        clNmField.setAlignmentX(Component.CENTER_ALIGNMENT);
        fNumField.setAlignmentX(Component.CENTER_ALIGNMENT);
        cNumField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        start = new JButton("Start");
        panel2.add(start);
        start.addActionListener(new MakeFieldListener());
        
        //Add a menu bar to the frame
        frame.setJMenuBar(makeMenuBar());
        
        //Sets up the frame
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Make the menu bar
     */
    public JMenuBar makeMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu options = new JMenu("Options");
        bar.add(file);
        bar.add(options);
        choosePath = new JMenuItem("Choose Path...");
        file.add(choosePath);
        file.addSeparator();
        choosePath.addActionListener(new ChoosePathListener());
        addMainMethod = new JCheckBoxMenuItem("Include Main Method");
        options.add(addMainMethod);
        addMainMethod.addActionListener(new AddMainListener());
        exit = new JMenuItem("Exit");
        file.add(exit);
        exit.addActionListener(new ExitListener());
        return bar;
    }
    
    /**
     * A listener for the make field button
     */
    private class MakeFieldListener implements ActionListener {
        /** The button to make another field */
        private JButton make;
        
        /** The frame for making fields */
        private JFrame makeField;
        
        /**
         * Opens up the Make Field window if it is not open
         * otherwise, it clears it
         */
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == start) {
                if(nameField.getText().equals("") || 
                    verField.getText().equals("") ||
                    clNmField.getText().equals("") ||
                    fNumField.getText().equals("") ||
                    cNumField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, 
                        "All fields must be entered to continue", 
                        "Missing Information", JOptionPane.ERROR_MESSAGE);
                } else {
                    currentField = 0;
                    w.setName(nameField.getText());
                    w.setDate(verField.getText());
                    w.setClassName(clNmField.getText());
                    try {
                    	Integer.parseInt(fNumField.getText());
                    } catch (Exception except) {
                    	JOptionPane.showMessageDialog(null, 
                                "The number of fields must be an integer", 
                                "Incorrect Data Type", JOptionPane.ERROR_MESSAGE);
                    	return;
                    }
                    w.setFieldArrays(Integer.parseInt(fNumField.getText()));
                    try {
                    	Integer.parseInt(cNumField.getText());
                    } catch (Exception except) {
                    	JOptionPane.showMessageDialog(null, 
                                "The number of constructors must be an integer", 
                                "Incorrect Data Type", JOptionPane.ERROR_MESSAGE);
                    	return;
                    }
                    w.setConNum(Integer.parseInt(cNumField.getText()));
                    if(w.testWrite()) {
                    	//Do nothing
                    } else {
                    	return;
                    }
                    if (Integer.parseInt(fNumField.getText()) <= 0 &&
                    	Integer.parseInt(cNumField.getText()) <= 0) {
                    	System.exit(1);
                    }
                    w.setPubPri(true, currentField);
                    w.setStatic(false, currentField);
                    w.setFinal(false, currentField);
                    makeMakeField();
                }
            } else if (e.getSource() == make) {
                if (fTypeField.getText().equals("") ||
                	fNameField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, 
                        "Field must have a name and type", 
                        "Missing Information", JOptionPane.ERROR_MESSAGE);
                } else if (!publicRadio.isSelected() &&
                    !privateRadio.isSelected()) {
                    JOptionPane.showMessageDialog(null, 
                        "Field must be either public or private", 
                        "Missing Information", JOptionPane.ERROR_MESSAGE);
                } else if (!staticRadio.isSelected() &&
                    !intRadio.isSelected()) {
                    JOptionPane.showMessageDialog(null, 
                        "Field must be either an instance or static type", 
                        "Missing Information", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (getter.isSelected()) {
                        w.getFieldGet()[currentField] = true;
                    } else {
                        w.getFieldGet()[currentField] = false;
                    }
                    if (setter.isSelected()) {
                        w.getFieldSet()[currentField] = true;
                    } else {
                        w.getFieldSet()[currentField] = false;
                    }
                    w.getFieldTypes()[currentField] = fTypeField.getText();
                    w.getFieldNames()[currentField] = fNameField.getText();
                    currentField++;
                    if (currentField >= w.getFieldNum()) {
                    	makeField.setVisible(false);
                        w.write();
                        System.exit(1);
                        return;
                    }
                    w.setPubPri(true, currentField);
                    w.setStatic(false, currentField);
                    w.setFinal(false, currentField);
                    clearMakeField();
                }
            }
        }
        
        /**
         * Makes the Make Field frame
         */
        public void makeMakeField() {
            //Removes the old frame
            frame.setVisible(false);
            
            //Creates the frame and sets up the panels
            makeField = new JFrame("Make Fields");
            Container c = makeField.getContentPane();
            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
            JPanel eastPanel = new JPanel();
            JPanel publicPanel = new JPanel();
            publicPanel.setLayout(new BoxLayout(publicPanel, BoxLayout.PAGE_AXIS));
            JPanel staticPanel = new JPanel();
            staticPanel.setLayout(new BoxLayout(staticPanel, BoxLayout.PAGE_AXIS));
            JPanel southPanel = new JPanel();
            JPanel westPanel = new JPanel();
            c.add(textPanel, BorderLayout.CENTER);
            c.add(eastPanel, BorderLayout.EAST);
            c.add(southPanel, BorderLayout.SOUTH);
            c.add(westPanel, BorderLayout.WEST);
            eastPanel.add(publicPanel);
            eastPanel.add(staticPanel);
            
            //Blank and filler labels for formatting
            JLabel blank1 = new JLabel(" ");
            JLabel blank2 = new JLabel(" ");
            JLabel blank3 = new JLabel(" ");
            JLabel blank4 = new JLabel(" ");
            JLabel blank5 = new JLabel(" ");
            JLabel blank6 = new JLabel(" ");
            JLabel filler = new JLabel("    ");
            westPanel.add(filler);
            
            //The two text fields
            JLabel fType = new JLabel("Field Type");
            JLabel fName = new JLabel("Field Name");
            fTypeField = new JTextField(15);
            fNameField = new JTextField(15);
            fType.setAlignmentX(Component.CENTER_ALIGNMENT);
            fName.setAlignmentX(Component.CENTER_ALIGNMENT);
            fTypeField.setAlignmentX(Component.CENTER_ALIGNMENT);
            fNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
            textPanel.add(blank1);
            textPanel.add(fType);
            textPanel.add(fTypeField);
            textPanel.add(blank2);
            textPanel.add(fName);
            textPanel.add(fNameField);
            textPanel.add(blank3);
            
            //Private vs Public radio buttons
            ButtonGroup pubPri = new ButtonGroup();
            publicRadio = new JRadioButton("Public");
            privateRadio = new JRadioButton("Private", true);
            pubPri.add(publicRadio);
            pubPri.add(privateRadio);
            publicPanel.add(blank4);
            publicPanel.add(privateRadio);
            publicPanel.add(publicRadio);
            publicPanel.add(blank5);
            
            //Instance vs Static radio buttons
            ButtonGroup statInt = new ButtonGroup();
            intRadio = new JRadioButton("Instance", true);
            staticRadio = new JRadioButton("Static");
            finalRadio = new JRadioButton("Final");
            statInt.add(intRadio);
            statInt.add(staticRadio);
            staticPanel.add(blank6);
            staticPanel.add(intRadio);
            staticPanel.add(staticRadio);
            staticPanel.add(finalRadio);
            
            //Adds radio button action listeners
            RadioListener rl = new RadioListener();
            publicRadio.addActionListener(rl);
            privateRadio.addActionListener(rl);
            intRadio.addActionListener(rl);
            staticRadio.addActionListener(rl);
            finalRadio.addActionListener(rl);
            
            //Sets up the south panel
            getter = new JCheckBox("Getter Method");
            setter = new JCheckBox("Setter Method");
            make = new JButton("Make Field");
            southPanel.add(getter);
            southPanel.add(setter);
            southPanel.add(make, FlowLayout.RIGHT);
            make.addActionListener(this);
            
            //Sets up the frame
            makeField.pack();
            makeField.setResizable(false);
            makeField.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            makeField.setLocationRelativeTo(null);
            makeField.setVisible(true);
        }
        
        /**
         * Clears the Make Field frame
         */
        public void clearMakeField() {
            fTypeField.setText("");
            fNameField.setText("");
            privateRadio.setSelected(true);
            intRadio.setSelected(true);
            finalRadio.setSelected(false);
            getter.setSelected(false);
            getter.setEnabled(true);
            setter.setSelected(false);
            setter.setEnabled(true);
        }
    }
    
    /**
     * A listener for the "Exit" menu item
     */
    private class ExitListener implements ActionListener {
        /**
         * Quits the program
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    /**
     * A listener for the "Add Main Method" menu item
     */
    private class AddMainListener implements ActionListener {
        /**
         * Adds or removes the main method
         */
        public void actionPerformed(ActionEvent e) {
            w.needsMain();
        }
    }
    
    /**
     * A listener for the Radio Buttons
     */
    private class RadioListener implements ActionListener {
        /**
         * Takes an action depending on what radio button
         * was clicked
         */
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == privateRadio) {
                w.getPubPri()[currentField] = "private ";
                getter.setEnabled(true);
                setter.setEnabled(true);
            } else if (e.getSource() == publicRadio) {
                w.getPubPri()[currentField] = "public ";
                getter.setEnabled(false);
                getter.setSelected(false);
                setter.setEnabled(false);
                setter.setSelected(false);
            } else if (e.getSource() == intRadio) {
                w.setStatic(false, currentField);
            } else if (e.getSource() == staticRadio) {
                w.setStatic(true, currentField);
            } else if (e.getSource() == finalRadio) {
                w.setFinal(true, currentField);
            }
        }
    }
    
    /**
     * A listener for the "Choose Path" menu item
     */
    private class ChoosePathListener implements ActionListener {
    	/**
         * Opens a file chooser for the user to choose a new path
         */
        public void actionPerformed(ActionEvent e) {
        	if (e.getSource() == choosePath) {
        		JFileChooser chooser = new JFileChooser();
            	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            	chooser.setDialogTitle("Choose a Directory...");
            	chooser.setAcceptAllFileFilterUsed(false);
            	File f = new File(".");
            	try {
            		f = new File(f.getCanonicalPath());
            	} catch (Exception except) {}
                chooser.setCurrentDirectory(f);
                chooser.showOpenDialog(null);
            	File chosenDir = chooser.getSelectedFile();
            	String dirString = "";
            	try {
            		dirString = chosenDir.getCanonicalPath();
            	} catch (Exception except) {}
            	if (chosenDir == null) {
            		return;
            	}
            	if (dirString.equals("")) {
            		w.setPath(chosenDir.getPath());
            	} else {
            		w.setPath(dirString);
            	}
        	}
        }
    }
}
