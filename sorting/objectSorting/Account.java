package sorting.objectSorting;

//Representa uma conta bancaria simples 
public class Account implements Comparable<Account>{
	//O numero da conta. Contas tem o numero como chave e sao indexadas por ele.
	private String number;
	
	//O saldo da conta
	private double balance;

	public int compareTo(Account other){
		return this.number.compareTo(other.number);
	}
	
	//Uma operacao de credito em uma conta
	public void credit(double value){
		this.balance = this.balance + value;
	}
	
	//Uma operacao de debito em uma conta
	public void debit(double value){
		this.balance = this.balance + value;
	}
	
	//Uma operacao de transferencia dessa conta para a conta target
	public void transfer(Account target, double value){
		this.debit(value);
		target.credit(value);
	}
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Account(String number, double balance) {
		super();
		this.number = number;
		this.balance = balance;
	}
}
