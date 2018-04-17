public class ArrayQueueADT {

	// inv: queue != null && queue.size >= 0 && for i = 0 to queue.size - 1 : queue.elements[i] != null

	private int size;
	private Object[] elements = new Object[0];
	private int start, end;
	
	private static int inc(ArrayQueueADT queue, int x) {
		x++;
		if(x == queue.elements.length) {
			x = 0;
		}
		return x;
	}

	private static int dec(ArrayQueueADT queue, int x) {
		x--;
		if(x < 0) x += queue.elements.length;
		return x;
	}


	//	pre: element != null
	//  post: (queue.start - 1 < 0) ? queue.start = queue.elements.length - 1 : queue.start = queue.start - 1
	//		  queue.elements[queue.start] = element 
	//		  queue.size = queue.size + 1
	public static Object[] toArray(ArrayQueueADT queue) {
		Object[] ans = new Object[queue.size];
		int j = queue.start;
		for (int i = 0; i < queue.size; i++) {
			ans[i] = queue.elements[j];
			j = inc(queue, j);
		}
		return ans;
	}

	public static void push(ArrayQueueADT queue, Object element) {
		assert element != null;
		ensureCapacity(queue, queue.size + 1);
		queue.start = dec(queue, queue.start);
		queue.elements[queue.start] = element;
		queue.size++; 
	}

	// pre: queue.size > 0
	// post: R = queue.elements[(queue.end - 1 < 0) ? queue.elements.length - 1 : queue.end - 1] && queue.elements = queue.elements'

	public static Object peek(ArrayQueueADT queue) {
		if(queue.end == 0) {
			return queue.elements[queue.elements.length - 1];
		} else {
			return queue.elements[queue.end - 1];
		}
	}

	// pre: queue.size > 0;
    // post: (queue.end - 1 < 0) ? queue.end = queue.elements.length - 1 : queue.end = queue.end - 1,
    //      R = queue.elements[queue.end] 

	public static Object remove(ArrayQueueADT queue) {
		Object result = peek(queue);
		queue.size--;
		queue.end = dec(queue, queue.end);
		return result;
	}
	
    //pre: element != null
    //post: queue.elements[queue.end] = element,
    //      queue.end = (queue.end + 1) % queue.elements.length,
    //      queue.size = queue.size + 1 
	
	public static void enqueue(ArrayQueueADT queue, Object element) {
		assert element != null;
		ensureCapacity(queue, queue.size + 1);
		queue.elements[queue.end] = element;
		queue.size++;
		queue.end = inc(queue, queue.end);
	}

	private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
		if(capacity <= queue.elements.length) {
			return;
		}
		Object[] newElements = new Object[2 * capacity];
		for(int i = 0 ; i < queue.elements.length ; ++i) {
			newElements[i] = queue.elements[queue.start];
			queue.start = inc(queue, queue.start);
		}
		queue.elements = newElements;
		queue.start = 0;
		queue.end = capacity - 1;
	}

	// pre: queue.size > 0
	// post: R = queue.elements[queue.start] && queue.elements = queue.elements'

	public static Object element(ArrayQueueADT queue) {
		assert queue.size > 0;
		return queue.elements[queue.start];
	}

	//pre: queue.size > 0
    //post: R = queue.elements[queue.start],
    //      queue.start = (queue.start + 1) % queue.elements.length,
    //      queue.size = queue.size - 1 

	public static Object dequeue(ArrayQueueADT queue) {
		assert queue.size > 0;
		Object result = queue.elements[queue.start];
		queue.size--;
		queue.start = inc(queue, queue.start);
		return result;
	}

	// post: R = queue.size && queue.size == queue.size' && queue.elements == queue.elements'

	public static int size(ArrayQueueADT queue) {
		return queue.size;
	}

	// post: R = (queue.size == 0) && queue.size == queue.size' && queue.elements == queue.elements'  

	public static boolean isEmpty(ArrayQueueADT queue) {
		return queue.size == 0;
	}

	// post: queue.start = 0 && queue.end = 0 && queue.size = 0 && queue.elements.length = 0

	public static void clear(ArrayQueueADT queue) {
		queue.start = 0;
		queue.end = 0;
		queue.size = 0;
		queue.elements = new Object[0];
	}
}