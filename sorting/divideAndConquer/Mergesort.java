package sorting.divideAndConquer;

import sorting.SortingImpl;

public class Mergesort<T extends Comparable<T>> extends SortingImpl<T> {

	@Override
	protected void sort(T[] array, int leftIndex, int rightIndex) {
		mergesort(array, leftIndex, rightIndex);

	}

	protected void mergesort(T array[], int leftIndex, int rightIndex) {

		if (leftIndex < rightIndex) {

			int media = (leftIndex + rightIndex) / 2;

			mergesort(array, leftIndex, media);

			mergesort(array, media + 1, rightIndex);

			merge(array, leftIndex, media, rightIndex);
		}
	}

	@SuppressWarnings("unchecked")
	private void merge(T[] array, int leftIndex, int media, int rightIndex) {
		// vector auxiliar
		T[] arrayAux = (T[]) new Comparable[array.length];
		
		for (int i = leftIndex; i <= media; i++) {
			arrayAux[i] = array[i];
		}

		for (int j = media + 1; j <= rightIndex; j++) {
			arrayAux[rightIndex + media + 1 - j] = array[j];
		}

		int i = leftIndex;
		int j = rightIndex;

		for (int k = leftIndex; k <= rightIndex; k++) {
			if (arrayAux[i].compareTo(arrayAux[j]) <= 0) {
				array[k] = arrayAux[i];
				i++;
			} else {
				array[k] = arrayAux[j];
				j--;
			}
		}
	}
}
