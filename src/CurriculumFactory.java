import java.util.LinkedList;

public class CurriculumFactory {
	CourseFactory coursefactory = new CourseFactory();
	int size;
	LinkedList<String> infos;

	CurriculumFactory(int size, LinkedList<String> infos){
		this.size = size;
		this.infos = infos;
	}

	Curriculum getCurriculum(){
		Curriculum curriculum = new Curriculum();
		for(int i = 0; i < size; i++) {
			Course tmp = coursefactory.getCourse(infos.get(i));
			// avoid course tmp overlapping with fixed courses
			while (curriculum.isConflict(tmp))
				tmp = coursefactory.getCourse(infos.get(i));
			curriculum.add(tmp);
		}
		return curriculum;
	}
}
