import java.util.Arrays;

public class ArrayQueue extends AbstractQueue {

	private Object[] elements = new Object[0];
	private int start, end;
	
	private int inc(int x) {
		x++;
		if(x == elements.length) {
			x = 0;
		}
		return x;
	}

	private int dec(int x) {
		x--;
		if(x < 0) x += elements.length;
		return x;
	}

	protected void enqueueImpl(Object element) {
		ensureCapacity(size + 1);
		elements[end] = element;
		end = inc(end);
    }

	private void ensureCapacity(int capacity) {
		if(capacity <= elements.length) {
			return;
		}
		Object[] newElements = new Object[2 * capacity];
		System.arraycopy(elements, start, newElements, 0, elements.length - start);
		System.arraycopy(elements, 0, newElements, elements.length - start, end);
		elements = newElements;
		start = 0;
		end = capacity - 1;
	}

	protected  Object elementImpl() {
		return elements[start];
	}

	protected void remove() {
		start = inc(start);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}
	
	public void clear() {
		start = 0;
		end = 0;
		size = 0;
		elements = new Object[0];
	}

    protected Queue emptyClone() {
    	return new ArrayQueue();
    }

    protected Object[] toArray() {
    	Object[] res = new Object[size];
    	int k = 0;
    	int i = start;
    	for(int it = 0 ; it < size ; ++it) {
    		res[k++] = elements[i];
    		i = inc(i);
    	}
    	return res;	
    }

}