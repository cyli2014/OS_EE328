//package com.salary;

import java.util.Arrays;
import java.util.Comparator;

public class SalarySystem {
	public static void main(String[] args) {
		Worker[] company = new Worker[6];
		company[0] = new HourlyWorker("Darwin", 20, 30);
		company[1] = new HourlyWorker("Ada", 21, 50);
		company[2] = new SalariedWorker("Bryce", 30, 30);
		company[3] = new SalariedWorker("Iris", 31, 45);
		company[4] = new HourlyWorker("Teacher", 40, 24);
		company[5] = new SalariedWorker("TA", 25, 10);

		System.out.println("Sort by name:");
		Arrays.sort(company);
		for (int i = 0; i < 6; i++)
			company[i].printPay();
		System.out.println("Sort by salary rate:");
		Arrays.sort(company, new salaryRateCompare());
		for (int i = 0; i < 6; i++)
			company[i].printPay();
		System.out.println("Sort by total salary:");
		Arrays.sort(company, new totalSalaryCompare());
		for (int i = 0; i < 6; i++)
			company[i].printPay();
	}
}

class Worker implements Comparable<Worker> {
	String workerName;
	double workTime = 0;
	double salaryRate = 0;
	double totalSalary = 0;

	public Worker(String name, double rate, double time) {
		workerName = name;
		if (rate < 0 || time < 0)
			System.out.println("Illegal salary rate or working time.");
		else {
			salaryRate = rate;
			workTime = time;
		}

	}

	public void computePay() {
		this.totalSalary = this.salaryRate * this.workTime;
	}

	public void printPay() {
		computePay();
		System.out.println("Worker : " + this.workerName);
		System.out.println("Salary rate: " + this.salaryRate);
		System.out.println("Total salary: " + this.totalSalary + "\n");
	}

	public int compareTo(Worker other) {
		if (this.workerName.equals(other.workerName))
			return 0;

		char[] thisname = this.workerName.toCharArray();
		char[] othername = other.workerName.toCharArray();

		int iterate = thisname.length < othername.length ? thisname.length : othername.length;

		for (int i = 0; i < iterate; i++) {
			if (thisname[i] == othername[i])
				continue;
			else if (thisname[i] < othername[i])
				return -1;
			else
				return 1;
		}
		return (thisname.length < othername.length ? -1 : 1);
	}
}

class HourlyWorker extends Worker {
	static final int timeThreshold = 40;

	public HourlyWorker(String name, double rate, int time) {
		super(name, rate, time);
	}

	public void computePay() {
		if (this.workTime > timeThreshold)
			this.totalSalary = this.salaryRate * timeThreshold + 2 * this.salaryRate * (this.workTime - timeThreshold);
		else
			this.totalSalary = this.salaryRate * this.workTime;
	}

	public void printPay() {
		computePay();
		System.out.println("Hourly worker : " + this.workerName);
		System.out.println("Salary rate: " + this.salaryRate);
		System.out.println("Total salary: " + this.totalSalary + "\n");
	}
}

class SalariedWorker extends Worker {
	static final int effectiveTime = 40;

	public SalariedWorker(String name, double rate, double time) {
		super(name, rate, time);
	}

	public void computePay() {
		this.totalSalary = this.salaryRate * effectiveTime;
	}

	public void printPay() {
		computePay();
		System.out.println("Salaried worker : " + this.workerName);
		System.out.println("Salary rate: " + this.salaryRate);
		System.out.println("Total salary: " + this.totalSalary + "\n");
	}
}

class salaryRateCompare implements Comparator<Worker> {
	public int compare(Worker w1, Worker w2) {
		if (w1.salaryRate < w2.salaryRate)
			return -1;
		else if (w1.salaryRate > w2.salaryRate)
			return 1;
		else
			return 0;
	}
}

class totalSalaryCompare implements Comparator<Worker> {
	public int compare(Worker w1, Worker w2) {
		if (w1.totalSalary < w2.totalSalary)
			return -1;
		else if (w1.totalSalary > w2.totalSalary)
			return 1;
		else
			return 0;
	}
}