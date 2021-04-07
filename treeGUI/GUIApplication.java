package treeGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.SecureRandom;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import binaryTree.BSTNode;
import ioHandler.IOHandler;//creating Files names 
import student.Student;
import wiredBST.WiredBST;

/**
 * 
 * This class is responsible for 1)supplying a GUI interactive application which
 * supports the wired binary search tree operations & queries. 2)It also
 * supports a print visualization of the existing tree. 3) It support importing
 * an input file with following commands....
 * 
 * commands | insert ---> <numeric id> required delete----> <numeric id>
 * required search-----> <numeric id> required minimum maximum median
 * predecessor--> <numeric id> required successor---> <numeric id> required
 * inorder preorder postorder ---------------------------------------------- ##
 * See "inputFileExample.txt" for understand.......
 */

@SuppressWarnings("serial")
public class GUIApplication extends JFrame implements ActionListener {
	// Data decleration variables:
	WiredBST<Student> tree; // the Tree
	IOHandler ioHandler; // I/O handler for file import
	BSTNode<Student> node;
	String message;

	/* GUI instance variables: */

	// PANELS
	private final JPanel northPanel;
	private final JPanel southPanel;
	private final JPanel eastPanel;
	private final JPanel westPanel;
	private final JPanel centerPanel;
	private final JPanel controllsPanel;
	private final JPanel inputFieldsPanel;
	private final JPanel dictionaryOperationsPanel;
	private final JPanel queriesPanel;
	private final JPanel TraversalsPanel;
	private final JPanel extraFeaturesPanel;
	private final TreePrinter<Student> canvasPanel;
	private final JScrollPane canvasScrollPane;

	// BUTTONS AND CONTROLS used for doing require operation:
	private JButton insertButton;
	private JButton deleteButton;
	private JButton searchButton;

	private JButton successorButton;
	private JButton predecessorButton;

	private JButton minimumButton;
	private JButton maximumButton;

	private JButton medianButton;

	private JButton inOrderButton;
	private JButton preOrderButton;
	private JButton postOrderButton;

	private JButton printBFSButton;
	private JButton clearButton;
	private JButton importFileButton;

	private final JTextField studentIDField;
	private final JTextField studentNameField;// A top side
	private final JCheckBox displayWiresCheckBox;

	// For Labels:
	private final JLabel applicationHeaderLabel;
	private final JLabel studentIDLabel;
	private final JLabel studentNameLabel;
	private final JLabel statusBar;

	// For Icons: (only show effective buttons)
	private final Icon insertIcon;
	private final Icon deleteIcon;
	private final Icon searchIcon;
	private final Icon importIcon;
	private final Icon clearIcon;

	// for Fonts:
	private static final Font defaultFont = new Font("Tahoma", 0, 14);// "Tahoma" is font used for presenting
																		// infomation...

	// for Colors:
	private static Color green1 = new Color(152, 251, 152);// This Intensity in RED-GREEN-BLUE format
	private static Color green2 = new Color(0, 255, 152);// give RGB colour combination background
	private static Color green3 = new Color(0, 255, 127);
	private static Color green4 = new Color(102, 255, 102);

	/**
	 * This class responding for 1) Initialize all GUI components: 2)the wired
	 * binary tree to manipulate and display it:
	 * 
	 */

