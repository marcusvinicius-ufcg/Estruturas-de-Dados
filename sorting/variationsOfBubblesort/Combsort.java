package sorting.variationsOfBubblesort;

import sorting.SortingImpl;
import sorting.Util;

/**
 * The combsort algoritm.
 */
public class Combsort<T extends Comparable<T>> extends SortingImpl<T> {

	@Override
	protected void sort(T[] array, int leftIndex, int rightIndex) {

		int gap = array.length;

		boolean swaps = true;

		while (gap > 1 || swaps) {
			if (gap > 1){
				gap = (int) (gap / 1.25);
			}

			int index = leftIndex;
			swaps = false;
			
			while (index + gap < rightIndex) {
				
				if (array[index].compareTo(array[index + gap]) > 0) {
					
					Util.swap(array, index, index+gap);
					swaps = true;
				}
				index++;	
			}
		}
	}
}
