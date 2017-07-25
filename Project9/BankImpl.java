public class BankImpl implements Bank {

	private int[] available;
	private int[][] max;
	private int[][] allocation;
	private int[][] need;
	private int m; // m 个元素
	private int n; // 进程个数

	public BankImpl(int[] initialResources) {

		this.m = initialResources.length;
		this.n = TestHarness.n;
		this.available = new int[this.m];
		System.arraycopy(initialResources, 0, this.available, 0, this.m);
		this.max = new int[this.n][this.m];
		this.allocation = new int[this.n][this.m];
		this.need = new int[this.n][this.m];
	}

	@Override
	public void addCustomer(int customerNumber, int[] maximumDemand) {
		// TODO Auto-generated method stub
		System.arraycopy(maximumDemand, 0, max[customerNumber], 0, this.m);
		for (int j = 0; j < this.m; j++)
			this.need[customerNumber][j] = this.max[customerNumber][j] - this.allocation[customerNumber][j];
	}

	@Override
	public void getState() {
		// TODO Auto-generated method stub
		System.out.println("Available vector:");
		for (int i = 0; i < this.m; i++)
			System.out.print(this.available[i] + "\t");

		System.out.println("\nMax matrix:");
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++)
				System.out.print(this.max[i][j] + "\t");
			System.out.println("");
		}

		System.out.println("Allocation matrix:");
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++)
				System.out.print(this.allocation[i][j] + "\t");
			System.out.println("");
		}

		System.out.println("Need matrix:");
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.m; j++)
				System.out.print(this.need[i][j] + "\t");
			System.out.println("");
		}

	}

	@Override
	public boolean requestResources(int customerNumber, int[] request) {
		// TODO Auto-generated method stub
		if (customerNumber > this.n)
			System.out.println("Input Error!");
		for (int i = 0; i < this.m; i++) {
			if (request[i] > this.need[customerNumber][i])
				return false;
			if (request[i] > this.available[i])
				return false;
		}

		for (int i = 0; i < this.m; i++) {
			this.available[i] -= request[i];
			this.allocation[customerNumber][i] += request[i];
			this.need[customerNumber][i] -= request[i];
		}

		if (safe())
			return true;
		else {
			for (int i = 0; i < this.m; i++) {
				this.available[i] += request[i];
				this.allocation[customerNumber][i] -= request[i];
				this.need[customerNumber][i] += request[i];
			}
			return false;
		}
	}

	@Override
	public void releaseResources(int customerNumber, int[] release) {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.m; i++) {
			this.available[i] += release[i];
			this.allocation[customerNumber][i] -= release[i];
			this.need[customerNumber][i] += release[i];
		}

	}

	public boolean safe() {

		int cnt = 0;
		int i = 0;
		int j = 0;
		int k = 0;
		int[] work = new int[this.m];
		System.arraycopy(this.available, 0, work, 0, this.m);

		boolean[] finish = new boolean[this.n];
		for (i = 0; i < this.n; i++)
			finish[i] = false;
		// ************************
		for (i = 0; i < this.n; i++) {
			cnt = 0;
			for (j = 0; j < this.m; j++)
				if (finish[i] == false && this.need[i][j] <= work[j])
					cnt++;
			if (cnt == this.m) {
				for (k = 0; k < this.m; k++)
					work[k] += this.allocation[i][k];
				finish[i] = true;
				i = -1; // 从头开始循环
			}
		}
		for (i = 0; i < this.n; i++)
			if (!finish[i])
				return false;

		return true;
	}

	public static void main(String[] args){

	}
}
