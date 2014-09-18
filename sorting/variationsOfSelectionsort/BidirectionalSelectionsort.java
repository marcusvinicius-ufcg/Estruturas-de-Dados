package sorting.variationsOfSelectionsort;

import sorting.SortingImpl;
import sorting.Util;

/**
 * This selectionsort variation puts the greatest element into the right
 * position (walking from left to right) and, after that, it puts the smallest
 * element into the left position (walking from right to left). This happens in
 * a same iteration. The following iterations do the same until the array is
 * completely ordered.The algorithm must sort the elements from leftIndex to
 * rightIndex (inclusive).
 */
public class BidirectionalSelectionsort<T extends Comparable<T>> extends
		SortingImpl<T> {

	@Override
	protected void sort(T[] array, int leftIndex, int rightIndex) {

		for (int left = leftIndex, right = rightIndex; left < right; left++, right--) {

			int minimo = left;
			for (int i = left + 1; i <= right; i++) {
				if (array[i].compareTo(array[minimo]) < 0) {
					minimo = i;
				}
			}
			Util.swap(array, left, minimo);

			int maximo = right;
			for (int j = right - 1; j >= left; j--) {
				if (array[j].compareTo(array[maximo]) > 0) {
					maximo = j;
				}
			}
			Util.swap(array, right, maximo);
		}
	}
}