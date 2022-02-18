import java.util.Random;

public class MyRandom {
	
	static Random rd = new Random();
	
	static int rangeInt(int end) {
		return rd.nextInt(end);
	}
	
	static int rangeInt(int start, int end) {
		return rd.nextInt(end-start+1) + start;
	}
	
	static int rangeInt(int start, int end, int[] prohibit, int offset) {
		while (true) {
			Boolean pass = true;
			int result = rd.nextInt(end-start+1) + start;
			for (int i: prohibit) {
				if (result<=i && i<=result+offset) pass = false;
			}
			if (pass) return result;
		}
	}
}
