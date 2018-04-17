public class ArrayQueueModule {
	
	// inv: size >= 0 && for i = 0 to size - 1 : elements[(start + i) % elements.length] != null

	static int size = 0;
	static Object[] elements = new Object[0];
	static int start = 0, end = 0;
	
	private static int inc(int x) {
		x++;
		if(x == elements.length) {
			x = 0;
		}
		return x;
	}

	private static int dec(int x) {
		x--;
		if(x < 0) x += elements.length;
		return x;
	}
	
	public static Object[] toArray() {
        Object[] ans = new Object[size];
        System.arraycopy(elements, start, ans, 0, elements.length - start);
        System.arraycopy(elements, 0, ans, elements.length - start, end);
        return ans;
    }
	
	//	pre: element != null
	//  post: (start - 1 < 0) ? start = elements.length - 1 : start = start - 1
	//		  elements[start] = element 
	//		  size = size + 1

	public static void push(Object element) {
		assert element != null;
		ensureCapacity(size + 1);
		start = dec(start);
		elements[start] = element;
		size++; 
	}
	
	// pre: size > 0
	// post: R = elements[(end - 1 < 0) ? elements.length - 1 : end - 1] && elements = elements'
	
	public static Object peek() {
		if(end == 0) {
			return elements[elements.length - 1];
		} else {
			return elements[end - 1];
		}
	}

	// pre: size > 0;
    // post: (end - 1 < 0) ? end = elements.length - 1 : end = end - 1,
    //      R = elements[end] 

	public static Object remove() {
		Object result = peek();
		size--;
		end = dec(end);
		return result;
	}

	//pre: element != null
    //post: elements[end] = element,
    //      end = (end + 1) % elements.length,
    //      size = size + 1 

	public static void enqueue(Object element) {
		assert element != null;
		ensureCapacity(size + 1);
		elements[end] = element;
		size++;
		end = inc(end);
	}

	private static void ensureCapacity(int capacity) {
		if(capacity <= elements.length) {
			return;
		}
		Object[] newElements = new Object[2 * capacity];
		for(int i = 0 ; i < elements.length ; ++i) {
			newElements[i] = elements[start];
			start = inc(start);
		}
		elements = newElements;
		start = 0;
		end = capacity - 1;
	}

	// pre: size > 0
	// post: R = elements[start] && elements = elements'

	public static Object element() {
		assert size > 0;
		return elements[start];
	}

	//pre: size > 0
    //post: R = elements[start],
    //      start = (start + 1) % elements.length,
    //      size = size - 1 

	public static Object dequeue() {
		assert size > 0;
		Object result = element();
		size--;
		start = inc(start);
		return result;
	}

	// post: R = size && size == size' && elements == elements'

	public static int size() {
		return size;
	}

	// post: R = (size == 0) && size == size' && elements == elements'  

	public static boolean isEmpty() {
		return size == 0;
	}

	// post: start = 0 && end = 0 && size = 0 && elements.length = 0

	public static void clear() {
		start = 0;
		end = 0;
		size = 0;
		elements = new Object[0];
	}
}