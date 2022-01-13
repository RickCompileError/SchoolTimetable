import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GenericDemo {
	String filepath;
	LinkedList<String> CourseData = new LinkedList<>();
	Popularity popularity = new Popularity();
	int popularitySize;
	Generic generic;
	CurriculumFactory curriculumFactory;

	GenericDemo(String filepath){
		try {
			Scanner sc = new Scanner(new FileInputStream(filepath));
			popularity = new Popularity();
			if (sc.hasNextLine())
				this.popularitySize = Integer.parseInt(sc.nextLine());
			while(sc.hasNextLine())
				CourseData.add(sc.nextLine());
			curriculumFactory = new CurriculumFactory(CourseData.size(), CourseData);
			while (popularity.size()<popularitySize)
				popularity.add(curriculumFactory.getCurriculum());
			generic = new Generic(popularity.size());
			popularity.printFitness();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"FileNotFoundException",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	void run() {
		for (int i=1;i<=100;i++) {
			popularity = generic.nextGeneration(popularity);
			System.out.println("[GenericDemo] the best fitness value in " + i + " generation: "
									+ getBest().getFitnessValue());
			if (getBest().getFitnessValue()>0) 
				break;
		}
	}
	
	Curriculum getBest() {
		return popularity.getBest();
	}

	public static void main(String[] args) {
		JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			GenericDemo gd = new GenericDemo(fc.getSelectedFile().getAbsolutePath());
			gd.run();
			gd.getBest();
		}
		else {
			JOptionPane.showMessageDialog(null,
					"No input file",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}