	public GUIApplication(WiredBST<Student> tree) {
		super("Wired Binary Search Tree Application");

		this.tree = tree;
		this.ioHandler = new IOHandler();
		this.setSize(1000, 700);
		this.setFont(defaultFont);

		// Create PANELS
		northPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel(new BorderLayout());
		eastPanel = new JPanel(new BorderLayout());
		westPanel = new JPanel(new BorderLayout());
		centerPanel = new JPanel(new BorderLayout());
		canvasPanel = new TreePrinter<Student>(tree);
		controllsPanel = new JPanel(new GridLayout(5, 0, 5, 5));
		inputFieldsPanel = new JPanel(new GridLayout(1, 0, 5, 5));
		dictionaryOperationsPanel = new JPanel(new GridLayout(1, 0, 5, 5));
		queriesPanel = new JPanel(new GridLayout(1, 0, 5, 5));
		TraversalsPanel = new JPanel(new GridLayout(1, 3, 5, 5));
		extraFeaturesPanel = new JPanel(new GridLayout(1, 0, 5, 5));

		northPanel.setBorder(BorderFactory.createLineBorder(Color.black)); // set black colour for panels
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		southPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		canvasPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		inputFieldsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		dictionaryOperationsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		queriesPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		TraversalsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		extraFeaturesPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		/*------------------------------------------------------------------------------------------- */

		// Set main panels locations:
		this.add(northPanel, BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(westPanel, BorderLayout.WEST);
		/*-----------------------------------------------------------------------------------------------------*/

		// Giving Application Title
		applicationHeaderLabel = new JLabel("Wired Binary Search Tree GUI application", SwingConstants.LEFT);
		applicationHeaderLabel.setBackground(new Color(200, 255, 175));
		applicationHeaderLabel.setOpaque(true);
		northPanel.add(applicationHeaderLabel, BorderLayout.PAGE_START);

		/*------------------------------------------------------------------------------------------------------------*/

		// Canvas panel ( This is display area section):
		canvasScrollPane = new JScrollPane(canvasPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		centerPanel.add(canvasScrollPane);

		/*------------------------------------------------------------------------------------------------------*/

		// Input fields panel:

		// for students ID/MIS
		studentIDLabel = new JLabel("Student's MIS : ");
		studentIDLabel.setFont(defaultFont);
		studentIDField = new JTextField("23", 10);
		studentIDField.setBackground(new Color(250, 250, 210));// give RGB colour combination background
		studentIDField.setFont(defaultFont);
		studentIDField.addActionListener(this);
		studentIDField.setToolTipText("Enter Students MIS number");

		// For student name
		studentNameLabel = new JLabel("Student Name : "); // (optional panel)
		studentNameLabel.setFont(defaultFont);
		studentNameField = new JTextField("A", 16);
		studentNameField.setFont(defaultFont);
		studentNameField.addActionListener(this);
		studentNameField.setToolTipText("Enter students name :");
		studentNameField.setBackground(new Color(250, 250, 210));// give RGB colour combination background

		inputFieldsPanel.add(studentIDLabel);
		inputFieldsPanel.add(studentIDField); // Adding extra mis and names fields
		inputFieldsPanel.add(studentNameLabel);
		inputFieldsPanel.add(studentNameField);
		studentIDLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		studentNameLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		studentIDField.setBorder(BorderFactory.createLineBorder(Color.black));
		studentNameField.setBorder(BorderFactory.createLineBorder(Color.black));
		/*---------------------------------------------------------------------------------------------------------------------------*/

		// For Performing Dictionary operations panels:
		insertIcon = new ImageIcon(getClass().getResource("icons\\insert.png"));// insert icon-image add in insert panel
		insertButton = new JButton("Insert", insertIcon);
		insertButton.setToolTipText("Insert new student to tree");
		insertButton.setBackground(green1);
		insertButton.addActionListener(this);

		deleteIcon = new ImageIcon(getClass().getResource("icons\\delete.png"));// delete icon-image add in delete panel
		deleteButton = new JButton("Delete", deleteIcon);
		deleteButton.setToolTipText("Delete student from tree");
		deleteButton.setBackground(green1);
		deleteButton.addActionListener(this);

		searchIcon = new ImageIcon(getClass().getResource("icons\\search.png"));// Search icon-image add in search panel
		searchButton = new JButton("Search", searchIcon);
		searchButton.setBackground(green1);
		searchButton.setToolTipText("Search for student in tree by it's id");
		searchButton.addActionListener(this);

		dictionaryOperationsPanel.add(insertButton);
		dictionaryOperationsPanel.add(deleteButton);// Adding.....
		dictionaryOperationsPanel.add(searchButton);

		/*-----------------------------------------------------------------------------------------------------------*/
		// For Queries panel:
		minimumButton = new JButton("Minimum");
		maximumButton = new JButton("Maximum"); // {create new Queries panels and named it}
		medianButton = new JButton("Median");
		predecessorButton = new JButton("Predecessor");// set names
		successorButton = new JButton("Successor");

		minimumButton.setBackground(green2);
		maximumButton.setBackground(green2);// set colours
		medianButton.setBackground(green2);
		predecessorButton.setBackground(green2);
		successorButton.setBackground(green2);
		minimumButton.addActionListener(this);
		maximumButton.addActionListener(this);
		medianButton.addActionListener(this);
		predecessorButton.addActionListener(this);
		successorButton.addActionListener(this);

		queriesPanel.add(minimumButton);
		queriesPanel.add(maximumButton);
		queriesPanel.add(medianButton); // Adding.......
		queriesPanel.add(predecessorButton);
		queriesPanel.add(successorButton);
		/*------------------------------------------------------------------------------------------------------------------------------*/

		// Tree walk traversals panel:
		inOrderButton = new JButton("Inorder tree walk");
		preOrderButton = new JButton("Preorder tree walk"); // Assign
		postOrderButton = new JButton("Postorder tree walk");

		inOrderButton.setBackground(green3);
		preOrderButton.setBackground(green3); // colour it
		postOrderButton.setBackground(green3);
		inOrderButton.addActionListener(this);
		preOrderButton.addActionListener(this);
		postOrderButton.addActionListener(this);

		TraversalsPanel.add(inOrderButton);
		TraversalsPanel.add(preOrderButton); // Adding......
		TraversalsPanel.add(postOrderButton);
		/*-----------------------------------------------------------------------------------------------------*/

		// Adding Extra Features Panel:
		importIcon = new ImageIcon(getClass().getResource("icons\\import.png"));// import icon-image add in import panel
		importFileButton = new JButton("Import input File", importIcon);
		printBFSButton = new JButton("BFS print");
		clearIcon = new ImageIcon(getClass().getResource("icons\\clear.png"));// clear icon-image add in clear panel
		clearButton = new JButton("Clear Tree", clearIcon);
		importFileButton.addActionListener(this);
		printBFSButton.addActionListener(this);
		clearButton.addActionListener(this);

		displayWiresCheckBox = new JCheckBox("Display Tree Wires");
		displayWiresCheckBox.setToolTipText("if this is checked, non null wires displayed in red colour");
		// displayWiresCheckBox.setBackground(new Color(153, 204, 255));
		displayWiresCheckBox.addActionListener(this);

		importFileButton.setBackground(green4);
		printBFSButton.setBackground(green4);
		clearButton.setBackground(green4);

		extraFeaturesPanel.add(importFileButton);
		extraFeaturesPanel.add(printBFSButton); // Adding.........
		extraFeaturesPanel.add(clearButton);
		extraFeaturesPanel.add(displayWiresCheckBox);
		/*--------------------------------------------------------------------------------------------*/

		// For Controls panel setup:
		controllsPanel.add(inputFieldsPanel);
		controllsPanel.add(dictionaryOperationsPanel); // Whole operation add in control panel
		controllsPanel.add(queriesPanel);
		controllsPanel.add(TraversalsPanel);
		controllsPanel.add(extraFeaturesPanel);

		southPanel.add(controllsPanel, BorderLayout.NORTH);

		// For Status bar section:
		statusBar = new JLabel("COEPIAN:");
		statusBar.setFont(new Font("Tahoma", Font.BOLD, 17));
		statusBar.setOpaque(true);
		// statusBar.setBackground(new Color(250, 250, 210));
		southPanel.add(statusBar, BorderLayout.SOUTH);
		statusBar.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	/**********************************************************************************************************************/

	/**
	 * GUI event handler: 1)handles all push buttons and check box: 2)Operation
	 * Performed
	 */

	public void actionPerformed(ActionEvent event) {
		// clear status bar from previous status:
		clearStatusBar();

		// valid input:
		if (!inputIsValid(event))
			return;

		// get input fields values:
		int inputID;
		if (!studentIDField.getText().isEmpty())
			inputID = Integer.parseInt(studentIDField.getText());
		else
			inputID = 0;
		String inputName = studentNameField.getText();

		// check which object fired the event and treat it accordingly:
		Object triggeringObject = event.getSource();

		// INSERT:
		if (triggeringObject == insertButton) {
			try {
				node = tree.insert(new Student(inputID, inputName));
			} catch (KeyAlreadyExistsException exception) {
				message = "Insertion failed:" + exception.getMessage() + "key must be unique!!!!!";
				displayMessage(message, JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (node != null) {
				message = String.format("Student < %s > inserted successfully", (inputID + " " + inputName));
				displayMessage(message, JOptionPane.INFORMATION_MESSAGE);
			}
			repaint();
			return;
		}
		/*----------------------------------------------------------------------------------------------------------------------------*/

		// DELETE
		else if (triggeringObject == deleteButton) {
			node = tree.delete(tree.search(tree.getRoot(), new Student(inputID, null)));
			if (node == null)
				displayMessage(("Deleteion failed: ID/MIS" + inputID + " does not exist on tree"),
						JOptionPane.ERROR_MESSAGE);
			else {
				message = String.format("Student < %s > deleted successfully", (node.getData()));
				displayMessage(message, JOptionPane.INFORMATION_MESSAGE);
			}
			repaint();
			return;
		}

		/*-----------------------------------------------------------------------------------------------------------------------------*/

		// SEARCH
		else if (triggeringObject == searchButton) {
			Student studentKey = new Student(inputID, null);
			node = tree.search(tree.getRoot(), studentKey);
			if (node == null)
				displayMessage(("Search failed: ID/MIS " + inputID + " does not exist on this tree"),
						JOptionPane.INFORMATION_MESSAGE);
			else {
				message = String.format("Student < %s > found", (node.toString()));
				displayMessage(message, JOptionPane.INFORMATION_MESSAGE);
			}
			return;
		}

		/*----------------------------------------------------------------------------------------------------------------------------------*/

		// MAXIMUM:
		else if (triggeringObject == maximumButton) {
			node = tree.getMaximum(tree.getRoot());
			if (node == null)
				displayMessage(("No maximum, tree is empty "), JOptionPane.ERROR_MESSAGE);
			else
				displayMessage((String.format("Maximum is : < %s >", node)), JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		/*---------------------------------------------------------------------------------------------------------------------*/

		// MINIMUM :
		else if (triggeringObject == minimumButton) {
			node = tree.getMinimum(tree.getRoot());
			if (node == null)
				displayMessage(("No minimum, tree is empty "), JOptionPane.ERROR_MESSAGE);
			else
				displayMessage((String.format("Minimum is : < %s >", node)), JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		/*----------------------------------------------------------------------------------------------------------------------*/

		// MEDIAN:
		else if (triggeringObject == medianButton) {
			node = tree.getMedian();
			if (node == null)
				displayMessage(("No median, tree is empty "), JOptionPane.ERROR_MESSAGE);
			else
				displayMessage((String.format("Median is : < %s >", node)), JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		/*----------------------------------------------------------------------------------------------------------------------*/

		// SUCCESSOR
		else if (triggeringObject == successorButton) {
			Student studentKey = new Student(inputID, null);
			node = tree.search(tree.getRoot(), studentKey);
			if (node == null)
				displayMessage(("successor not found: key " + inputID + " does not exist"),
						JOptionPane.INFORMATION_MESSAGE);
			else {
				node = tree.getSuccessor(node);
				if (node != null)
					displayMessage((String.format("Successor of < %d > is < %s >", inputID, node.getData())),
							JOptionPane.INFORMATION_MESSAGE);
				else
					displayMessage((String.format("<%d> is the maximum node... it has no successor.", inputID)),
							JOptionPane.INFORMATION_MESSAGE);
			}
			return;
		}

		/*-----------------------------------------------------------------------------------------------------------------*/

		// PREDECESSOR
		else if (triggeringObject == predecessorButton) {
			Student studentKey = new Student(inputID, null);
			node = tree.search(tree.getRoot(), studentKey);
			if (node == null)
				displayMessage(("predecessor not found: key " + inputID + " does not exist"),
						JOptionPane.INFORMATION_MESSAGE);
			else {
				node = tree.getPredecessor(node);
				if (node != null)
					displayMessage((String.format("Predecessor of < %d > is < %s >", inputID, node.getData())),
							JOptionPane.INFORMATION_MESSAGE);
				else
					displayMessage((String.format("<%d> is the minimum node... it has no predecessor.", inputID)),
							JOptionPane.INFORMATION_MESSAGE);
			}
			return;
		}
		/*-----------------------------------------------------------------------------------------------------------------------*/

		// IN-ORDER TREE TRAVESERAL:
		else if (triggeringObject == inOrderButton) {
			message = tree.inorderTraversal(tree.getRoot());
			System.out.println(message);
			setStatusBar(message);
			return;
		}

		// PRE-ORDER TREE TRAVESERAL:
		else if (triggeringObject == preOrderButton) {
			message = tree.preorderTraversal(tree.getRoot());
			System.out.println(message);
			setStatusBar(message);
			return;
		}

		// POST-ORDER TREE TRAVESERAL:
		else if (triggeringObject == postOrderButton) {
			message = tree.postorderTraversal(tree.getRoot());
			System.out.println(message);
			setStatusBar(message);
			return;
		}
		/*-----------------------------------------------------------------------------------------------------------------------*/

		// BREADTH-DEPTH-FIRST TREE TRAVESERAL:
		else if (triggeringObject == printBFSButton) {
			System.out.println(tree);
			setStatusBar("Breadth-Search-Scan print sent to standart output");
			return;
		}

		/*---------------------------------------------------------------------------------------------------------------*/

		// CLEAR CURRENT TREE:
		else if (triggeringObject == clearButton) {
			int dialogResult = JOptionPane.showConfirmDialog(this, "clear complete tree?");
			if (dialogResult == JOptionPane.OK_OPTION) {
				this.tree = new WiredBST<Student>();
				setStatusBar("Tree was cleared, The tree is now empty.");
				canvasPanel.setTree(tree);
				repaint();
				return;
			}
		}

		/*--------------------------------------------------------------------------------------------------------------*/

		// TOGGLE DISPLAY MODE FOR WIRES:
		else if (triggeringObject == displayWiresCheckBox) {
			if (displayWiresCheckBox.isSelected())
				canvasPanel.setDisplayWires(true);
			else
				canvasPanel.setDisplayWires(false);
			repaint();
			return;
		}

		/*----------------------------------------------------------------------------------------------------*/

		// IMPORT INPUT FILE:
		else if (triggeringObject == this.importFileButton) {
			// let user select the desired input file:
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showOpenDialog(this);

			// if user clicked Cancel button or exited dialog, return:
			if (result != JFileChooser.APPROVE_OPTION)
				return;

			// pass file to ioHandler for processing:
			try {
				File inputFile = fileChooser.getSelectedFile();
				ioHandler.processInputFile(tree, inputFile);
				repaint();
				return;
			} catch (Exception e) {
				displayMessage(("Error in opening file" + e.getMessage()), JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}

	/*-----------------------------------------------------------------------------------------------------------*/

	/** initialization method for setting GUI first time: */
	public void initGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/* Display Message on GUI and\or standard error output */
	private void displayMessage(String message, int messageType) {
		if (messageType == JOptionPane.ERROR_MESSAGE) {
			JOptionPane.showMessageDialog(this, message, "Wired BST Application", JOptionPane.ERROR_MESSAGE);
			System.err.println(message);
		} else {
			setStatusBar(message);
			System.out.println(message);
		}
	}

	/* Display messages on GUI status bar */
	private void setStatusBar(String message) {
		SecureRandom randomNumbers = new SecureRandom();
		int randomRedColor = randomNumbers.nextInt(256);
		int randomBlueColor = randomNumbers.nextInt(256);
		statusBar.setOpaque(true);
		statusBar.setText(message);
		statusBar.setBackground(new Color(randomRedColor, 255, randomBlueColor));
	}

	/* Clear status bar from previous status */
	public void clearStatusBar() {
		statusBar.setText(null);
		statusBar.setBackground(Color.LIGHT_GRAY);
	}

	/*---********************************************************************************************************************---*/

	/* Input validation tests */
	private boolean inputIsValid(ActionEvent event) {
		// validate student id input is a natural number:
		String inputID = studentIDField.getText();

		// make sure all mandatory input fields have input:
		if (inputID.isEmpty()) {
			Object triggeringObject = event.getSource();
			if ((triggeringObject == insertButton) || (triggeringObject == deleteButton)
					|| (triggeringObject == searchButton) || (triggeringObject == predecessorButton)
					|| ((triggeringObject == successorButton))) {
				displayMessage(String.format("Student ID/MIS is a mandatory field for this operation"),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} else // make sure student id/mis is positive integer:
		{
			try {
				int value = Integer.parseInt(inputID);
				if (value <= 0) {
					message = String.format("Invalid student id/mis input: \"%s\", ID/MIS must be a positive number",
							inputID);
					displayMessage(message, JOptionPane.ERROR_MESSAGE);
					return false;
				}
			} catch (NumberFormatException e) {
				message = String.format("Invalid student id/mis input: \"%s\", ID must be numeric", inputID);
				displayMessage(message, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
} // end of class
