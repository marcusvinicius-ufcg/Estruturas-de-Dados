package adt.heap;

public class MinHeapImpl<T extends Comparable<T>> implements MinHeap<T> {

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	private T[] heap;
	private int size;

	@SuppressWarnings("unchecked")
	public MinHeapImpl() {
		heap = (T[]) new Comparable[INITIAL_SIZE];
		size = 0;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void insert(T element) {
		size++;
		Integer i = size;

		while (i > 1 && getElement(parent(i)).compareTo(element) > 0) {
			setElement(i, getElement(parent(i)));
			i = parent(i);
		}
		setElement(i, element);
	}

	@Override
	public T extractRootElement() {
		if (isEmpty()){
			return null;
		}

		T min = getElement(1);
		heap[1] = getElement(size);
		size--;
		heapify(1);
		
		return min;
	}

	@Override
	public T rootElement() {
		return isEmpty() ? null : heap[1];
	}

	@Override
	public T[] heapsort(T[] array) {
		buildHeap(array);
		for (int i = array.length; i >= 2; i--) {
			Util.swap(heap, 1, i);
			size--;
			heapify(1);
		}
		size = array.length;

		return subArray(1, size + 1);
	}

	@Override
	public void buildHeap(T[] array) {
		Integer newSize = INITIAL_SIZE;

		while (newSize < array.length) {
			newSize += INCREASING_FACTOR;
		}

		heap = (T[]) new Comparable[newSize];
		size = array.length;

		System.arraycopy(array, 0, heap, 1, size);

		for (int i = (int) Math.floor(size / 2); i >= 1; i--) {
			heapify(i);
		}
	}

	private void heapify(int position) {
		Integer l = left(position);
		Integer r = right(position);

		Integer less = (l <= size && getElement(l).compareTo(getElement(position)) < 0) ? l : position;
		
		if (r <= size && getElement(r).compareTo(getElement(less)) < 0) {
			less = r;
		}

		if (less != position) {
			Util.swap(heap, position, less);
			heapify(less);
		}
	}

	@Override
	public T[] toArray() {
		return subArray(1, size + 1);
	}

	private T[] subArray(int begin, int end) {
		T[] sub = (T[]) new Comparable[end - begin];
		System.arraycopy(heap, 1, sub, 0, end - begin);
		
		return sub;
	}

	private T getElement(int index) {
		return heap[index];
	}

	private void setElement(int index, T key) {
		heap[index] = key;
	}

	private int parent(int i) {
		return isOcuppiedPosition(i) ? (int) Math.floor(i/2) : 0;
	}

	private int left(int i) {
		return isOcuppiedPosition(i) ? 2*i : 0;
	}

	private int right(int i) {
		return isOcuppiedPosition(i) ? 2*i+1 : 0;
	}

	private boolean isOcuppiedPosition(int position) {
		return position >= 1 && position <= size;
	}
}
