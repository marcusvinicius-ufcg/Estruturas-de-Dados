package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;
	private Integer index;

	public BSTImpl() {
		root = new BSTNode<T>();
		root.setParent(new BSTNode<T>());
		root.setLeft(new BSTNode<T>());
		root.setRight(new BSTNode<T>());
		index = 0;
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(root);
	}

	protected int height(BSTNode<T> node) {
		if (node.isEmpty()) {
			return -1;
		} else {
			Integer leftHeight = height((BSTNode<T>) node.getLeft());
			Integer rightHeight = height((BSTNode<T>) node.getRight());

			return leftHeight < rightHeight ? rightHeight+1 : leftHeight+1;
		}
	}

	
	@Override
	public BSTNode<T> search(T element) {
		return search(element, root);
	}

	protected BSTNode<T> search(T element, BSTNode<T> node) {
		BSTNode<T> auxNode = new BSTNode<T>();

		if (!node.isEmpty()) {
			if (element.compareTo(node.getData()) < 0) {
				auxNode = search(element, (BSTNode<T>) node.getLeft());
			} else if (element.compareTo(node.getData()) > 0) {
				auxNode = search(element, (BSTNode<T>) node.getRight());
			} else
				auxNode = node;
		}
		return auxNode;
	}

	@Override
	public void insert(T value) {
		if (root.isEmpty()) {
			root.setData(value);
			root.setParent(new BSTNode<T>());
			root.setLeft(new BSTNode<T>());
			root.setRight(new BSTNode<T>());
		} else {
			insert(value, root);
		}
	}

	private void insert(T value, BSTNode<T> node) {
		if (value.compareTo(node.getData()) < 0) {
			if (node.getLeft().isEmpty()) {
				node.getLeft().setParent(node);
				node.getLeft().setData(value);
				node.getLeft().setLeft(new BSTNode<T>());
				node.getLeft().setRight(new BSTNode<T>());
			} else {
				insert(value, (BSTNode<T>) node.getLeft());
			}
		} else {
			if (node.getRight().isEmpty()) {
				node.getRight().setParent(node);
				node.getRight().setData(value);
				node.getRight().setLeft(new BSTNode<T>());
				node.getRight().setRight(new BSTNode<T>());
			} else {
				insert(value, (BSTNode<T>) node.getRight());
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return maximo(root);
	}

	private BSTNode<T> maximo(BSTNode<T> node) {
		return node.isEmpty() ? null : node.getRight().isEmpty() ? node : maximo((BSTNode<T>) node.getRight());
	}

	@Override
	public BSTNode<T> minimum() {
		return minimum(root);
	}

	protected BSTNode<T> minimum(BSTNode<T> node) {
		return node == null || node.isEmpty() ? null : (node.getLeft().isEmpty()) ? node: minimum((BSTNode<T>) node.getLeft());
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> result = search(element);
		return !result.isEmpty() ? sucessor(result) : new BSTNode<T>();
	}

	private BSTNode<T> sucessor(BSTNode<T> node) {

		if (!node.getRight().isEmpty()) {
			return minimum((BSTNode<T>) node.getRight());
		}
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (!parent.isEmpty() && node.equals(parent.getRight())) {
			node = parent;
			parent = (BSTNode<T>) parent.getParent();
		}
		return parent.isEmpty() ? null : parent;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		return predecessor(search(element));
	}

	private BSTNode<T> predecessor(BSTNode<T> node) {
		if (search(node.getData()) == null) {
			return null;
		}
		if (!node.getLeft().isEmpty()) {
			return maximo((BSTNode<T>) node.getLeft());
		}

		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (!parent.isEmpty() && node.equals(parent.getLeft())) {
			node = parent;
			parent = (BSTNode<T>) parent.getParent();
		}
		return parent.isEmpty() ? null : parent;
	}

	@Override
	public void remove(T element) {
		root = remove(root, element);
	}
	

	protected BSTNode<T> remove(BSTNode<T> node, T element) {
		if (isEmpty()) {
			return new BSTNode<>();
		}
		if (element.compareTo(node.getData()) < 0) {
			node.setLeft(remove((BSTNode<T>) node.getLeft(), element));
		} else if (element.compareTo(node.getData()) > 0) {
			node.setRight(remove((BSTNode<T>) node.getRight(), element));
		} else {
			if (node.getRight().isEmpty()) {
				return (BSTNode<T>) node.getLeft();
			}
			if (node.getLeft().isEmpty()) {
				return (BSTNode<T>) node.getRight();
			}
			BSTNode<T> t = node;
			node = minimum((BSTNode<T>) t.getRight());
			node.setRight(removerMinimo((BSTNode<T>) t.getRight()));
			node.setLeft(t.getLeft());
		}
		return node;
	}

	protected BSTNode<T> removerMinimo(BSTNode<T> node) {
		if (node.isEmpty()) {
			return node;
		} else if (!(node.getLeft().isEmpty())) {
			node.setLeft(removerMinimo((BSTNode<T>) node.getLeft()));
			return node;
		} else {
			node = (BSTNode<T>) node.getRight();
			return node;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T[] preOrder() {

		Comparable[] array = new Comparable[size()];
		preOrder((T[]) array, root);
		index = 0;
		return (T[]) array;
	}

	private void preOrder(T[] array, BSTNode<T> node) {
		if (!node.isEmpty()) {
			array[index++] = node.getData();
			preOrder(array, (BSTNode<T>) node.getLeft());
			preOrder(array, (BSTNode<T>) node.getRight());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public T[] order() {

		Comparable[] array = new Comparable[size()];
		order((T[]) array, root);
		index = 0;
		return (T[]) array;
	}

	private void order(T[] array, BSTNode<T> node) {
		if (!node.isEmpty()) {
			order(array, (BSTNode<T>) node.getLeft());
			array[index++] = node.getData();
			order(array, (BSTNode<T>) node.getRight());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T[] postOrder() {
		Comparable[] array = new Comparable[size()];
		postOrder((T[]) array, root);
		index = 0;
		return (T[]) array;
	}

	private void postOrder(T[] array, BSTNode<T> node) {
		if (!node.isEmpty()) {
			postOrder(array, (BSTNode<T>) node.getLeft());
			postOrder(array, (BSTNode<T>) node.getRight());
			array[index++] = node.getData();
		}
	}

	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		Integer result = 0;
		return !node.isEmpty() ? result = 1+size((BSTNode<T>) node.getLeft())+size((BSTNode<T>) node.getRight()): result;
	}
}
