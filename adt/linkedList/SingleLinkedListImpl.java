package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return head.isNIL();
	}

	@Override
	public int size() {
		int size = 0;

		SingleLinkedListNode<T> auxNode = head;

		while (!auxNode.isNIL()) {
			size++;
			auxNode = auxNode.next;
		}

		return size;
	}

	@Override
	public T search(T element) {

		SingleLinkedListNode<T> auxNode = head;

		while (!auxNode.isNIL() && !auxNode.data.equals(element)) {
			auxNode = auxNode.next;
		}

		return auxNode.data;

	}

	@Override
	public void insert(T element) {
		SingleLinkedListNode<T> auxHead = head;

		if (head.isNIL()) {
			SingleLinkedListNode<T> newHead = new SingleLinkedListNode<T>(element,
					new SingleLinkedListNode<T>());
			head = newHead;
		} else {
			while (!auxHead.next.isNIL()) {
				auxHead = auxHead.next;
			}
			SingleLinkedListNode<T> newNode = new SingleLinkedListNode<T>(element,
					new SingleLinkedListNode<T>());
			newNode.next = auxHead.next;
			auxHead.next = newNode;
		}
	}

	@Override
	public void remove(T element) {

		if (head.data.equals(element)) {
			head = head.next;
		} else {
			SingleLinkedListNode<T> auxNode = head;
			SingleLinkedListNode<T> previous = new SingleLinkedListNode<T>();

			while (!auxNode.isNIL() && !auxNode.data.equals(element)) {
				previous = auxNode;
				auxNode = auxNode.next;
			}

			previous.next = auxNode.next;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray() {

		T[] array = (T[]) new Object[this.size()];

		int count = 0;

		SingleLinkedListNode<T> auxNode = head;

		while (!auxNode.isNIL()) {
			array[count++] = auxNode.getData();
			auxNode = auxNode.next;
		}

		return array;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();

		SingleLinkedListNode<T> auxNode = head;
		builder.append("[");

		while (!auxNode.isNIL()) {

			builder.append(auxNode.toString());

			if (!auxNode.next.isNIL())
				builder.append(", ");

			auxNode = auxNode.next;

		}

		builder.append("]");

		return builder.toString();
	}

}