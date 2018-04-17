import java.util.ArrayList;
import java.util.List;

public class SumLongHex {
	public static void main(String[] args) {            	
		long sum = 0;
		for (int i = 0 ; i < args.length ; ++i) {
			for (String s : split(args[i])) {
				int len = s.length();
                if (len >= 3 && s.toLowerCase().startsWith("0x")) {
                    sum += Long.parseUnsignedLong(s.substring(2, len), 16);
                } else {
                    sum += Long.valueOf(s);
                }
			}
		}
		System.out.println(sum);
	}

	public static List<String> split(String s) {
    	List<String> list = new ArrayList<>();
		int left = 0;
	   	for (int i = 0 ; i < s.length() ; ++i) {
	        if (Character.isWhitespace(s.charAt(i))) {
	            if (left < i) {
	                list.add(s.substring(left, i));
                }
                left = i + 1;
            }
	    }
        if (left < s.length()) {
            list.add(s.substring(left, s.length()));
        }
		return list;
    }
}                                                                       
