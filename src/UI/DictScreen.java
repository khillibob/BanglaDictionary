package UI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.ReadWord;

public class DictScreen {

	private JFrame frame;
	private JTextField input;

	/**
	 * Launch the application.
	 */
	public static void dictUI(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DictScreen window = new DictScreen();
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
	public DictScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel = new JLabel("Type word");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		final JTextArea output = new JTextArea();
		GridBagConstraints gbc_output = new GridBagConstraints();
		gbc_output.gridwidth = 10;
		gbc_output.gridheight = 4;
		gbc_output.insets = new Insets(0, 0, 5, 5);
		gbc_output.fill = GridBagConstraints.BOTH;
		gbc_output.gridx = 1;
		gbc_output.gridy = 3;
		frame.getContentPane().add(output, gbc_output);
		input = new JTextField();
		GridBagConstraints gbc_input = new GridBagConstraints();
		gbc_input.gridwidth = 8;
		gbc_input.insets = new Insets(0, 0, 5, 5);
		gbc_input.fill = GridBagConstraints.HORIZONTAL;
		gbc_input.gridx = 1;
		gbc_input.gridy = 2;
		frame.getContentPane().add(input, gbc_input);
		input.setColumns(10);

		File font_file = new File(System.getProperty("user.dir")+File.separator+"src"+File.separator+"Bangla.ttf");
		 Font font;
		 try {
			font = Font.createFont(Font.TRUETYPE_FONT, font_file);

			 Font sizedFont = font.deriveFont(20f);
			 output.setFont(sizedFont);
		} catch (FontFormatException e2) {
			// TODO Auto-generated catch block
			output.setText("There are some errors!! Send email to khillibob@gmail.com");
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			output.setText("There are some errors!! Send email to khillibob@gmail.com");
			e2.printStackTrace();
		}
		JButton btnFindMeaning = new JButton("Find Meaning");
		btnFindMeaning.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent arg0) {


			//	InputStream is = MyClass.class.getResourceAsStream("TestFont.ttf");
			//	Font font = Font.createFont(Font.TRUETYPE_FONT, is);
				String ip = input.getText();
				 
				 String meaning = ReadWord.getWord(ip);
				 output.setText(meaning);
				


			}
		});


		/*btnFindMeaning.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent arg0) {
				try {
					output.setText(System.getProperty("user.dir")+"\n\n"+System.getProperty("user.home")+"\n\n"+
							DictScreen.class.getResource("Files")+"\n\n"+

DictScreen.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()

+"\n\n"+
DictScreen.class.getClassLoader().getResource("Files/a.txt")+"\n\n"+
DictScreen.class.getClassLoader().getResource("Files/").getPath()
							);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
*/

		GridBagConstraints gbc_btnFindMeaning = new GridBagConstraints();
		gbc_btnFindMeaning.insets = new Insets(0, 0, 5, 5);
		gbc_btnFindMeaning.gridx = 10;
		gbc_btnFindMeaning.gridy = 2;
		frame.getContentPane().add(btnFindMeaning, gbc_btnFindMeaning);


		JLabel lblNewLabel_1 = new JLabel("");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridx = 13;
		gbc_lblNewLabel_1.gridy = 8;
		frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);


		input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = input.getText();
				String meaning = ReadWord.getWord(ip);
				output.setText(meaning);
			}
		});
	}

}
