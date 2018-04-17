import java.util.function.Function;
import java.util.function.Predicate; 

public abstract class AbstractQueue implements Queue {
	protected int size = 0;

	public void enqueue(Object element) {
		assert element != null;
		enqueueImpl(element);
		size++;
	}

	protected abstract void enqueueImpl(Object element);

	public Object element() {
		assert size > 0;
		return elementImpl();
	}
	
	protected abstract Object elementImpl();

	public Object dequeue() {
		assert size > 0;
		Object result = element();
        size--;
        remove();	
        return result;
	}

	protected abstract void remove();

	public int size() {
		return size;
	}

    public boolean isEmpty() {
        return size == 0;
    }

    protected abstract Queue emptyClone();
    protected abstract Object[] toArray();
 
    public Queue filter(Predicate<Object> predicate) {
        Queue q = this.emptyClone();
        Object[] a = this.toArray();
 
        for (int i = 0; i < size; i++) {
            if (predicate.test(a[i])) {
                q.enqueue(a[i]);
            }
        }
        return q;
    }
 
    public Queue map(Function<Object, Object> function) {
        Queue q = this.emptyClone();
        Object[] a = this.toArray();
 
        for (int i = 0; i < size; i++) {
            q.enqueue(function.apply(a[i]));
        }
        return q;
    }

}