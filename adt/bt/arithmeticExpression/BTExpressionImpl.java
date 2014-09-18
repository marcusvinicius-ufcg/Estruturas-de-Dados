package adt.bt.arithmeticExpression;

import adt.bt.BTNode;

/**
 * An implementation of a BTExpression. Its root contains an Arithmetic
 * Expression. Some methods do not make sense in this kind of tree.
 */
public class BTExpressionImpl implements BTExpression {

	protected BTNode<String>	root;

	@Override
	public BTNode<String> getRoot() {
		return root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(root);
	}

	private int height(BTNode<String> node) {
		Integer leftHeight = 0;
		Integer rightHeight = 0;
		final Integer TREE_EMPTY = -1;

		if (node.isEmpty())
			return TREE_EMPTY;

		if (!node.getLeft().isEmpty())
			leftHeight += 1 + height((BTNode<String>) node.getLeft());

		if (!node.getRight().isEmpty())
			rightHeight += 1 + height((BTNode<String>) node.getRight());

		return Math.max(leftHeight, rightHeight);
	}
	
	@Override
	public BTNode<String> search(String elem) {
		return search(elem, root);
	}

	private BTNode<String> search(String element, BTNode<String> node) {
		BTNode<String> auxNode = new BTNode<String>();

		if (!node.isEmpty()) {
			if (element.compareTo(node.getData()) < 0) {
				auxNode = search(element, (BTNode<String>) node.getLeft());
			} else if (element.compareTo(node.getData()) > 0) {
				auxNode = search(element, (BTNode<String>) node.getRight());
			} else
				auxNode = node;
		}
		return auxNode;
	}
	
	@Override
	public void insert(String value) {
		throw new RuntimeException("Method does not make sense");
	}

	@Override
	public void remove(String key) {
		throw new RuntimeException("Method does not make sense");
	}

	@Override
	public String[] preOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] order() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] postOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer evaluate() {
		// TODO Auto-generated method stub
		return null;
	}

}
