import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = 35;
        int[][] q = new int[N][N];
        int[][] g = new int[N][N];
        int n, m;
        int temp = 0, c = 0;

        n = scanner.nextInt();
        m = scanner.nextInt();

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                q[i][j] = scanner.nextInt();
                g[i][j] = q[i][j]; // 将数组备份，消除行之后，无法用数组q判断来消除列
            }
        }

        for (int i = 1; i <= n; i++) { // 先将每一行应消除的消除
            for (int j = 1; j <= m; j++) {
                if (temp != q[i][j]) { // 如果不是原来的数字，计数改为1，temp存储新的数字
                    temp = q[i][j]; // temp存储当前遇到的数字
                    c = 1;
                } else {
                    c++; // 仍为这个数字，计数加一
                    if (c >= 3) { // 计数超过三时擦除数字
                        q[i][j] = 0;
                        q[i][j - 1] = 0;
                        q[i][j - 2] = 0;
                    }
                }
            }
            temp = 0;
            c = 0;
        }

        for (int j = 1; j <= m; j++) { // 将每一列应消除的消除，操作原理同上
            for (int i = 1; i <= n; i++) {
                if (temp != g[i][j]) {
                    temp = g[i][j];
                    c = 1;
                } else {
                    c++;
                    if (c >= 3) {
                        q[i][j] = 0;
                        q[i - 1][j] = 0;
                        q[i - 2][j] = 0;
                    }
                }
            }
            temp = 0;
            c = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++)
                System.out.print(q[i][j] + " ");
            System.out.println();
        }

        scanner.close();
    }
}