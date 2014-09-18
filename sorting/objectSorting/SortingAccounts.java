package sorting.objectSorting;

import java.util.Comparator;

public class SortingAccounts implements SortingObjects<Account> {

	/**
	 * Sorts the the array of accounts by number in the simplest way, using one
	 * of the above strategies. You must consider that the accounts will be
	 * ordered by number. You must get this number directly from each account
	 * using getNumber().
	 */
	@Override
	public void sort(Account[] array, Strategy strategy) {
		Comparator<Account> comparaPorNumero = new Comparator<Account>() {

			@Override
			public int compare(Account o1, Account o2) {
				return o1.getNumber().compareTo(o2.getNumber());
			}
		};

		if (strategy == Strategy.BUBBLESORT) {
			bubblesort(array, comparaPorNumero);
		}

		if (strategy == Strategy.MERGESORT) {
			mergesort(array, 0, array.length - 1, comparaPorNumero);
		}
	}

	/**
	 * Sorts the the array of accounts using a given comparator and one of the
	 * above strategies. You must use an external Comparator to determine the
	 * order between two accounts. You have to implement this comparator in this
	 * file (externally to the class SortingAccounts). The class cannot be
	 * public.
	 */
	@Override
	public void sort(Account[] array, Comparator<Account> comparator,
			Strategy strategy) {
		if (strategy == Strategy.BUBBLESORT) {
			bubblesort(array, comparator);
		}

		if (strategy == Strategy.MERGESORT) {
			mergesort(array, 0, array.length - 1, comparator);
		}
	}

	/**
	 * Sorts the the array of accounts assuming that each stored element is
	 * Comparable. Note that Account already implements Comparable. You must
	 * consider this to know if an account is less or greater than another
	 * account.
	 */
	@Override
	public void sortComparables(Account[] array, Strategy strategy) {

		Comparator<Account> compara = new Comparator<Account>() {

			@Override
			public int compare(Account o1, Account o2) {
				return o1.compareTo(o2);
			}
		};

		if (strategy == Strategy.BUBBLESORT) {
			bubblesort(array, compara);
		}

		if (strategy == Strategy.MERGESORT) {
			mergesort(array, 0, array.length - 1, compara);
		}
	}

	private void mergesort(Account array[], int leftIndex, int rightIndex,
			Comparator<Account> comparator) {

		if (leftIndex < rightIndex) {

			int media = (leftIndex + rightIndex) / 2;

			mergesort(array, leftIndex, media, comparator);

			mergesort(array, media + 1, rightIndex, comparator);

			merge(array, leftIndex, media, rightIndex, comparator);
		}
	}

	private void merge(Account[] array, int leftIndex, int media, int rightIndex, Comparator<Account> comparator) {

		Account[] arrayAux = new Account[array.length];
		for (int i = leftIndex; i <= media; i++) {
			arrayAux[i] = array[i];
		}

		for (int j = media + 1; j <= rightIndex; j++) {
			arrayAux[rightIndex + media + 1 - j] = array[j];
		}

		int i = leftIndex;
		int j = rightIndex;

		for (int k = leftIndex; k <= rightIndex; k++) {
			if (comparator.compare(arrayAux[i], arrayAux[j]) <= 0) {
				array[k] = arrayAux[i];
				i++;
			} else {
				array[k] = arrayAux[j];
				j--;
			}
		}
	}

	private void bubblesort(Account[] array, Comparator<Account> comparator) {

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < (array.length-1); j++) {
				if (comparator.compare(array[j], array[j + 1]) == 1) {
					Account temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
	}
}
