package adt.hashtable;

public class HashFunctionQuadraticProbingImpl<T> implements HashFunctionOpenAddress {

	
	protected Hashtable<T> hashtable;
	protected HashFunctionClosedAddressMethod method;
	protected int c1;
	protected int c2;
	
	// The auxiliary hash function used by quadratic probing.
	protected HashFunctionClosedAddress originalHashFunction;
	
	public HashFunctionQuadraticProbingImpl(Hashtable<T> hashtable,
			HashFunctionClosedAddressMethod method, //the method of the original hash function
			int c1, int c2) {
		super();
		this.hashtable = hashtable;
		this.method = method;
		this.c1 = c1;
		this.c2 = c2;
		
		//Adjust your constructor to initialize the attribute originalHashFunction with the correct hash function
		
		switch (method) {
		//Implementado apenas o metodo de Divisao como pede o Roteiro
		case DIVISION:
			originalHashFunction = new HashFunctionDivisionMethodImpl<T>(hashtable);
			break;
		
		case MULTIPLICATION:
			//Nao Implementado
			break;
		
		default:
			break;
		}
	}
	
	/**
	 * The hash function might use the length of the hashtable. This can be captured through the method
	 * capacity of the hashtable.
	 */
	@Override
	public int hash(Object element, int probe) {
		Integer m = hashtable.capacity();
		return (hash(element) + c1*probe + c2*(probe^2)) % m;
	}
	
	public int hash(Object element){
		Integer m = hashtable.capacity();
		return Math.abs(element.hashCode()) % m;
	}
}
