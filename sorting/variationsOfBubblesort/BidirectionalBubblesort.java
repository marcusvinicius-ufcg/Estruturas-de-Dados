package sorting.variationsOfBubblesort;

import sorting.SortingImpl;
import sorting.Util;

/**
 * This bubblesort variation pushes the greatest element to the right and
 * (walking from left to right), after that, it pushes the smallest element to
 * the left (walking from right to left). This happens in a same iteration. The
 * following iterations do the same until the array is completely ordered. The
 * algorithm must sort the elements from leftIndex to rightIndex (inclusive).
 */
public class BidirectionalBubblesort<T extends Comparable<T>> extends
		SortingImpl<T> {

	@Override
	protected void sort(T[] array, int leftIndex, int rightIndex) {

		int size = (rightIndex - leftIndex) / 2;

		for (int i = 0; i < size; i++) {

			int maxIndex = max(array, leftIndex, rightIndex);

			while (maxIndex < rightIndex) {
				Util.swap(array, maxIndex, maxIndex + 1);
				maxIndex++;
			}

			rightIndex--;

			int minIndex = min(array, leftIndex, rightIndex);

			while (minIndex > leftIndex) {
				Util.swap(array, minIndex, minIndex - 1);
				minIndex--;
			}

			
			leftIndex++;
		}
	}

	private int min(T[] array, int leftIndex, int rightIndex) {
		int minIndex = leftIndex;

		for (int i = leftIndex + 1; i <= rightIndex; i++) {
			
			if (array[i].compareTo(array[minIndex]) < 0) {
				minIndex = i;
			}
		}

		return minIndex;
	}

	private int max(T[] array, int leftIndex, int rightIndex) {

		int maxIndex = leftIndex;

		for (int i = leftIndex + 1; i <= rightIndex; i++) {

			if (array[i].compareTo(array[maxIndex]) > 0) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

}