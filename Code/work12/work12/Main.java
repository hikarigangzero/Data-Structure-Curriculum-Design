import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Window {
    int id;
    int x1, y1, x2, y2;
    int layer;

    Window(int id, int x1, int y1, int x2, int y2, int layer) {
        this.id = id;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.layer = layer;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        List<Window> windows = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            int x1 = scanner.nextInt();
            int y1 = scanner.nextInt();
            int x2 = scanner.nextInt();
            int y2 = scanner.nextInt();
            windows.add(new Window(id, x1, y1, x2, y2, i));
        }

        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            Collections.sort(windows, Comparator.comparingInt(w -> w.layer));

            int top = -1;
            for (int j = 0; j < n; j++) {
                if ((x >= windows.get(j).x1 && x <= windows.get(j).x2) && (y <= windows.get(j).y1 && y >= windows.get(j).y2)) {
                    top = j;
                    break;
                }
            }

            if (top != -1) {
                int curLayer = windows.get(top).layer;
                for (int j = 0; j < n; j++) {
                    if (windows.get(j).layer < curLayer) {
                        windows.get(j).layer++;
                    }
                }
                windows.get(top).layer = 0;
            }
        }

        Collections.sort(windows, Comparator.comparingInt(w -> w.layer));
        for (Window window : windows) {
            System.out.print(window.id + " ");
        }
        System.out.println();
        scanner.close();
    }
}
