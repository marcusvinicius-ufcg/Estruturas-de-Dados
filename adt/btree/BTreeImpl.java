package adt.btree;

import java.util.ArrayList;
import java.util.List;

public class BTreeImpl<T extends Comparable<T>> implements
		BTree<T> {

	protected BNode<T> root;
	protected int order;
	
	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
	}
	
	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}
	private int height(BNode<T> node){
		int resp = -1;
		if(!node.isEmpty()){
			if(node.isLeaf()){
				resp = 0;
			}else{
				resp = 1 + height(node.children.get(0));
			}
		}
		return resp;
	}
	
	@Override
	public BNode<T>[] depthLeftOrder() {
		
		List<BNode<T>> array = new ArrayList<BNode<T>>();
		depthLeftOrder(array, root);
		BNode<T>[] resp = toArray(array);
		return resp;
	}
	
	private void depthLeftOrder(List<BNode<T>> array, BNode<T> node){
		if (!node.isEmpty()){
			array.add(node);
			for (int i = 0; i < node.getChildren().size(); i++){
				depthLeftOrder(array, node.getChildren().get(i));
			}
		}
	}
	
	private BNode<T>[] toArray(List<BNode<T>> list){
		BNode<T>[] array = (BNode<T>[]) new BNode[list.size()];
		for (int i = 0; i < list.size(); i++){
			array[i] = list.get(i);
		}
		return array;
	}
	
	@Override
	public int size() {
		BNode<T>[] nodes = depthLeftOrder();
		int size = 0;
		if (nodes.length!=0){
			for (BNode<T> node : nodes){
				size += node.size();
			}
		}
		return size;
	}

	@Override
	public BNodePosition<T> search(T element) {
		BNodePosition<T> resp = search(root, element);
		return resp;
	}
	private BNodePosition<T> search(BNode<T> node, T element){
		BNodePosition<T> resp;
		int i = 0;
		while (i < node.size() && element.compareTo(node.getElementAt(i)) > 0){
			i++;
		}
		if (i < node.size() && element.equals(node.getElementAt(i))) {
			resp = new BNodePosition<T>(node, i);
		}
		else {
			if (node.isLeaf()){
				resp = new BNodePosition<T>();
			}else{
				resp = search(node.getChildren().get(i), element);
			}
		}
		return resp;
	}

	@Override
	public void insert(T element) {
		BNode<T> nodeToInsert = nodeToInsert(root, element);
		if (nodeToInsert!=null){
			if (!nodeToInsert.isFull()){
				nodeToInsert.addElement(element);
			} else {
				nodeToInsert.addElement(element);
				splitRecursive(nodeToInsert);
			}
		}

	}
	private void splitRecursive(BNode<T> node){
		if (node.getElements().size()>order-1){
			split(node);
			splitRecursive(node.parent);
		} 
	}
	private BNode<T> nodeToInsert(BNode<T> node, T element){
		BNode<T> resp;
		if (node.isLeaf()){
			resp = node;
			for (T elem : node.getElements()){
				if (elem.equals(element)){
					resp = null;
				}
			}
		} else {
			int i = 0;
			while (i < node.size() && element.compareTo(node.getElementAt(i)) > 0){
				i++;
			}
			if (i < node.size() && element.equals(node.getElementAt(i))){
				resp = null;
			} else {
				resp = nodeToInsert(node.getChildren().get(i), element);
			}
		}
		return resp;
	}

	//NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		BNode<T> maximo = node;
		while (!maximo.isLeaf()){
			maximo = maximo.getChildren().getLast();
		}
		
		return maximo;
	}
	
	@Override
	public BNode<T> minimum(BNode<T> node) {
		BNode<T> minimo = node;
		while (!minimo.isLeaf()){
			minimo = minimo.getChildren().getFirst();
		}
		return minimo;
	}
	
	@Override
	public void remove(T element) {
		//NAO PRECISA IMPLEMENTAR
	}
	
	private void split(BNode<T> node){
		
		int mediana = (node.getElements().size()/2);
		T elemMediano = node.getElementAt(mediana);
		
		BNode<T> left = new BNode<T>(order);
		BNode<T> right = new BNode<T>(order);

		for (int i = 0; i < node.size(); i++){     // colocando os elementos em left e right
			if (i < mediana) {
				left.addElement(node.getElementAt(i));
			} else if (i > mediana) {
				right.addElement(node.getElementAt(i));
			}
		}

		if (node.getChildren().size() > 0) {  // se o noh tem filhos, setando filhos de left e right
			for (int i = 0; i < node.getChildren().size(); i++) {
				if (i <= mediana) {
					left.getChildren().add(node.getChildren().get(i));
					node.getChildren().get(i).parent = left;
				} else {
					right.getChildren().add(node.getChildren().get(i));
					node.getChildren().get(i).parent = right;
				}
			}
		}
		
		if (node.equals(root)) {  // se node eh raiz o pai eh null, criando novo pai
			BNode<T> newParent = new BNode<T>(order);
			node.parent = newParent;
			newParent.getChildren().add(node);
			root = newParent;
		}
		
		node.parent.addElement(elemMediano);       // subindo mediana para o pai
		int index = node.parent.indexOfChild(node);
		
		node.parent.getChildren().set(index, left);  // filhos do pai agora vao incluir left e right
		node.parent.getChildren().add(index+1, right);
		
		left.parent = node.parent;  // setando pai de left e right
		right.parent = node.parent;
	}
	
	public String[] toStringArray(){               // usar nos testes
		BNode<T>[] nodes = depthLeftOrder();
		String[] array = new String[nodes.length];
		String node = "";
		for (int i = 0; i < nodes.length; i++){
			node = nodes[i].toString();
			array[i] = node;
		}
		return array;
	}

}