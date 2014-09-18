package sorting.linearSorting;

import sorting.SortingImpl;

public class Countingsort extends SortingImpl<Integer> {

	private final int k = 100;

	@Override
	protected void sort(Integer[] array, int leftIndex, int rightIndex) {

		if (rightIndex <= leftIndex) {
			return;
		}
		int n = array.length;

		int[] C = new int[k + 1];
		int[] B = new int[n];

		for (int i = leftIndex; i <= rightIndex; i++) {
			C[array[i]]++;
		}

		int total = 0;
		for (int i = 0; i <= k; i++) {
			int count = C[i];
			C[i] = total;
			total += count;
		}

		for (int i = leftIndex; i <= rightIndex; i++) {
			B[C[array[i]]] = array[i];
			C[array[i]]++;
		}

		for (int i = leftIndex; i <= rightIndex; i++) {
			array[i] = B[i];
		}
	}
}
