import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class MiMap {
    static class MyMap{
        int x;
        int y;
        int value;
        boolean visit;
    }
    static public MyMap[][]arr;

    static private int x1,y1,x2,y2;

    static public Stack<MyMap>temp=new Stack<>();

    public void Judge(int x,int y,int m,int n){
        MyMap [][]brr=new MyMap[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                brr[i][j]=new MyMap();
            }
        }
        for (int i = 0; i < m; i++) {
            System.arraycopy(arr[i], 0, brr[i], 0, n);
        }
        temp.push(brr[x][y]);
        brr[x][y].visit=true;
        while(!temp.isEmpty()&&((x!=x2)||(y!=y2))){
            if((y+1)<n&&brr[x][y+1].value==0&& !brr[x][y+1].visit){
                y++;
                brr[x][y].visit=true;
                temp.push(brr[x][y]);
            }
            else if((x+1)<m&&brr[x+1][y].value==0&& !brr[x+1][y].visit){
                x++;
                brr[x][y].visit=true;
                temp.push(brr[x][y]);
            }
            else if((y-1)>=0&&brr[x][y-1].value==0&& !brr[x][y-1].visit){
                y--;
                brr[x][y].visit=true;
                temp.push(brr[x][y]);
            }
            else if((x-1)>=0&&brr[x-1][y].value==0&& !brr[x-1][y].visit){
                x--;
                brr[x][y].visit=true;
                temp.push(brr[x][y]);
            }
            else{
                temp.pop();
                MyMap lin2= temp.peek();
                x=lin2.x;
                y=lin2.y;
            }
        }
    }

    public void Set(int m,int n){
        while(!temp.isEmpty()){
            MyMap t=temp.pop();
            arr[t.x][t.y].value=2;
        }
    }

    public void Print(int m,int n){
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(arr[i][j].value==0) {
                    System.out.print("✳ ");
                }
                else if(arr[i][j].value==1){
                    System.out.print("✲ ");
                }
                else{
                    System.out.print("✱ ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int m,n;
        File file=new File("迷宫样例.txt");
        FileInputStream in=null;
        DataInputStream din=null;

        try {
            Scanner scanner=new Scanner(file);
            m=scanner.nextInt();
            n=scanner.nextInt();
            arr=new MyMap[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    arr[i][j]=new MyMap();
                    arr[i][j].x=i;
                    arr[i][j].y=j;
                    arr[i][j].value=scanner.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        MiMap map=new MiMap();
        Scanner scanner=new Scanner(System.in);
        x1=scanner.nextInt();
        y1=scanner.nextInt();
        x2=scanner.nextInt();
        y2=scanner.nextInt();
        map.Judge(x1,y1,m,n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(arr[i][j].value+" ");
            }
            System.out.println();
        }
        System.out.println("----------");
        map.Set(m,n);
        map.Print(m,n);
        scanner.close();
    }
}
/*
0 0 24 24
 */