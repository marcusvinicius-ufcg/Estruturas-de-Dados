package adt.bst.improved;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

public class BSTImprovedImplT2<T extends Comparable<T>> extends BSTImpl<T> {

	private int i = 0;

	/**
	 * It builds an array (T[][]) containing all single paths from a specific
	 * node to all its leaves. A single path is an array (of type T[])
	 * containing all visited nodes from the rootElement (inclusive) to a leaf
	 * (inclusive). You can use auxiliary methods for this purpose.
	 * 
	 * @param rootElement
	 * @return
	 */
	public Object[][] buildAllPaths(T rootElement) {

		BSTNode<T> node = search(rootElement);

		Integer columns = height(node) + 1;
		Integer rows = numberLeafs(node);
		
		Object[][] matriz = new Object[rows][columns];
		Object[] lefs = getLeafs(rootElement);

		for (int row = 0; row < rows; row++) {

			Object[] path = new Object[columns];
			i = 0;
			createRow((T) lefs[row], node, path);
			matriz[row] = path;
		}

		return (Object[][]) matriz;
	}

	private void createRow(T element, BSTNode<T> node, Object[] array) {

		if (node.isEmpty()) {
			return;
		} else {

			if (element.compareTo(node.getData()) < 0) {
				array[i++] = node.getData();
				createRow(element, (BSTNode<T>) node.getLeft(), array);

			} else if (element.compareTo(node.getData()) > 0) {
				array[i++] = node.getData();
				createRow(element, (BSTNode<T>) node.getRight(), array);

			} else {
				array[i++] = node.getData();
			}
		}
	}

	private Object[] getLeafs(T rootElement) {
		BSTNode<T> node = search(rootElement);

		Object[] array = new Object[numberLeafs(node)];
		i = 0;

		addLeafs(array, node);

		return array;
	}

	private void addLeafs(Object[] array, BSTNode<T> node) {
		if (node.isEmpty()) {
			return;
		} else if (node.isLeaf()) {
			array[i++] = node.getData();
		} else {
			addLeafs(array, (BSTNode<T>) node.getLeft());
			addLeafs(array, (BSTNode<T>) node.getRight());
		}
	}

	private int numberLeafs(BSTNode<T> node) {
		if (node.isEmpty()) {
			return 0;
		} else if (node.isLeaf()) {
			return 1;
		} else {
			return numberLeafs((BSTNode<T>) node.getLeft()) + numberLeafs((BSTNode<T>) node.getRight());
		}
	}
}
