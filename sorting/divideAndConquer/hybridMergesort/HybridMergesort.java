package sorting.divideAndConquer.hybridMergesort;

import sorting.SortingImpl;

/**
 * The algorithm is an hybrid of mergesort and insertion sort. Depending on the
 * size of the input, the algorithm decides between the application of mergesort
 * or insertion sort. The implementation of the algorithm must be in-place!
 */
public class HybridMergesort<T extends Comparable<T>> extends SortingImpl<T> {
	/**
	 * The limit to choose between applying merge or insertion. For inputs with
	 * size less or equal to 4, apply insertion sort.
	 */
	public static final int SIZE_LIMIT = 4;

	protected static int MERGESORT_APPLICATIONS = 0;
	protected static int INSERTIONSORT_APPLICATIONS = 0;

	protected void sort(T[] array, int leftIndex, int rightIndex) {
		hybridMergesort(array, leftIndex, rightIndex);
	}

	protected void hybridMergesort(T array[], int leftIndex, int rightIndex) {

		MERGESORT_APPLICATIONS++;

		if (leftIndex < rightIndex) {

			int indiceMedio = (leftIndex + rightIndex) / 2;

			if (array.length > SIZE_LIMIT) {

				hybridMergesort(array, leftIndex, indiceMedio);

				hybridMergesort(array, indiceMedio + 1, rightIndex);

				merge(array, leftIndex, indiceMedio, rightIndex);

			} else {
				insertionSort(array, leftIndex, indiceMedio);

				insertionSort(array, indiceMedio + 1, rightIndex);

				merge(array, leftIndex, indiceMedio, rightIndex);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void merge(T[] array, int leftIndex, int indiceMedio, int rightIndex) {
		// vector auxiliar
		T[] arrayAuxiliar = (T[]) new Comparable[array.length];

		for (int i = leftIndex; i <= indiceMedio; i++) {
			arrayAuxiliar[i] = array[i];
		}

		for (int j = indiceMedio + 1; j <= rightIndex; j++) {
			arrayAuxiliar[rightIndex + indiceMedio + 1 - j] = array[j];
		}

		int i = leftIndex;
		int j = rightIndex;

		for (int k = leftIndex; k <= rightIndex; k++) {
			if (arrayAuxiliar[i].compareTo(arrayAuxiliar[j]) <= 0) {
				array[k] = arrayAuxiliar[i];
				i++;
			} else {
				array[k] = arrayAuxiliar[j];
				j--;
			}
		}
	}

	protected void insertionSort(T[] array, int leftIndex, int rightIndex) {

		INSERTIONSORT_APPLICATIONS++;

		int n = (rightIndex - leftIndex) + 1;

		for (int j = leftIndex + 1; j < n; j++) {

			T chave = array[j];

			int i = j - 1;

			while (i >= 0 && (array[i].compareTo(chave) > 0)) {
				array[i + 1] = array[i];
				i--;
			}
			array[i + 1] = chave;
		}
	}

	public int getCallInsertionApplications() {
		return INSERTIONSORT_APPLICATIONS;
	}

	public int getCallMergesortApplications() {
		return MERGESORT_APPLICATIONS;
	}
}
