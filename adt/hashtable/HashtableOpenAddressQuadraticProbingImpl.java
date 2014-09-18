package adt.hashtable;

public class HashtableOpenAddressQuadraticProbingImpl<T> extends AbstractHashtable<T, Object> {

	private static final DELETED DELETED = new DELETED();
	// DO NOT DELET THIS CONSTRUCTOR. ADJUST IT.
	public HashtableOpenAddressQuadraticProbingImpl(int size,
			HashFunctionClosedAddressMethod method, int c1, int c2) {
		super(size);
		this.table = new Object[size];
		// TODO Adjust your constructor here
		// The length of the internal table must be given size
		// the hash function must be an implementation of linear probing.
		hashFunction = new HashFunctionQuadraticProbingImpl<T>(this, method,c1, c2);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(T element) {
		if (elements == capacity()) {
			throw new HashtableOverflowException();
		}
		if (element == null) {
			return;
		}

		if (search(element) == null) {
			Integer index = 0;
			while (true) {
				Integer indexAux = ((HashFunctionQuadraticProbingImpl<T>) hashFunction).hash(element, index);
				if (table[indexAux] == null || table[indexAux].equals(DELETED)) {
					table[indexAux] = element;
					break;
				} else {
					index++;
				}
			}
			elements++;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(T element) {
		if (element == null || search(element) == null) {
			return;
		}
		Integer index = 0;
		while (true) {
			Integer indexAux = ((HashFunctionQuadraticProbingImpl<T>) hashFunction).hash(element, index);
			if (table[indexAux].equals(element)) {
				table[indexAux] = DELETED;
				break;
			} else {
				index++;
			}
		}
		elements--;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T search(T element) {
		if (element == null) {
			return null;
		}
		Integer index = 0;
		while (true) {
			Integer indexAux = ((HashFunctionQuadraticProbingImpl<T>) hashFunction).hash(element, index);
			if (table[indexAux] == null || table[indexAux].equals(element)) {
				return (T) table[indexAux];
			} else {
				index++;
			}
			//Condicao de parada
			if (index == capacity()) {
				return null;
			}
		}
	}

	@Override
	public int indexOf(T element) {
		Integer index = 0;
		while (true) {

			@SuppressWarnings("unchecked")
			Integer indexAux = ((HashFunctionQuadraticProbingImpl<T>) hashFunction).hash(element, index);
			if (table[indexAux] == null) {
				return -1;
			} else if (table[indexAux].equals(element)) {
				return indexAux;
			} else {
				index++;
			}
			//Condicao de parada
			if (index == capacity()) {
				return -1;
			}
		}
	}
}
