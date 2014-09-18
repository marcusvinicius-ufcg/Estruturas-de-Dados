package adt.btree;

import java.util.Collections;
import java.util.LinkedList;

public class BNode<T extends Comparable<T>> {
	protected LinkedList<T> elements; //PODE TRABALHAR COM ARRAY TAMBEM
	protected LinkedList<BNode<T>> children; //PODE TRABALHAR COM ARRAY TAMBEM
	protected BNode<T> parent;
	protected int maxKeys;
	protected int maxChildren;
	
	public BNode(int order){
		this.maxChildren = order;
		this.maxKeys = order - 1;
		this.elements = new LinkedList<T>();	
		this.children = new LinkedList<BNode<T>>();
	}
	@Override
	public String toString() {
		return this.elements.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		boolean resp = false;
		if(obj != null){
			if(obj instanceof BNode){
				if(this.size() == ((BNode<T>) obj).size()){
					resp = true;
					int i = 0;
					while(i<this.size() && resp){
						resp = resp && this.getElementAt(i).equals(((BNode<T>) obj).getElementAt(i));
						i++;
					}
				}
			}
		}
		return resp;
	}
	public boolean isEmpty(){
		return this.size() == 0;
	}
	public int size(){
		return this.elements.size();
	}
	public boolean isLeaf(){
		return this.children.size() == 0;
	}
	public boolean isFull(){
		return this.size()== maxKeys;
	}
	public void addElement(T element){
		this.elements.add(element);
		Collections.sort(elements);
	}
	public void removeElement(T element){
		this.elements.remove(element);
	}
	public void removeElement(int position){
		this.elements.remove(position);
	}
	public void addChild(int position, BNode<T> child){
		this.children.add(position, child);
		child.parent = this;
	}
	public void removeChild(BNode<T> child){
		this.children.remove(child);
	}
	public int indexOfChild(BNode<T> child){
		return this.children.indexOf(child);
	}
	public T getElementAt(int index){
		return this.elements.get(index);
	}
	protected void split(){
		//TODO Implement this method
		//ESTE METODO PODE SER IMPLEMENTADO NA BTREEIMPL TAMBEM. FICA A SEU CRITERIO
		
		T medio = getElementoMedio();
		
		BNode<T> leftNodes = new BNode<>(maxChildren);
		BNode<T> rightNodes = new BNode<>(maxChildren);
		
		for (int i = 0; i < this.elements.size(); i++) {
			if (medio.compareTo(this.getElementAt(i)) > 0) {
				leftNodes.elements.add(this.getElementAt(i));
			} else if (medio.compareTo(this.getElementAt(i)) < 0) {
				rightNodes.elements.add(this.getElementAt(i));
			}
		}
		
		if (this.parent == null && this.isLeaf()) {
			this.setElements(new LinkedList<T>());
			this.addElement(medio);
			leftNodes.parent = this;
			rightNodes.parent = this;
			this.addChild(0, leftNodes);
			this.addChild(1, rightNodes);
		} else if (this.parent == null && !this.isLeaf()) {
			LinkedList<BNode<T>> children = this.children;
			this.setElements(new LinkedList<T>());
			this.addElement(medio);
			this.setChildren(new LinkedList<BNode<T>>());
			leftNodes.parent = this;
			rightNodes.parent = this;
			this.addChild(0, leftNodes);
			this.addChild(1, rightNodes);
			if (!this.isLeaf()) {
				corrigirChildrens(children, leftNodes, 0, leftNodes.size() + 1);
				corrigirChildrens(children, rightNodes, rightNodes.size() + 1, children.size());
			}
		//caso seja folha
		} else if (this.isLeaf()) {
			
			BNode<T> promote = new BNode<>(maxChildren);
			
			promote.elements.add(medio);
			promote.parent = this.parent;
			
			leftNodes.parent = this.parent;
			rightNodes.parent = this.parent;
			
			Integer position = procuraPosicaoNoPai(promote.parent.getElements(), medio);
			Integer left = position;
			Integer right = position + 1;

			parent.children.set(left, leftNodes);
			parent.children.add(right, rightNodes);
			
			promote.promote();
			
		} else {
			
			LinkedList<BNode<T>> children = this.children;
			
			BNode<T> promote = new BNode<>(maxChildren);
			
			promote.elements.add(medio);
			promote.parent = this.parent;
			
			leftNodes.parent = this.parent;
			rightNodes.parent = this.parent;
			
			Integer position = procuraPosicaoNoPai(promote.parent.getElements(), medio);
			Integer left = position;
			Integer right = position + 1;
			
			parent.children.add(left, leftNodes);
			parent.children.add(right, rightNodes);
			
			Integer aux1 = 0;
			Integer aux2 = 0;
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).elements.get(0).compareTo(medio) < 0) {
					leftNodes.addChild(aux1++, children.get(i));
				} else {
					rightNodes.addChild(aux2++, children.get(i));
				}
			}
			parent.removeChild(this);
			promote.promote();
		}
	}
	
	private void corrigirChildrens(LinkedList<BNode<T>> children, BNode<T> parent, int first, int last) {
		for (int i = first; i < last; i++) {
			int position = procuraPosicaoNoPai(parent.getElements(), children.get(i).elements.get(0));
			parent.addChild(position, children.get(i));
		}
	}
	
	private int procuraPosicaoNoPai(LinkedList<T> elements, T element) {
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).compareTo(element) > 0) {
				return i;
			}
		}	
		return elements.size();
		
	}
	
	//Metodo retorna o Elemento Medio
	private T getElementoMedio() {
		return elements.get(elements.size()/2);
	}
	
	protected void promote(){
		//TODO Implement this method
		//ESTE METODO PODE SER IMPLEMENTADO NA BTREEIMPL TAMBEM. FICA A SEU CRITERIO
		
		Integer position = procuraPosicaoNoPai(this.parent.getElements(), this.getElementAt(0));
		this.parent.getElements().add(position, this.getElementAt(0));
		if (this.parent.size() > maxKeys) {
			this.parent.split();
		}
	}
	public LinkedList<T> getElements() {
		return elements;
	}
	public void setElements(LinkedList<T> elements) {
		this.elements = elements;
	}
	public LinkedList<BNode<T>> getChildren() {
		return children;
	}
	public void setChildren(LinkedList<BNode<T>> children) {
		this.children = children;
	}
}
