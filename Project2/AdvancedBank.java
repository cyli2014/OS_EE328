//package com.bank;

import java.io.*;

public class AdvancedBank {
	public static void main(String[] args) {
		String command;
		String command1;
		String command2;
		String command3;
		String command4;
		Account[] bank = new Account[9];
		Account currentAccount = bank[0];
		int count = 0;
		try {
			do {
				System.out.print("\n");
				System.out.println("Press 1 -- create a new account;");
				System.out.println("Press 2 -- log in;");
				System.out.println("Press 3 -- withdraw;");
				System.out.println("Press 4 -- deposit;");
				System.out.println("Press 5 -- check balance;");
				System.out.println("Press 6 -- check the month;");
				System.out.println("Press 7 -- list all accounts created;");
				System.out.println("Press 8 -- quit.");
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				command = reader.readLine();
				if (!command.matches("[1-8]")) // Reject illegal input
					System.out.println("\nPlease enter a number in the range 1-8.");
				else {
					if (command.equals("1")) {
						System.out.println("\nPress 1 to create a Checking Account;");
						System.out.println("Press 2 to create a Saving Account.");
						BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
						command1 = reader1.readLine();
						if (!command1.matches("[1-2]"))
							System.out.println("\nPlease enter a number in the range 1-2.");
						else {
							if (command1.equals("1")) {
								bank[count] = new CheckingAccount(50);
								currentAccount = bank[count];
								count++;
							} else {
								bank[count] = new SavingAccount(0);
								currentAccount = bank[count];
								count++;
							}
						}
					}
					if (command.equals("2")) {
						System.out.print("\nEnter the account number:");
						BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
						command2 = reader2.readLine();
						if (!command2.matches("[1-9]")) {
							System.out.println("\nPlease enter a number in the range 1-9.");
						} else {
							int n = Integer.parseInt(command2);
							if (n > count)
								System.out.println("\nAccount doesn't exist.");
							else {
								currentAccount = bank[n - 1];
							}
						}
					}
					if (command.equals("3")) {
						if (count == 0)
							System.out.println("\nPlease log in first.");
						else {
							System.out.print("\nHow much do you want to withdraw?");
							BufferedReader reader3 = new BufferedReader(new InputStreamReader(System.in));
							command3 = reader3.readLine();
							if (!command3.matches("^\\d+(\\.\\d+)?")) {
								System.out.println("\nPlease input a number.");
							} else {
								double money = Double.parseDouble(command3);
								currentAccount.withdraw(money);
								currentAccount.print();
							}
						}
					}
					if (command.equals("4")) {
						if (count == 0)
							System.out.println("\nPlease log in first.");
						else {
							System.out.print("\nHow much do you want to deposit?");
							BufferedReader reader4 = new BufferedReader(new InputStreamReader(System.in));
							command4 = reader4.readLine();
							if (!command4.matches("^\\d+(\\.\\d+)?"))
								System.out.println("\nPlease input a number.");
							else {
								double money = Double.parseDouble(command4);
								currentAccount.deposit(money);
								currentAccount.print();
							}
						}
					}
					if (command.equals("5")) {
						if (count == 0)
							System.out.println("\nPlease log in first.");
						else
							currentAccount.print();
					}
					if (command.equals("6")) {
						if (count == 0)
							System.out.println("\nPlease log in first.");
						else {
							currentAccount.endMonth();
							currentAccount.print();
						}
					}
					if (command.equals("7")) {
						if (count == 0)
							System.out.println("\nNo account created.");
						else {
							for (int i = 0; i < count; ++i)
								bank[i].print();
						}
					}
				}
			} while (!command.equals("8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Account {
	static int total = 0;
	double balance = 0;
	int id = 1;
	int transtime = 0;

	public Account(double init_balance) {
		balance = init_balance;
		total = total + 1;
		id = total;
	}

	public void deposit(double money) {
		transtime = transtime + 1;
		balance = balance + money;
	}

	public void withdraw(double money) {
		if (balance >= money) {
			transtime = transtime + 1;
			balance = balance - money;
			System.out.println("Successfully withdrawn.");
		} else
			System.out.println("No enough balance.");
	}

	public void endMonth() {
	}

	public void print() {
	}
}

class CheckingAccount extends Account {
	public CheckingAccount(double init_balance) {
		super(init_balance);
	}

	public void endMonth() {
		if (balance < 50) {
			try {
				System.out.print("Set your balance to $50 to avoid monthly fee? Press \"Y\" if yes: ");
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				String num = reader.readLine();
				if (num.equals("Y"))
					balance = 50;
				else {
					if (balance < 2)
						balance = 0;
					else
						balance = balance - 2;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void print() {
		System.out.println("My checking number is " + id + ", and my balance is $" + balance + ".");
	}
}

class SavingAccount extends Account {
	static final double interest = 0.05;

	public SavingAccount(double init_balance) {
		super(init_balance);
	}

	public void endMonth() {
		balance = balance * (1 + interest);
		if (transtime > 3) {
			if (balance < 3)
				balance = 0;
			else
				balance = balance - 3;
		}
		System.out.println("There are " + transtime + " transactions this month.");
		transtime = 0;
	}

	public void print() {
		System.out.println("My saving number is " + id + ", and my balance is $" + balance + ".");
	}
}
