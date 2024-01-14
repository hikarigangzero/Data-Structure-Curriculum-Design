import java.util.Scanner;
import java.util.Arrays;

class Node implements Comparable<Node> {
    int id;
    int ti;

    Node(int id, int ti) {
        this.id = id;
        this.ti = ti;
    }

    @Override
    public int compareTo(Node other) {
        if (ti == other.ti)
            return Integer.compare(id, other.id);
        return Integer.compare(ti, other.ti);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = 1010;
        int[] key = new int[N];

        int n = scanner.nextInt();
        int m = scanner.nextInt();

        // Initialize key array
        for (int i = 1; i <= n; i++)
            key[i] = i;

        Node[] q1 = new Node[m];
        Node[] q2 = new Node[m];

        for (int i = 0; i < m; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = scanner.nextInt();
            q1[i] = new Node(a, b);
            q2[i] = new Node(a, b + c);
        }

        Arrays.sort(q1);
        Arrays.sort(q2);

        int i = 0, j = 0;
        while (i < m && j < m) {
            if (q1[i].ti < q2[j].ti) {
                int id = q1[i].id;
                for (int k = 1; k <= n; k++) {
                    if (key[k] == id)
                        key[k] = 0;
                }
                i++;
            } else if (q1[i].ti >= q2[j].ti) {
                int id2 = q2[j].id;

                for (int k = 1; k <= n; k++) {
                    if (key[k] == 0) {
                        key[k] = id2;
                        break;
                    }
                }
                j++;
            }
        }

        // Finish returning the remaining keys
        while (j < m) {
            int id2 = q2[j].id;
            for (int k = 1; k <= n; k++) {
                if (key[k] == 0) {
                    key[k] = id2;
                    break;
                }
            }
            j++;
        }

        for (int k = 1; k <= n; k++)
            System.out.print(key[k] + " ");
        System.out.println();

        scanner.close();
    }
}