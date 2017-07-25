//package homework1;

import java.io.*;

public class SnakeTable {
	public static void main(String[] args) {
		try {
			System.out.print("Enter table size 1-9, 0 to exit:");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String num = reader.readLine();
			if (!num.matches("[0-9]"))
				System.out.println("please enter a number in the range 0-9.");
			else if (!num.matches("0"))
				printSnake(num);
		} catch (IOException expt) {
			expt.printStackTrace();
		}
	}

	public static void printSnake(String num) {
		int n = Integer.parseInt(num);
		int element = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i % 2 == 1) {
					element = n * (i - 1) + j;
					System.out.print(element + "\t");
				} else {
					element = n * i + 1 - j;
					System.out.print(element + "\t");
				}
			}
			System.out.print("\n");
		}
	}
}
