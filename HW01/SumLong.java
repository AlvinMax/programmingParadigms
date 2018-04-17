public class SumLong {
	public static void main(String[] args) {            	
		long sum = 0;
		for (int i = 0 ; i < args.length ; ++i) {
			for (String s : args[i].split("\\p{javaWhitespace}")) {
				if (s.length() > 0) {
					sum += Long.valueOf(s);
				}
			}
		}
		System.out.println(sum);
	}
}