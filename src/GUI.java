import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class GUI extends JFrame{
	String filepath;
	LinkedList<String> CourseData;
	Popularity popularity;
	int popularitySize;
	Generic generic;
	CurriculumFactory curriculumFactory;

	GridBagConstraints c;
	JTabbedPane tabbedPane;
	JTable table1;
	JScrollPane scrollPane1;
	JTable table2;
	JScrollPane scrollPane2;
	JTable table3;
	JScrollPane scrollPane3;
	JTable table4;
	JScrollPane scrollPane4;
	JTable table5;
	JScrollPane scrollPane5;
	JButton b1;
	JButton b2;
	JButton b3;
	JButton b4;
	JTextField textfield1;
	JLabel label1;
	int round = 0;
	String[] columnNames = {"","一","二","三","四","五"};
	
	public GUI(){
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		build_table();
		Object[][] data = {};
		build_table1(data);
		build_table2(data);
		build_table3(data);
		build_table4(data);
		build_table5(data);
		build_setting();
		this.setTitle("基因演算法實作");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setVisible(true);
	}

	private void build_table(){
		tabbedPane = new JTabbedPane();
		c.weightx = 0.75;
		c.weighty = 1;
		this.add(tabbedPane, c);
	}

	private void build_table1(Object[][] data){
		table1 = new JTable(data, columnNames);
		scrollPane1 = new JScrollPane(table1);
		tabbedPane.addTab("大一課表", scrollPane1);
	}

	private void build_table2(Object[][] data){
		table2 = new JTable(data, columnNames);
		scrollPane2 = new JScrollPane(table2);
		tabbedPane.addTab("大二課表", scrollPane2);
	}

	private void build_table3(Object[][] data){
		table3 = new JTable(data, columnNames);
		scrollPane3 = new JScrollPane(table3);
		tabbedPane.addTab("大三課表", scrollPane3);
	}

	private void build_table4(Object[][] data){
		table4 = new JTable(data, columnNames);
		scrollPane4 = new JScrollPane(table4);
		tabbedPane.addTab("大四課表", scrollPane4);
	}

	private void build_table5(Object[][] data){
		table5 = new JTable(data, columnNames);
		scrollPane5 = new JScrollPane(table5);
		tabbedPane.addTab("碩士班課表", scrollPane5);
	}

	private void build_setting() {
		JPanel panel = new JPanel(new GridLayout(0,1));
		c.weightx = 0.25;

		b1 = new JButton("載入課表");
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
				int returnVal = fc.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					load(fc.getSelectedFile().getAbsolutePath());
					b2.setEnabled(true);
					b3.setEnabled(true);
					b4.setEnabled(true);
				}
				else {
					JOptionPane.showMessageDialog(null,
							"No input file",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		panel.add(b1);

		b2 = new JButton("開始計算");
		b2.setEnabled(false);
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					round = Integer.parseInt(textfield1.getText());
					run();
				} catch (NumberFormatException exception){
					JOptionPane.showMessageDialog(null,
							"Input error",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					textfield1.setText("1000");
				}
			}
		});
		panel.add(b2);

		b3 = new JButton("重置");
		b3.setEnabled(false);
		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load(filepath);
			}
		});
		panel.add(b3);
		
		b4 = new JButton("儲存");
		b4.setEnabled(false);
		b4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PrintWriter writer = new PrintWriter("school_table.txt","UTF-8");
					Curriculum curriculum = getBest();
					for (int i=0;i<curriculum.size();i++) {
						writer.println(curriculum.getCourse(i).toString());
					}
					writer.close();
					JOptionPane.showMessageDialog(null,
							"Curriculum save to school_table.txt successfully",
							"Success",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException exception) {
					JOptionPane.showMessageDialog(null,
							"Write I/O error",
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(b4);
		
		textfield1 = new JTextField("1000");
		panel.add(textfield1);

		label1 = new JLabel("Fitness value = N/A");
		panel.add(label1);

		this.add(panel, c);
	}

	private void load(String filepath) {
			try {
				Scanner sc = new Scanner(new FileInputStream(filepath));
				CourseData = new LinkedList<>();
				if (sc.hasNextLine())
					this.popularitySize = Integer.parseInt(sc.nextLine());
				while(sc.hasNextLine())
					CourseData.add(sc.nextLine());
				sc.close();
				curriculumFactory = new CurriculumFactory(CourseData.size(), CourseData);
				popularity = new Popularity();
				while (popularity.size()<popularitySize)
					popularity.add(curriculumFactory.getCurriculum());
				generic = new Generic(popularity.size());
				popularity.printFitness();
				printtable();
				this.filepath = filepath;
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,
						"FileNotFoundException",
					    "Error",
					    JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
	}

	private void run() {
		for (int i=1;i<=round;i++) {
			popularity = generic.nextGeneration(popularity);
			System.out.println("[GUI] the best fitness value in " + i + " generation: "
									+ getBest().getFitnessValue());
			if (getBest().getFitnessValue()>0) 
				break;
		}
		printtable();
	}

	private void printtable() {
		String[][][] data = {
			    {
			    	{"第一節", "", "", "", "", ""},
			    	{"第二節", "", "", "", "", ""},
			    	{"第三節", "", "", "", "", ""},
			    	{"第四節", "", "", "", "", ""},
			    	{"第五節", "", "", "", "", ""},
			    	{"第六節", "", "", "", "", ""},
			    	{"第七節", "", "", "", "", ""},
			    	{"第八節", "", "", "", "", ""},
			    	{"第九節", "", "", "", "", ""},
			    	{"第十節", "", "", "", "", ""},
			    	{"第十一節", "", "", "", "", ""}
			    },
			    {
			    	{"第一節", "", "", "", "", ""},
			    	{"第二節", "", "", "", "", ""},
			    	{"第三節", "", "", "", "", ""},
			    	{"第四節", "", "", "", "", ""},
			    	{"第五節", "", "", "", "", ""},
			    	{"第六節", "", "", "", "", ""},
			    	{"第七節", "", "", "", "", ""},
			    	{"第八節", "", "", "", "", ""},
			    	{"第九節", "", "", "", "", ""},
			    	{"第十節", "", "", "", "", ""},
			    	{"第十一節", "", "", "", "", ""}
			    },
			    {
			    	{"第一節", "", "", "", "", ""},
			    	{"第二節", "", "", "", "", ""},
			    	{"第三節", "", "", "", "", ""},
			    	{"第四節", "", "", "", "", ""},
			    	{"第五節", "", "", "", "", ""},
			    	{"第六節", "", "", "", "", ""},
			    	{"第七節", "", "", "", "", ""},
			    	{"第八節", "", "", "", "", ""},
			    	{"第九節", "", "", "", "", ""},
			    	{"第十節", "", "", "", "", ""},
			    	{"第十一節", "", "", "", "", ""}
			    },
			    {
			    	{"第一節", "", "", "", "", ""},
			    	{"第二節", "", "", "", "", ""},
			    	{"第三節", "", "", "", "", ""},
			    	{"第四節", "", "", "", "", ""},
			    	{"第五節", "", "", "", "", ""},
			    	{"第六節", "", "", "", "", ""},
			    	{"第七節", "", "", "", "", ""},
			    	{"第八節", "", "", "", "", ""},
			    	{"第九節", "", "", "", "", ""},
			    	{"第十節", "", "", "", "", ""},
			    	{"第十一節", "", "", "", "", ""}
			    },
			    {
			    	{"第一節", "", "", "", "", ""},
			    	{"第二節", "", "", "", "", ""},
			    	{"第三節", "", "", "", "", ""},
			    	{"第四節", "", "", "", "", ""},
			    	{"第五節", "", "", "", "", ""},
			    	{"第六節", "", "", "", "", ""},
			    	{"第七節", "", "", "", "", ""},
			    	{"第八節", "", "", "", "", ""},
			    	{"第九節", "", "", "", "", ""},
			    	{"第十節", "", "", "", "", ""},
			    	{"第十一節", "", "", "", "", ""}
			    },
			};
		Curriculum c = getBest();
		for(int i = 0; i < CourseData.size(); i++) {
			Course course = c.getCourse(i);
			String[] s = CourseData.get(i).split(" ");
			for(int j = course.start; j <= course.end; j++) {
				//System.out.println(s[1] + ": course.grade=" + course.grade + "/course.week=" + course.week + "/j = " + j);
				if(data[course.grade - 1][j - 1][course.week] != "")
					if(!data[course.grade - 1][j - 1][course.week].contains("(多堂)")) 
						data[course.grade - 1][j - 1][course.week] = "(多堂)\n" + data[course.grade - 1][j - 1][course.week] + "&" + (course.isMajor() ? "(必)" : "(選)") + s[1] + (course.isFixed() ? "" : "[" + s[5] + "]");
					else
						data[course.grade - 1][j - 1][course.week] += "&" + (course.isFixed() ? "(必)" : "(選)") + s[1] + (course.isFixed() ? "" : "[" + s[5] + "]");
				else
					data[course.grade - 1][j - 1][course.week] = (course.isMajor() ? "(必)" : "(選)") + s[1] + (course.isFixed() ? "" : "[" + s[5] + "]");
			}
		}
		tabbedPane.removeAll();
		
		build_table1(data[0]);
		build_table2(data[1]);
		build_table3(data[2]);
		build_table4(data[3]);
		build_table5(data[4]);

		label1.setText("Fitness value = " + c.getFitnessValue()); 
	}

	Curriculum getBest() {
		return popularity.getBest();
	}

	public static void main(String[] args) {
		new GUI();
	}
}
