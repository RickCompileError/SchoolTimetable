import java.util.ArrayList;
import java.util.Random;

public class Generic {
	Random rd = new Random();
	Popularity popularity;
	int popularitySize;
	int bound;
	Curriculum c1;
	Curriculum c2;
	Curriculum offSpring;

	Generic(int popularitySize) {
		this.popularitySize = popularitySize;
		bound = (int)(popularitySize*(1/2.0));
	}
	
	Popularity nextGeneration(Popularity popularity) {
		Popularity generation = new Popularity();
		this.popularity = popularity;
		for (int i=0;i<popularitySize*(1/10.0);i++)
			generation.add(popularity.getCurriculum(i));
		while (generation.size()<popularitySize) {
			select();
			crossover();
			mutation();
			generation.add(offSpring);
		}
		return generation;
	}

	void select() {
		int val1, val2;
		do {
			val1 = rd.nextInt(bound);
			val2 = rd.nextInt(bound);
			//System.out.println("[Debug] val1 = " + val1 + " / val2 = " + val2);
		}while(val1 == val2);
		c1 = popularity.getCurriculum(val1);
		c2 = popularity.getCurriculum(val2);
	}

	void crossover(){
		int id = rd.nextInt(c1.size());
		ArrayList<Course> tmp = new ArrayList<>();
		tmp.addAll(c1.courses.subList(0, id));
		tmp.addAll(c2.courses.subList(id, c2.size()));
		offSpring = new Curriculum(tmp);
	}

	void mutation() {
		if(rd.nextInt(10) == 0) {
			int id = rd.nextInt(c1.size()), week = rd.nextInt(5) + 1, starttime = rd.nextInt(8) + 1;
			offSpring.modify(id, week, starttime);
		}
	}
}
