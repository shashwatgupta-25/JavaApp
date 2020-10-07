package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class SimpleTextEditor {
	
	private final String title = "Simple Text Editor";

	private JFrame frame;
	private JTextArea textArea;
	private File openFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					SimpleTextEditor window = new SimpleTextEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SimpleTextEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setAutoRequestFocus(false);
		frame.setTitle(title);
		frame.setBounds(100, 100, 616, 444);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setForeground(Color.BLACK);
		textArea.setFont(new Font("Arial", Font.PLAIN, 14));
		frame.getContentPane().add(textArea, BorderLayout.NORTH);
		
		JScrollPane scroll = new JScrollPane(textArea);
		frame.getContentPane().add(scroll, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(0, 5, 0, 0));
		menuBar.setBackground(Color.BLACK);
		menuBar.setForeground(Color.WHITE);
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Open");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				open();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save As");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				create();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Save");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Close");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_3);
		
	}
	
	private void open() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Select a file To Open");
			chooser.showOpenDialog(null);
			
			File selectedFile = chooser.getSelectedFile();
			if(!selectedFile.exists()) {
				//Error message 
				JOptionPane.showMessageDialog(null, "Failed to open a file, file doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
				openFile = null;
				return ;
			}
			
			Scanner reader = new Scanner(selectedFile);
			String contents = "";
			while(reader.hasNextLine()) {
				contents += reader.nextLine()+"\n";
			}
			reader.close();
			textArea.setText(contents);
			
			openFile = selectedFile;
			
			frame.setTitle(title + " - " + selectedFile.getName());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void create() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Choose location to Save");
			chooser.showSaveDialog(null);
			
			openFile = chooser.getSelectedFile();
			
			save();
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void save() {
		try {
			if(openFile == null) {
				//Error Message
				JOptionPane.showMessageDialog(null, "Can't Save , No file is selected", "Error", JOptionPane.ERROR_MESSAGE);
				return ;
			}
			
			String contents = textArea.getText();
			
			Formatter form = new Formatter(openFile);
			form.format("%s", contents);
			form.close();
			
			frame.setTitle(title + " - " + openFile.getName());
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void close() {
		if(openFile == null) {
			JOptionPane.showMessageDialog(null, "Failed to close the file,No file is selected", "Error", JOptionPane.ERROR_MESSAGE);
			return ;
		}
		try {
			
			int input = JOptionPane.showConfirmDialog(null,"Do You want to save before closing?", "Wait!" ,JOptionPane.YES_NO_OPTION);
			if(input == JOptionPane.YES_OPTION) {
				save();
			}
			
			textArea.setText("");
			openFile = null;
			frame.setTitle(title);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}