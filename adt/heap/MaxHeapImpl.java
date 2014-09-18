package adt.heap;

import java.util.Arrays;

import sorting.Util;

public class MaxHeapImpl<T extends Comparable<T>> implements MaxHeap<T> {

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;
	private int size;
	private T[] heap;

	@SuppressWarnings("unchecked")
	public MaxHeapImpl() {
		heap = (T[]) new Comparable[INITIAL_SIZE];
		size = 0;
	}
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void insert(T element) {
		//verifica se a heap já está cheia
		if (size >= heap.length) {
			heap = Arrays.copyOf(heap, heap.length+INCREASING_FACTOR);
		}
		
		else  {
			int i = size;	
			//realoca o elemento caso ele seja menor que o elemento que se deseja inserir
			//OUTRA FORMA
			//while (i > 0 && element.compareTo(heap[parent(i)] < 0)
			while (i > 0 && heap[parent(i)].compareTo(element) < 0) {
				heap[i] = heap[parent(i)];
				i = parent(i);
			}
			heap[i] = element;
			size++;
		}
	}
	
	private int parent(int i) {
		return (i-1) /2;
	}
	
	private int left(int i) {
		return 2*i + 1;
	}
	
	private int right(int i) {
		return 2*i + 2;
	}

	@Override
	public T extractRootElement() {
		if (this.rootElement() == null) {
			return null;
		}
		
		T root = this.rootElement();
		Util.swap(heap, 0, size-1);
		size--;
		heapify(0);
		
		return root;
	}
	
	@Override
	public T rootElement() {
		if (this.isEmpty()) {
			return null;
		}
		return heap[0];
	}

	private void heapify(int position) {
		int left = left(position);
		int right = right(position);
		int max = position;
		
		if (left <= size-1 && heap[left].compareTo(heap[position]) > 0) {
			max = left;
		}
		if (right <= size-1 && heap[right].compareTo(heap[max]) > 0) {
			max = right;
		}
		
		//faz a troca
		if (max != position) {
			Util.swap(heap, position, max);
			heapify(max);
		}
	}

	@Override
	public T[] heapsort(T[] array) {
		T[] copy = array.clone();
		this.buildHeap(copy);
		int i = size-1;
		while (!this.isEmpty()) {
			copy[i--] = this.extractRootElement();
		}
		return copy;
	}

	@Override
	public void buildHeap(T[] array) {
		heap = array.clone();
		size = heap.length;
		for (int i = (size-1)/2; i >= 0; i--) {
			heapify(i);
		}
	}

	@Override
	public T[] toArray() {
		return heap.clone();
	}
}