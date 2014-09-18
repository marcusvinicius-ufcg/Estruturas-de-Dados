package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {

	DoubleLinkedListNode<T> last;

	public DoubleLinkedListImpl() {
		head = new DoubleLinkedListNode<>();
		last = (DoubleLinkedListNode<T>) head;

	}

	@Override
	public void insertFirst(T element) {
		DoubleLinkedListNode<T> newHead = new DoubleLinkedListNode<>(element, (DoubleLinkedListNode<T>) head, new DoubleLinkedListNode<T>());
		
		((DoubleLinkedListNode<T>) head).previous = newHead;
		
		if (head.isNIL()) {
			last = newHead;

		}
		head = newHead;
	}

	@Override
	public void removeFirst() {
		if (!head.isNIL()) {
			head = head.next;

			if (head.isNIL()) {

				last = (DoubleLinkedListNode<T>) head;
			}
			((DoubleLinkedListNode<T>) head).previous = new DoubleLinkedListNode<T>();
		}
	}

	@Override
	public void removeLast() {
		if (!last.isNIL()) {
			last = last.previous;

			if (last.isNIL()) {
				head = last;
			}
			last.next = new DoubleLinkedListNode<T>();
			
		}
	}
	
	@Override
	public T search(T element) {

		DoubleLinkedListNode<T> auxHead = (DoubleLinkedListNode<T>) head;
		DoubleLinkedListNode<T> auxLast = last;
		
		while (!(auxHead.equals(auxLast)) && !(auxHead.next.equals(auxLast)) &&
			   !(auxHead.data.equals(element)) && !(auxLast.data.equals(element))) {
			auxHead = (DoubleLinkedListNode<T>) auxHead.next;
			auxLast = auxLast.previous;
		}

		if(auxHead.data.equals(element)){
			return auxHead.data;
		}
		if(auxLast.data.equals(element)){
			return auxLast.data;
		}
		return element;

	}
	
	@Override
	public void insert(T element) {
		DoubleLinkedListNode<T> newLast = new DoubleLinkedListNode<>(element, new DoubleLinkedListNode<T>(), last);

		last.next = newLast;
		
		if (last.isNIL()) {
			
			head = newLast;
		}
		last = newLast;
	}
	@Override
	public void remove(T element) {

		if (head.data.equals(element)) {
			removeFirst();
		} else {
			SingleLinkedListNode<T> aux = head;

			while (!aux.isNIL() && !aux.data.equals(element)) {
				aux = aux.next;
			}

			if(!aux.isNIL()){
				((DoubleLinkedListNode<T>) aux).previous.next = aux.next;
				((DoubleLinkedListNode<T>) aux.next).previous = ((DoubleLinkedListNode<T>) aux).previous;
			} 
		}
	}
	

}
