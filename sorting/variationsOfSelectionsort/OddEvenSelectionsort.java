package sorting.variationsOfSelectionsort;

import sorting.SortingImpl;
import sorting.Util;

public class OddEvenSelectionsort<T extends Comparable<T>> extends
		SortingImpl<T> {

	@Override
	protected void sort(T[] array, int leftIndex, int rightIndex) {

		// divisao logica do array bubblesort iterando os pares
		for (int i = leftIndex; i <= rightIndex; i++) {
			for (int j = leftIndex; j <= rightIndex-2; j++) {
				// index par
				if (j % 2 == 0) {
					// pegando o proximo par
					if (array[j].compareTo(array[j+2]) == 1) {
						Util.swap(array, j, j+2);
					}
				}
			}
		}

		// divisao logica do array bubblesort iterando os impares
		for (int i = leftIndex; i <= rightIndex; i++) {

			for (int j = leftIndex; j <= rightIndex-2; j++) {
				// index impar
				if (!(j % 2 == 0)) {
					// pegando o proximo impar
					if (array[j].compareTo(array[j+2]) == 1) {
						Util.swap(array, j, j+2);
					}
				}
			}
		}

		// selection sort pares
		for (int i = leftIndex; i <= rightIndex; i++) {

			int menor = i;
			// elemento i com i+1
			for (int j = i + 1; j <= rightIndex; j++) {
				// index par
				if (j % 2 == 0) {
					if (array[j].compareTo(array[menor]) == -1) {
						menor = j;
					}
				}
			}
			Util.swap(array, i, menor);
		}

		// selection sort impares
		for (int i = leftIndex; i <= rightIndex; i++) {

			int menor = i;
			// elementos i com i+1
			for (int j = i + 1; j <= rightIndex; j++) {
				// index impar
				if (!(j % 2 == 0)) {
					if (array[j].compareTo(array[menor]) == -1) {
						menor = j;
					}
				}
			}
			Util.swap(array, i, menor);
		}
	}
}
