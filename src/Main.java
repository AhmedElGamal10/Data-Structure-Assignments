import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		int n;
		int temp = 0, cnt = 0;
		boolean[] visited = new boolean[1000];
		Scanner scan = new Scanner(System.in);
		n = scan.nextInt();
		for (int i = 0; i < n; i++) {
			temp = scan.nextInt();
			visited[temp] = true;
		}
		for (int i = 1; i <= temp; i++) {
			if (!visited[i])
				cnt++;
			else
				cnt = 0;

			if (cnt == 15) {
				System.out.println(i);
				System.exit(0);
			}
		}
		System.out.println(temp);
	}

}
