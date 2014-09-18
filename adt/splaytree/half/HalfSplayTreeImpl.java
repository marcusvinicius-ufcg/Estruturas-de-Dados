package adt.splaytree.half;
 
import adt.bst.BSTNode;
import adt.splaytree.SplayTreeImpl;
 
/**
 * Consider a variation of splay trees, called half-splay trees, where splaying
 * a node at depth d stops as soon as the node reaches depth [d/2]. Implement
 * this class.
 * 
 * Try to think about with method(s) you need to change. You may also need to
 * implement auxiliary method(s).
 * 
 * You will need a method to calculate the depth of a node in a tree. The splay
 * method has to use the above method to decide when stopping the rotations. You
 * may also need an auxiliary splay method (with other parameters).
 */
public class HalfSplayTreeImpl<T extends Comparable<T>> extends SplayTreeImpl<T> {
 
	protected int getProfundidade(BSTNode<T> node) {
		int depth = 0;
		BSTNode<T> aux = node;
		while (aux.getParent() != null) {
			depth++;
			aux = (BSTNode<T>) aux.getParent();
		}
		return depth;
	}
 
	public BSTNode<T> SuperSearch(T element) {
		BSTNode<T> node = super.search(element);
		return node;
	}
 

	protected void splay(BSTNode<T> node) {
		splay(node, getProfundidade(node));
	}
 
	protected void splay(BSTNode<T> node, int depth) {
		if (node == null || node.getParent() == null)
			return;
 
		if (node.getParent().equals(root)) {
			if (node.getData().compareTo(root.getData()) < 0) {
				rightRotation((BSTNode<T>) node.getParent());
			} else {
				leftRotation((BSTNode<T>) node.getParent());
			}
 
		} else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			BSTNode<T> grandParent = (BSTNode<T>) node.getParent().getParent();
			if (node.getData().compareTo(parent.getData()) < 0 && parent.getData().compareTo(grandParent.getData()) < 0) {
				if (getProfundidade(node) > (depth / 2)) {
					rightRotation(grandParent);
				}
				if (getProfundidade(node) > (depth / 2)) {
					rightRotation(parent);
				}
			} else if (node.getData().compareTo(parent.getData()) > 0
					&& parent.getData().compareTo(grandParent.getData()) > 0) {
				if (getProfundidade(node) > (depth / 2)) {
					leftRotation(grandParent);
				}
				if (getProfundidade(node) > (depth / 2)) {
					leftRotation(parent);
				}
			} else if (node.getData().compareTo(parent.getData()) < 0
					&& parent.getData().compareTo(grandParent.getData()) > 0) {
				if (getProfundidade(node) > (depth / 2)) {
					rightRotation(parent);
				}
				if (getProfundidade(node) > (depth / 2)) {
					leftRotation(grandParent);
				}
			} else if (node.getData().compareTo(parent.getData()) > 0
					&& parent.getData().compareTo(grandParent.getData()) < 0) {
				if (getProfundidade(node) > (depth / 2)) {
					leftRotation(parent);
				}
				if (getProfundidade(node) > (depth / 2)) {
					rightRotation(grandParent);
				}
			}
 
			if (getProfundidade(node) > depth) {
				splay(node, depth);
			}
		}
	}
}