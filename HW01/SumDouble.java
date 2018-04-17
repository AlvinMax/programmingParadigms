public class SumDouble {
	public static void main(String[] args) {            	
		double sum = 0;
		for (int i = 0 ; i < args.length ; ++i) {
			for (String s : args[i].split("\\p{javaWhitespace}")) {
				if (s.length() > 0) {
					sum += Double.valueOf(s);
				}
			}
		}
		System.out.println(sum);
	}
}