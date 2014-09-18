package adt.rbtree;

import java.util.ArrayList;
import java.util.List;

import adt.avltree.AVLTreeImpl;
import adt.bst.BSTNode;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends AVLTreeImpl<T> 
	implements RBTree<T> {
	
	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}
	
	protected int blackHeight() {
		return blackHeight(root);
	}
	private int blackHeight(BSTNode<T> node) {
		int resp;
		if (node.isEmpty()){
	        resp = 0;  
		} else {    
	        int left = height((BSTNode<T>) node.getLeft());    
	        int right = height((BSTNode<T>) node.getRight());    
	        if (left > right) {
	        	resp = left;
	        	if (((RBNode<T>)node).getColour().equals(Colour.BLACK)){
	        		resp = left + 1;    
	        	}
	        } else {
	        	resp = right;
	        	if (((RBNode<T>)node).getColour().equals(Colour.BLACK)){
	        		resp = right + 1;    
	        	}    
	        }
	    }
		return resp;
	}

	protected boolean verifyProperties(){
		boolean resp = verifyNodesColour()
						&& verifyNILNodeColour()
						&& verifyRootColour()
						&& verifyChildrenOfRedNodes()
						&& verifyBlackHeight();
		
		return resp;
	}
	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed by the type Colour.
	 */
	private boolean verifyNodesColour(){
		return true; //already implemented
	}
	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour(){
		return ((RBNode<T>)root).getColour() == Colour.BLACK; //already implemented
	}
	
	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour(){
		return true; //already implemented
	}
	
	/**
	 * Verifies the property for all RED nodes: the children of a red node must be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes(){
		return verifyChildrenOfRedNodes((RBNode<T>) root);
	}
	private boolean verifyChildrenOfRedNodes(RBNode<T> node){
		boolean resp = false;
		if (node.isEmpty()){
			return true;
		} else {
			if (node.getColour().equals(Colour.RED)) {
				if (((RBNode<T>)node.getLeft()).getColour().equals(Colour.RED) ||
					((RBNode<T>)node.getRight()).getColour().equals(Colour.RED)){
					resp = false;
				} else {
					resp = verifyChildrenOfRedNodes((RBNode<T>) node.getLeft()) && 
							verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
				}
			} else {
				resp = verifyChildrenOfRedNodes((RBNode<T>) node.getLeft()) && 
						verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
			}
		}
		return resp;
	}
	
	
	/**
	 * Verifies the black-height property from the root. The method blackHeight returns an exception if the black heights are different.  
	 */
	private boolean verifyBlackHeight(){
		return blackHeight((RBNode<T>) root.getLeft()) == blackHeight((RBNode<T>) root.getRight());
	}
	
	@Override
	public void insert(T value) {
		RBNode<T> inserido = insert((RBNode<T>) root, value);
		((RBNode<T>)inserido).setColour(Colour.RED);
		fixUpCase1(inserido);
	}

	private RBNode<T> insert(RBNode<T> node, T element){
		RBNode<T> inserido = null;
		if (node.isEmpty()){
			node.setData(element);
			node.setLeft(new RBNode<T>());
			node.setRight(new RBNode<T>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);
			inserido = node;
		} else {
			if (element.compareTo(node.getData()) < 0){
				inserido = insert((RBNode<T>) node.getLeft(), element);
			} else if (element.compareTo(node.getData()) > 0){
				inserido = insert((RBNode<T>) node.getRight(), element);
			}
		}
		return inserido;
	}
	
	@Override
	public RBNode<T>[] extendedPreOrder(){
		List<RBNode<T>> list = new ArrayList<RBNode<T>>();
		extendedPreOrder( root, list);
		return toArray(list);
	}
	
	private void extendedPreOrder(BSTNode<T> node, List<RBNode<T>> array){
		if (!node.isEmpty()){
			array.add((RBNode<T>) node);
			extendedPreOrder((BSTNode<T>) node.getLeft(), array);
			extendedPreOrder((BSTNode<T>) node.getRight(), array);
		}
	}
	
	private RBNode<T>[] toArray(List<RBNode<T>> list){
		RBNode<T>[] array = (RBNode<T>[]) new RBNode[list.size()];
		for (int i = 0; i < list.size(); i++){
			array[i] = list.get(i);
		}
		return array;
	}
	
	private void fixUpCase1(RBNode<T> node){
		if (node.equals(root)){
			node.setColour(Colour.BLACK);
		} else {
			fixUpCase2(node);
		}
	}
	
	private void fixUpCase2(RBNode<T> node){
		if (((RBNode<T>)node.getParent()).getColour().equals(Colour.RED)){
			fixUpCase3(node);
		}
	}
	
	private void fixUpCase3(RBNode<T> node){
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandParent = (RBNode<T>) parent.getParent();
		RBNode<T> uncle = getUncle(node);
		
		if (uncle.getColour().equals(Colour.RED)){
			parent.setColour(Colour.BLACK);
			uncle.setColour(Colour.BLACK);
			grandParent.setColour(Colour.RED);
			fixUpCase1(grandParent);
		} else {
			fixUpCase4(node);
		}
	}
	
	private void fixUpCase4(RBNode<T> node){
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandParent = (RBNode<T>) parent.getParent();
		RBNode<T> next = node;
		
		if (!parent.getRight().isEmpty() && !grandParent.getLeft().isEmpty()){
			if (isRightChild(node) && isLeftChild(parent)){
				leftRotation(parent);
				next = (RBNode<T>) node.getLeft();
			}
		}
		else if (!parent.getLeft().isEmpty() && !grandParent.getRight().isEmpty()){
			if (isLeftChild(node) && isRightChild(parent)){
				rightRotation(parent);
				next = (RBNode<T>) node.getRight();
			}
		}
		
		fixUpCase5(next);
	}
	
	private void fixUpCase5(RBNode<T> node){
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandParent = (RBNode<T>) parent.getParent();
		parent.setColour(Colour.BLACK);
		grandParent.setColour(Colour.RED);
		
		if (!parent.getLeft().isEmpty()){
			if (parent.getLeft().equals(node)){
				rightRotation(grandParent);
			} 
		} else {
				leftRotation(grandParent);
		}
	}
	
	private boolean isLeftChild(RBNode<T> child){
		return child.getParent().getLeft().equals(child);
	}
	
	private boolean isRightChild(RBNode<T> child){
		return child.getParent().getRight().equals(child);
	}
	
	private RBNode<T> getUncle(RBNode<T> node){
		RBNode<T> uncle = null;
		RBNode<T> parent = (RBNode<T>) node.getParent();
		
		if (parent!=null) {
			RBNode<T> grandParent = (RBNode<T>) parent.getParent();
			if (grandParent!=null){
				
				if (!grandParent.getLeft().isEmpty()){
					if (grandParent.getLeft().equals(parent)){
						uncle = (RBNode<T>) grandParent.getRight();
					}
					else {
						uncle = (RBNode<T>) grandParent.getLeft();
					}
				} else {
					uncle = (RBNode<T>) grandParent.getLeft();
				}
			}
		}
		return uncle;
	}
}