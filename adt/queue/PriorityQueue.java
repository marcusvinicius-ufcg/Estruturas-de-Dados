package adt.queue;

import java.util.Comparator;

public class PriorityQueue<T> implements Queue<T> {

	private T[] array;
	private int tail;
	private Comparator<T> comparator;

	@SuppressWarnings("unchecked")
	public PriorityQueue(int size, Comparator<T> comparator) {
		array = (T[]) new Object[size];
		tail = -1;
		this.comparator = comparator;
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull()) {
			throw new QueueOverflowException();
		}

		if (element == null) {
			return;
		}
		array[++tail] = element;

	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException();
		}
		
		selectionSort(array);
		T result = array[0];

		shiftLeft();

		tail--;

		return result;
	}

	@Override
	public T head() {
		selectionSort(array);
		return !isEmpty() ? array[0] : null;
	}

	@Override
	public boolean isEmpty() {
		return tail == -1;
	}

	@Override
	public boolean isFull() {
		return tail == array.length - 1;
	}

	private void shiftLeft() {
		for (int i = 0; i < tail; i++) {
			array[i] = array[i + 1];
		}
	}

	public void selectionSort(T[] array) {
		T temp;
		Integer i, j, first;
		for (i=array.length-1; i > 0; i--) {
			first = 0;
			for (j = 1; j <= i; j++){
				if (comparator.compare(array[j], array[first]) > 0){
					first = j;
				}
			}
			temp = array[first];
			array[first] = array[i];
			array[i] = temp;
		}
	}
}
