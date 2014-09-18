package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	public AVLTreeImpl(){
		super();
	}
	
	protected int calculateBalance(BSTNode<T> node){
		int resp = 0;
		if (!node.isEmpty()){
			resp = height((BSTNode<T>)node.getLeft()) - height((BSTNode<T>)node.getRight()); 
		}
		return resp;
	}
	

	protected void rebalance(BSTNode<T> node){
		int balance = calculateBalance(node);
		
		if (balance > 1){
			int balance2 = calculateBalance((BSTNode<T>)node.getLeft());
			if (balance2 < 0){
				leftRotation((BSTNode<T>)node.getLeft());
				rightRotation(node);
			} else {
				rightRotation(node);
			}
		} else if (balance < -1){
			int balance2 = calculateBalance((BSTNode<T>)node.getRight());
			if (balance2 > 0) {
				rightRotation((BSTNode<T>)node.getRight());
				leftRotation(node);
			} else {
				leftRotation(node);
			}
		}
	}
	
	protected void rebalanceUp(BSTNode<T> node){
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while(parent!=null){
			rebalance(parent);
			parent = (BSTNode<T>) parent.getParent();
		}
	}
	
	protected void leftRotation(BSTNode<T> node){
		BSTNode<T> pivot = (BSTNode<T>) node.getRight();
		
		node.setRight(pivot.getLeft());
		node.getRight().setParent(node);
		
		pivot.setLeft(node);
		pivot.setParent(node.getParent());
		
		node.setParent(pivot);

		if (node.equals(root)){
			root = pivot;
		} else {
			if (node.equals(pivot.getParent().getRight())){
				pivot.getParent().setRight(pivot);
			} else {
				pivot.getParent().setLeft(pivot);
			}
		}
		
	}
	
	protected void rightRotation(BSTNode<T> node){
		BSTNode<T> pivot = (BSTNode<T>) node.getLeft();
		
		node.setLeft(pivot.getRight());
		node.getLeft().setParent(node);
		
		pivot.setRight(node);
		pivot.setParent(node.getParent());

		node.setParent(pivot);
		
		if (node.equals(root)){
			root = pivot;
		} else {
			if (node.equals(pivot.getParent().getRight())){
				pivot.getParent().setRight(pivot);
			} else {
				pivot.getParent().setLeft(pivot);
			}
		}
	}
	
	@Override
	public void insert(T element) {
		insert(root, element);
	}
	
	private void insert(BSTNode<T> node, T element){
		if (node.isEmpty()){
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
		} else {
			if (element.compareTo(node.getData()) < 0){
				insert((BSTNode<T>) node.getLeft(), element);
			} else if (element.compareTo(node.getData()) > 0){
				insert((BSTNode<T>) node.getRight(), element);
			}
			rebalance(node);
		}
	}
	
	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		
		if (!node.isEmpty()){ 
			
			if (node.getLeft().isEmpty() && node.getRight().isEmpty()){
				node.setData(null);
				rebalanceUp(node);
			
			} else if ((node.getLeft().isEmpty() && !node.getRight().isEmpty()) || 
					  (!node.getLeft().isEmpty() && node.getRight().isEmpty())) {
				
				if (!(node.equals(root))) {
					
					if (node.getParent().getLeft().equals(node)){ 
				
						if (!node.getLeft().isEmpty()) {
			                 node.getParent().setLeft(node.getLeft()); 
						} else {
			                 node.getParent().setLeft(node.getRight()); 
						}
				
					} else {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setRight(node.getLeft());
						} else {
			                node.getParent().setRight(node.getRight());
						}
					}
				
				} else {
					if (node.getLeft().isEmpty()){
						root = (BSTNode<T>) node.getRight();
					} else {
						root = (BSTNode<T>) node.getLeft();
					}
				}
				rebalanceUp(node);
			
			} else {                                                        
				
				BSTNode<T> aux = new BSTNode<T>();
				aux.setData(sucessor(element).getData());
				remove(sucessor(element).getData());
		    	node.setData(aux.getData());
			}
		}
	}
}