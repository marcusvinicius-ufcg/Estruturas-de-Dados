package adt.bst;


/**
 * Prova do Segundo Estagio da disciplina
 * de Laboratorio de Estrutura de Dados
 * @author vinicius
 *
 */
public class BSTImprovedFillerImpl {

	/**
	 * This method fills an BST while maintaining the lowest height as possible.
	 */
	public static void fill(BSTImpl<Integer> tree, Integer[] numbers) {

		Integer[] copyNumbers = numbers;
		
		bubblesort(copyNumbers);
		
		addElementsInTree(tree, copyNumbers, 0, copyNumbers.length);
	}

	private static void addElementsInTree(BSTImpl<Integer> tree, Integer[] numbers, int leftIndex, int rightIndex) {
		if (leftIndex < rightIndex) {
			
			Integer median = (leftIndex + rightIndex) / 2;
			

			tree.insert(numbers[median]);
			
			addElementsInTree(tree, numbers, leftIndex, median);
			addElementsInTree(tree, numbers, median+1, rightIndex);
		}
	}

	private static void bubblesort(Integer[] array) {
		for (int i = 0; i <= array.length - 1; i++) {
			for (int j = 0; j < array.length - 1; j++) {
				if (array[j].compareTo(array[j + 1]) == 1) {
					swap(array, j, j + 1);
				}
			}
		}
	}

	/**
	 * Metodo realiza troca de dois elementos em um array.
	 * 
	 * @param array
	 * @param i - indice do primeiro elemento
	 * @param j - indice do segundo elemento
	 */
	private static void swap(Object[] array, int i, int j) {
		Object temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
