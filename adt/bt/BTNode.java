package adt.bt;

/**
 * The node of a binary tree. Its internal data has type T, which does not need to be comparable. 
 */
public class BTNode<T> {
	protected T data;
	protected BTNode<T> left;
	protected BTNode<T> right;
	protected BTNode<T> parent;

	public BTNode(T data, BTNode<T> left, BTNode<T> right, BTNode<T> parent) {
		this.data = data;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	public BTNode() {
	
	}
	
	public boolean isEmpty(){
		return this.data == null;
	}
	
	@Override
	public String toString(){
		String resp = "NIL";
		if(!isEmpty()){
			resp = data.toString();
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj){
		boolean resp = false;
		if(obj instanceof BTNode){
			//resp = 	(this.isEmpty() && ((BTNode<T>)obj).isEmpty()) || 
			//		this.data.equals(((BTNode<T>)obj).data);
			if(!this.isEmpty() && !((BTNode<T>)obj).isEmpty()){
				resp = 	this.data.equals(((BTNode<T>)obj).data);
			}else{
				resp = 	this.isEmpty() && ((BTNode<T>)obj).isEmpty();
			}
			
		}
		return resp;
	}
	public T getData() {
		return data;
	}
	
	public boolean isLeaf(){
		return parent.isEmpty() ? false : left.isEmpty() && right.isEmpty();
	}
	
	public boolean isLeft(){
		return parent.isEmpty() ? false : (parent.left.equals(this));
	}
	
	public boolean isRight(){
		return parent.isEmpty() ? false : (parent.right.equals(this));
	}

	public void setData(T data) {
		this.data = data;
	}

	public BTNode<T> getLeft() {
		return left;
	}

	public void setLeft(BTNode<T> left) {
		this.left = left;
	}

	public BTNode<T> getRight() {
		return right;
	}

	public void setRight(BTNode<T> right) {
		this.right = right;
	}

	public BTNode<T> getParent() {
		return parent;
	}

	public void setParent(BTNode<T> parent) {
		this.parent = parent;
	}

	
}
