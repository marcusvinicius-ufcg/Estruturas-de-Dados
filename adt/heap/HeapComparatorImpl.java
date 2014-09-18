package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Essa comparação não é feita diretamente com os elementos armazenados,
 * mas sim usando um comparator. Dessa forma, dependendo do comparator, a heap
 * pode funcionar como uma max-heap ou min-heap.
 */
public class HeapComparatorImpl<T extends Comparable<T>> implements
		GenericHeap<T> {

	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é
	 * mudar apenas o comparator e mandar reordenar a heap usando esse
	 * comparator. Assim os metodos da heap não precisam saber se vai funcionar
	 * como max-heap ou min-heap.
	 */
	protected Comparator<T> comparator;
	private T[] array;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma
	 * min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapComparatorImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento
	 * indexado pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento
	 * indexado pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = (T[]) new Comparable[index + 1];
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode
	 * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		Integer less = position;
		Integer l = left(position);
		Integer r = right(position);

		if ((r < index) && (comparator.compare(heap[r], heap[less])) < 0) {
			less = r;
		}

		if ((l < index) && (comparator.compare(heap[l], heap[position])) < 0) {
			less = l;
		}

		if (less != position) {
			Util.swap(heap, position, less);
			heapify(less);
		}
	}

	@Override
	public void insert(T element) {
		// ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}
		// /////////////////////////////////////////////////////////////////

		Integer auxIndex = index;

		while ((auxIndex > 0)
				&& (comparator.compare(heap[parent(auxIndex)], element) > 0)) {
			heap[auxIndex] = heap[parent(auxIndex)];
			auxIndex = parent(auxIndex);
		}

		heap[auxIndex] = element;
		index++;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buildHeap(T[] array) {
		index = array.length - 1;
		heap = (T[]) (new Comparable[array.length]);

		for (int i = 0; i < array.length; i++) {
			heap[i] = array[i];
		}

		for (int i = 0; i < array.length / 2; i++) {
			heapify(i);
		}

	}

	@Override
	public T extractRootElement() {
		if (isEmpty()) {
			return null;
		}
		T element = array[0];
		array[0] = array[index - 1];
		array[index - 1] = null;
		heapify(0);
		index--;
		return element;
	}

	@Override
	public T rootElement() {
		return isEmpty() ? null : array[0];
	}

	@Override
	public T[] heapsort(T[] array) {
		T[] auxArray = this.array;
		this.array = array;
		index = 0;
		buildHeap(array);

		Integer i = index;
		for (int j = index - 1; j > 0; j--) {
			Util.swap(this.array, 0, j);
			index = index - 1;
			heapify(0);
		}
		index = i;
		T[] result = toArray();
		this.array = auxArray;

		return result;
	}
}
