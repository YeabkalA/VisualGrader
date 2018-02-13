import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

public class GraderGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraderGUI frame = new GraderGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GraderGUI() {
		JFileChooser fileChooser = new JFileChooser();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1600, 750);
		setTitle("Grader");
		contentPane = new JPanel();
		
		contentPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
			}
		});
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblChooseYourFiles = new JLabel("Choose your files and folders");
		lblChooseYourFiles.setFont(new Font("Verdana", Font.BOLD, 20));
		
		JLabel lblHighestScore = new JLabel("Highest Score");
		
		JLabel lblLowestScore = new JLabel("Lowest Score");
		
		JLabel lblMedianScore = new JLabel("Median Score");
		
		JLabel lblthPercentile = new JLabel("25th, 75th Percentile");
		

		lblthPercentile.setVisible(false);
		lblMedianScore.setVisible(false);
		lblHighestScore.setVisible(false);
		lblLowestScore.setVisible(false);
		
		JButton btnSaveGradesAnd = new JButton("Save Grades");
		btnSaveGradesAnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {  
				  try {
					PrintWriter writer1 = new PrintWriter(new File("/home/ywubshit/Desktop/results.txt"));
					for(Student s: Action.students) {
						writer1.write(s.getName() + "-" + s.getGrade().getRawGrade());
						writer1.write("\n");
					}
					 writer1.flush();  
			         writer1.close();  
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
			}
		});
		btnSaveGradesAnd.setVisible(false);
		JButton btnEmailGrades = new JButton("Email Grades");
		btnEmailGrades.setVisible(false);
		btnEmailGrades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!Action.noStudent())
					Action.emailGrades();
			}
		});
		
		JButton btnSetStudentData = new JButton("Set Student Data Source");
		btnSetStudentData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Action.setStudentsDataPath(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		
		JButton btnSetTeacherData = new JButton("Set Teacher Data Source");
		btnSetTeacherData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Action.setTeacherDataPath(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		
		JButton btnSetSheetsData = new JButton("Set Sheets Data Source");
		btnSetSheetsData.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Action.setAnswerSheetPath(fileChooser.getCurrentDirectory().getPath());
			}
		});
		JButton btnGrade = new JButton("Grade");
		btnGrade.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Action.setExamName(textField.getText());
				if(Action.getAnsSheetDataPath().equals("")
						|| Action.getKeyPath().equals("") || Action.getStudentsDataPath().equals("")
						|| Action.getTeacherDataPath().equals(""))
					JOptionPane.showMessageDialog(null, "Please set each data");
				else {
					String[] args = {"sa"};
					try {
						Action.main(args);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					btnEmailGrades.setVisible(true);
					lblthPercentile.setVisible(true);
					lblMedianScore.setVisible(true);
					lblHighestScore.setVisible(true);
					lblLowestScore.setVisible(true);
					btnSaveGradesAnd.setVisible(true);
					btnEmailGrades.setVisible(true);
					lblthPercentile.setText("25th, 75th percentiles: " + Action.getStat()[2]+","+Action.getStat()[3]);
					lblMedianScore.setText("Median score: " + Integer.toString(Action.getStat()[4]));
					lblHighestScore.setText("Highest score: " + Integer.toString(Action.getStat()[1]));
					lblLowestScore.setText("Lowest score: " + Integer.toString(Action.getStat()[0]));
					
				}

			}
		});
		
		JButton btnSetSavingDestination = new JButton("Set Key Source");
		btnSetSavingDestination.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Action.setKeyPath(fileChooser.getSelectedFile().getAbsolutePath());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnRestart = new JButton("Restart");
		btnRestart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Action.setAnswerSheetPath("");
				Action.setExamName("");
				try {
					Action.setKeyPath("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				textField.setText("");
				Action.resetGrades();
				Action.resetStudents();
				btnSaveGradesAnd.setVisible(false);
				btnEmailGrades.setVisible(false);
				lblthPercentile.setVisible(false);
				lblMedianScore.setVisible(false);
				lblHighestScore.setVisible(false);
				lblLowestScore.setVisible(false);
			}
		});
		
		JLabel lblExamName = new JLabel("Exam Name");
		
		JLabel lblAutograder = new JLabel("Auto-Grader");
		lblAutograder.setFont(new Font("Verdana", Font.BOLD, 25));
		


		
	
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(1058, Short.MAX_VALUE)
					.addComponent(lblExamName)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)
					.addGap(173))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(76)
					.addComponent(lblAutograder)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblthPercentile, GroupLayout.PREFERRED_SIZE, 196, Short.MAX_VALUE)
							.addGap(0)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnRestart, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
									.addGap(57)
									.addComponent(btnGrade, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
									.addGap(149))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(649)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(fileChooser, GroupLayout.PREFERRED_SIZE, 638, Short.MAX_VALUE)
											.addGap(6))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(btnSetSheetsData, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
												.addComponent(btnSetStudentData, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE))
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addComponent(btnSetSavingDestination, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnSetTeacherData, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
											.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE))
										.addComponent(lblChooseYourFiles)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblMedianScore, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
							.addGap(1163))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblLowestScore, GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
							.addGap(1163))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblHighestScore, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
							.addGap(1160))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnSaveGradesAnd)
							.addGap(18)
							.addComponent(btnEmailGrades, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)))
					.addGap(12))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(27)
					.addComponent(lblChooseYourFiles)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblAutograder)
							.addGap(63)
							.addComponent(lblthPercentile)
							.addGap(44)
							.addComponent(lblHighestScore, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
							.addGap(45)
							.addComponent(lblLowestScore, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
							.addGap(47)
							.addComponent(lblMedianScore, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
							.addGap(87)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSaveGradesAnd)
								.addComponent(btnEmailGrades))
							.addGap(75))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(fileChooser, GroupLayout.PREFERRED_SIZE, 513, Short.MAX_VALUE)
							.addGap(29)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblExamName)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSetStudentData, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSetTeacherData, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSetSheetsData, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSetSavingDestination, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnRestart)
						.addComponent(btnGrade, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(27))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
