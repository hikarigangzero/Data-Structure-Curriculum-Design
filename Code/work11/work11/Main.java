import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long N, M, sum = 0, flag = 0, z = 0;
        N = scanner.nextLong();
        long[] ticket = new long[1000010];
        long[] goods = new long[1000010];

        for (long i = 0; i < N; i++) {
            ticket[(int) i] = scanner.nextLong();
        }

        M = scanner.nextLong();

        for (long i = 0; i < M; i++) {
            goods[(int) i] = scanner.nextLong();
        }

        Arrays.sort(ticket, 0, (int) N);
        Arrays.sort(goods, 0, (int) M);

        while (z < M && flag < N && goods[(int) z] < 0 && ticket[(int) flag] < 0) {
            sum += goods[(int) z++] * ticket[(int) flag++];
        }

        while (z < M && goods[(int) z] < 0) {
            z++;
        }

        while (flag < N && ticket[(int) flag] < 0) {
            flag++;
        }

        while (--M >= z && --N >= flag) {
            sum += goods[(int) M] * ticket[(int) N];
        }

        System.out.println(sum);
        scanner.close();
    }
}
