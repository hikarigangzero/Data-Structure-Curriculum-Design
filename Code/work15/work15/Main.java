import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Url {
    String path;
    String name;
}

public class Main {
    static int n, m;
    static Url[] url;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextInt();
        scanner.nextLine();

        url = new Url[n];
        for (int i = 0; i < n; i++) {
            url[i] = new Url();
            url[i].path = scanner.next();
            url[i].name = scanner.next();
        }

        for (int i = 0; i < m; i++) {
            String test = scanner.next();
            work(test);
        }

        scanner.close();
    }

    static String checkNum(String str) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                res.append(str.charAt(i));
            } else {
                res.setLength(0);
                return res.toString();
            }
        }

        int k = 0;
        while (k + 1 < res.length() && res.charAt(k) == '0') {
            k++;
        }
        return res.substring(k);
    }

    static void work(String str) {
        for (int i = 0; i < n; i++) {
            List<String> res = get(url[i].path, str);
            if (!res.isEmpty()) {
                System.out.print(url[i].name);
                for (int j = 1; j < res.size(); j++) {
                    System.out.print(" " + res.get(j));
                }
                System.out.println();
                return;
            }
        }
        System.out.println("404");
    }

    static List<String> get(String path, String str) {
        List<String> res = new ArrayList<>();
        res.add(""); // Default value

        int i = 1, j = 1;
        while (i < path.length() && j < str.length()) {
            int u = i + 1, v = j + 1;
            while (u < path.length() && path.charAt(u) != '/') {
                u++;
            }
            while (v < str.length() && str.charAt(v) != '/') {
                v++;
            }

            String a = path.substring(i, u);
            String b = str.substring(j, v);

            if (a.equals("<str>")) {
                res.add(b);
                i = u + 1;
                j = v + 1;
            } else if (a.equals("<int>")) {
                String t = checkNum(b);
                if (!t.isEmpty()) {
                    res.add(t);
                    i = u + 1;
                    j = v + 1;
                } else {
                    res.clear();
                    return res;
                }
            } else if (a.equals("<path>")) {
                res.add(str.substring(j));
                return res;
            } else if (!a.equals(b)) {
                res.clear();
                return res;
            } else {
                i = u + 1;
                j = v + 1;
            }
        }

        if (i - path.length() != j - str.length()) {
            res.clear();
        }

        return res;
    }
}
