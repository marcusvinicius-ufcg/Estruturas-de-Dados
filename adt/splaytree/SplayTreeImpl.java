package adt.splaytree;

import adt.avltree.AVLTreeImpl;
import adt.bst.BSTNode;

public class SplayTreeImpl<T extends Comparable<T>> extends AVLTreeImpl<T>
		implements SplayTree<T> {

	public SplayTreeImpl() {
		super();
	}

	private void splay(BSTNode<T> node) {

		// CASO NAO SEJA RAIZ
		if (!node.equals(root)) {    

			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			//CASO AVO SEJA RAIZ
			if (parent.equals(root)) {  
				
				if (parent.getLeft().equals(node)) {
					rightRotation((BSTNode<T>) parent);
				} else {
					leftRotation((BSTNode<T>) parent);
				}
			} else {
				
				BSTNode<T> grand = (BSTNode<T>) parent.getParent();
				
				// CASO SEJA FILHO DA ESQUERDA
				if (parent.getLeft().equals(node)) {
					// CASO PAI SEJA FILHO DA ESQUERDA
					if (grand.getLeft().equals(parent)) { 
						rightRotation(grand);
						rightRotation(parent);
					// CASO PAI SEJA FILHO DA DIREITA
					} else {
						rightRotation(parent);
						leftRotation(grand);
					}
				// CASO SEJA FILHO DA DIREIRA
				} else {
					//CASO PAI SEJA FILHO DA ESQUERDA
					if (grand.getLeft().equals(parent)) { 
						leftRotation(parent);
						rightRotation(grand);
					// CASO PAI SEJA FILHO DA DIREITA
					} else {
						leftRotation(grand);
						leftRotation(parent);
					}
				}
			}
			splay(node);
		}
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> node = search(element, root);
		if (!node.isEmpty()) {
			splay(node);
		} else {
			if (!node.equals(root)) {
				splay((BSTNode<T>) node.getParent());
			} 			
		}
		return node;
	}

	@Override
	public void insert(T element) {
		insert(root, element);
		BSTNode<T> node = search(element, root);
		if (!node.isEmpty()) {
			splay(node);
		}
	}

	private void insert(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		} else {
			if (element.compareTo(node.getData()) < 0) {
				insert((BSTNode<T>) node.getLeft(), element);
			} else if (element.compareTo(node.getData()) > 0) {
				insert((BSTNode<T>) node.getRight(), element);
			}
		}
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element, root);;
		if (node.isEmpty()) {
			if (!node.equals(root)) {
				splay((BSTNode<T>) node.getParent());
			}
		} else {
			if (!node.equals(root)) {
				BSTNode<T> toSplay = (BSTNode<T>) node.getParent();
				remove(node);
				splay(toSplay);
			} else {
				remove(node);
			}
		}
	}

	private void remove(BSTNode<T> node) {

		// CASO O ELEMENTO ESTEJA NA ARVORE
		if (!node.isEmpty()) { 

			//CASO NAO TENHA FILHOS
			if (node.getLeft().isEmpty() && node.getRight().isEmpty()) {
				node.setData(null);

			// CASO SO TENHA UM FILHO
			} else if ((node.getLeft().isEmpty() && !node.getRight().isEmpty()) || (!node.getLeft().isEmpty() && node.getRight().isEmpty())) { 

				if (!(node.equals(root))) {
					// CASO SEJA FILHO DA ESQUERDA
					if (node.getParent().getLeft().equals(node)) { 

						if (!node.getLeft().isEmpty()) {
							node.getParent().setLeft(node.getLeft()); 
						} else {
							node.getParent().setLeft(node.getRight()); 
						}
					// CASO SEJA FILHO DA DIREITA
					} else { 
						if (!node.getLeft().isEmpty()) {
							node.getParent().setRight(node.getLeft()); 
						} else {
							node.getParent().setRight(node.getRight()); 
						}
					}

				} else {
					if (node.getLeft().isEmpty()) {
						root = (BSTNode<T>) node.getRight();
					} else {
						root = (BSTNode<T>) node.getLeft();
					}
				}
				
			// CASO TENHA FILHOS A DIREITA E ESQUERDA
			} else { 
				BSTNode<T> aux = new BSTNode<T>();
				aux.setData(sucessor(node.getData()).getData());
				remove(sucessor(node.getData()));
				node.setData(aux.getData());
			}
		}
	}
}