/**
 * 
 */
package sorting.divideAndConquer;

import sorting.SortingImpl;
import sorting.Util;

public class Quicksort<T extends Comparable<T>> extends SortingImpl<T> {

	
	@Override
	protected void sort(T[] array,int leftIndex, int rightIndex) {
		if (leftIndex >= rightIndex) return;

		int m = divisao(array, leftIndex, rightIndex);

		sort(array, leftIndex, m-1);
		sort(array, m+1, rightIndex);

	}
	
	private int divisao(T[] array, int leftIndex, int rightIndex) {
		int i = leftIndex+1;
		int j = rightIndex;

		T pivo = array[leftIndex];
		
		while(i <= j){
			
			if (array[i].compareTo(pivo) <= 0) i++;
			
			else if (array[j].compareTo(pivo) > 0 ) j--;
			
			else Util.swap(array,i,j);
		}

		Util.swap(array, leftIndex, j);

		return j;
	}
}