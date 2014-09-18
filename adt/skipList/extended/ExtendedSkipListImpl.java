package adt.skipList.extended;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import adt.skipList.SkipListImpl;
import adt.skipList.SkipNode;

public class ExtendedSkipListImpl<V> extends SkipListImpl<V> {

	public ExtendedSkipListImpl(int maxLevel) {
		super(maxLevel);
	}
	
	/**
	 * This method must walk down in the forward pointers as the usual search, 
	 * except for when the key of the forward node is equal to the given key, the 
	 * algorithm walks to the right (to that node) and returns it. Otherwise, 
	 * it continues going down in the forward pointers. Thus, the criteria to walk
	 * horizontally is finding a node with a key that is less or equal to the given key.
	 * If there is no node containing the given key, the algorithm returns the NIL node.
	 */
	public SkipNode<V> extendedSearch(int key){
		SkipNode<V> foundNode = root;

		for (int i = (height() - 1); i >= 0; i--) {
			while (foundNode.getForward(i).getKey() < key) {
				foundNode = foundNode.getForward(i);
			}
		}
		foundNode = foundNode.getForward(0);

		if (foundNode.getKey() != key) {
			foundNode = NIL;
		}
		return foundNode;
	}
	
	/**
	 * It uses the extended search to find a node with the given key. If it exists, then the 
	 * algorithm returns the next node in the skip list. Otherwise, the algorithm returns 
	 * the NIL node. 
	 */
	public SkipNode<V> successor(int key){
		return search(key).getForward(0);
	}
	
	/**
	 * It returns an array containing the nodes of a skip list by height. Thus, first the nodes
	 * with highest height are put in the array (from left to right), then nodes with the next 
	 * highest height are put into the array, and so on. For example, consider the skip list with 
	 * max height 5 and nodes given by [(4,2),[8,3],(10,1),(15,2),(19,1)], where each pair represents 
	 * a node as (key,height). The result of this method would be [(8,3),(4,2),(15,2),(10,1),(19,1)].  
	 */
	public SkipNode<V>[] toArrayByHeight(){
				
		SkipNode<V>[] array = new SkipNode[size()];
		Integer aux = 0;
		for (int i = (height() - 1); i >= 0; i--) {
			SkipNode<V> currentNode = root.getForward(i);
			while (!currentNode.equals(NIL)) {
				array[aux++] = currentNode;
				currentNode = currentNode.getForward(0);
			}
		}
		return array;
	}
}
