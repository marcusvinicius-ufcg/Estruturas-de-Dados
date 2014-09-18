package adt.hashtable;

import java.util.LinkedList;

public class HashtableClosedAddressImpl<T> extends AbstractHashtable<T, LinkedList<T>> {

	protected HashFunctionClosedAddressMethod method; 
	
	// DO NOT DELETE THIS CONSTRUCTOR. ADJUST IT.
	@SuppressWarnings("unchecked")
	public HashtableClosedAddressImpl(int size, HashFunctionClosedAddressMethod method) {
		super(getPrimeAbove(size));
		table = new LinkedList[size];
		this.method = method;
		
		// Adjust your constructor here
		// The length of the internal table must be the immediate prime number greater than 
		// the given size. For example, if size=10 then the length must be 11. If size=20, the length 
		// must be 23. You may use the method getPrimeAbove(int size) but you must implement it.
		
		switch (method) {
		//Implementado apenas o metodo de Divisao como pede o Roteiro
		case DIVISION:
			hashFunction = new HashFunctionDivisionMethodImpl<T>(this);
			break;
		
		case MULTIPLICATION:
			//Nao Implementado
			break;
		
		default:
			break;
		}
		
		for(int i = 0 ; i < size ; i++){
            table[i] = new LinkedList<T>();
		}
	}
	
	//AUXILIARY
	/**
	 * It returns the prime number that is closest (and greater) to the given number.  
	 */
	private static int getPrimeAbove(int number){
		int result = number;
		while(!Util.isPrime(result)){
			result = result + 1;
		}
		return result;
	}
			
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void insert(T element) {
		
		if (elements == capacity()){
			throw new HashtableOverflowException();
		}
		
		Integer index   = ((HashFunctionDivisionMethodImpl<T>) hashFunction).hash(element);
		LinkedList list = (LinkedList)table[index];
		
		if(search(element) == null && list.size() > 1){
			COLLISIONS++;
		}
		
		list.add(element);
		table[index] = list;
		this.elements++;
		
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void remove(T element) {
		Integer index   = ((HashFunctionDivisionMethodImpl<T>) hashFunction).hash(element);
		LinkedList list = (LinkedList)table[index];
		list.remove(element);
		table[index] = list;
		this.elements--;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public T search(T element) {
		Integer index   = ((HashFunctionDivisionMethodImpl<T>) hashFunction).hash(element);
		
		LinkedList list = (LinkedList) table[index];
		
		if (list.contains(element)){
			index = list.indexOf(element);
			return (T) list.get(index);
		}else{
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int indexOf(T element) {
		Integer index   = ((HashFunctionDivisionMethodImpl<T>) hashFunction).hash(element);
		LinkedList list = (LinkedList)table[index];
		
		if (list.contains(element)){
			index = list.indexOf(element);
			return index;
		}else{
			return -1;
		}
	}
}
