package adt.stack;

import adt.queue.Queue;
import adt.queue.QueueImpl;
import adt.queue.QueueOverflowException;
import adt.queue.QueueUnderflowException;

public class StackUsingQueue<T> implements Stack<T> {

	private int size;
	private Queue<T> queue1;
	private Queue<T> queue2;

	public StackUsingQueue(int size) {
		this.size = size;
		queue1 = new QueueImpl<T>(size);
		queue2 = new QueueImpl<T>(size);
	}

	@Override
	public void push(T element) throws StackOverflowException {
		// CASO A PILHA ESTIVER CHEIA
		if (isFull()) {
			throw new StackOverflowException();
		}

		// ELEMENTOS NULOS NAO SAO PERMITIDOS
		if (element == null) {
			return;
		}

		while (!queue1.isEmpty()) {

			try {
				queue2.enqueue(queue1.dequeue());

			} catch (QueueOverflowException e) {
				throw new StackOverflowException();

			} catch (QueueUnderflowException e) {
				throw new StackOverflowException();
			}
		}

		try {
			queue1.enqueue(element);

		} catch (QueueOverflowException e) {
			throw new StackOverflowException();
		}

		while (!queue2.isEmpty()) {
			try {
				queue1.enqueue(queue2.dequeue());

			} catch (Exception e) {
				throw new StackOverflowException();
			}
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		try {
			return queue1.dequeue();
		} catch (QueueUnderflowException e) {
			throw new StackUnderflowException();
		}
	}

	@Override
	public T top() {
		return !isEmpty() ? queue1.head() : null;
	}

	@Override
	public boolean isEmpty() {
		return queue1.isEmpty();
	}

	@Override
	public boolean isFull() {
		return queue1.isFull();
	}
}
