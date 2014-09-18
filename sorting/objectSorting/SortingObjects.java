package sorting.objectSorting;

import java.util.Comparator;

public interface SortingObjects<T extends Comparable<T>> {

	public static enum Strategy {BUBBLESORT, MERGESORT}; 
	
	/**
	 * Sorts the the array of accounts by number in the simplest way, using one of the above strategies.
	 */
	public abstract void sort(T[] array, Strategy strategy);

	/**
	 * Sorts the the array of accounts using a given comparator and one of the above strategies.
	 */
	public abstract void sort(T[] array, Comparator<Account> comparator, Strategy strategy);

	/**
	 * Sorts the the array of accounts assuming that each stored element is Comparable.
	 */
	public abstract void sortComparables(T[] array, Strategy strategy);
	
}