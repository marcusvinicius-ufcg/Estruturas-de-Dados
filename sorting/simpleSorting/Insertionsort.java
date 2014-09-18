package sorting.simpleSorting;

import sorting.SortingImpl;
import sorting.Util;

/**
 * The insertion sort algorithm consumes the array (element by element) and
 * inserts the elements into an ordered list. The algorithm must sort the
 * elements from leftIndex to rightIndex (inclusive).
 */
public class Insertionsort<T extends Comparable<T>> extends SortingImpl<T> {

	@Override
	protected void sort(T[] array, int leftIndex, int rightIndex) {

		T key;
		
	
		
		for (int j = leftIndex; j <= rightIndex; j++) {
			
			key = array[j];	
			
			Integer i;
			for (i = j-1; i >= leftIndex && array[i].compareTo(key) == 1; i--) {
				Util.swap(array, i+1, i);
			}
			array[i + 1] = key;
		}
	}
}
