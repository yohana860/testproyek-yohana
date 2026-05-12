package del.alstrudat;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int n = Integer.parseInt(scanner.nextLine().trim());
    String[][] soldiers = new String[n][4];

    for (int i = 0; i < n; i++) {
      String line = scanner.nextLine().trim();
      String[] parts = line.split("\\s+");
      soldiers[i][0] = parts[0]; // nama
      soldiers[i][1] = parts[1]; // kekuatan
      soldiers[i][2] = parts[2]; // divisi
      soldiers[i][3] = parts[3]; // rank
    }

    Program.solve(n, soldiers);
    scanner.close();
  }
}
