import java.util.function.Function;
import java.util.function.Predicate; 

public interface Queue {
	// n, e[1]..e[n]
 	// inv: n >= 0 && e[i] != null

	// pre: element != null
	// post: n' == n + 1 && e'[n + 1] == x && e'[1..n] == e[1..n]
	void enqueue(Object element);
	
	// pre: n > 0
	// post: n' == n && e' == e && R == e[1] 
	Object element();
	
	// pre: n > 0
	// post: n' == n - 1 && e'[1..n'] == e[2..n] && R == e[1]
	Object dequeue();
	
	// post: n' == n && e' == e && R == n
	int size();
	
	// post: n' == n && e' == e && R == (n == 0)
	boolean isEmpty();
	
	// post: n' == 0 && e' == []	
	void clear();

	// post: n == cnt(p(e[i]) == true) && p(R[i]) == true &&
 	//    	 Exist 1 <= j[1] < j[2] < ... < j[n] <= n': R[i] == e[j[i]]
	Queue filter(Predicate<Object> predicate);
 	
 	// pre: for all x f(x) != null
 	// post: n == n' && e[i] == f(e'[i]) && R == e
    Queue map(Function<Object, Object> function);
}