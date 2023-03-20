package day17;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientDTO {
	private static int firstNum = 100;
	private static int num = 1000;
	private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yy.MM.dd hh:mm:ss");
	
	private String account;
	private String id;
	private String password;
	private String name;
	private long balance;
	private String joinDate;
	
	public ClientDTO() {
		this.joinDate = DTF.format(LocalDateTime.now());
		this.account = firstNum + "-" + num;
		if(num+1 == 1011) {
			firstNum++;
			num = 1000;
		}
		this.account = firstNum + "-" + num++;

	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return account + "\t\t" + id + "\t\t" + password + "\t" + name + "\t" + balance + "\t" +joinDate; 
	}
	
	public void print() {
		System.out.println(account + "\t\t" + id + "\t\t" + password + "\t" + name + "\t" + balance + "\t" +joinDate);
		// TODO Auto-generated method stub
		
	}

	
	
	
	

}
