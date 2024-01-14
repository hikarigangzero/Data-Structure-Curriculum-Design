import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
        //这部分是生成随机数的部分
        /*
        File file =new File("Data.txt");
        Random random=new Random();
        PrintWriter pw=null;
        try {
            pw=new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 25; i++) {
            int n=Math.abs(random.nextInt(1000));
            pw.print(n+" ");
        }
        pw.println();
        pw.close();
         */
        Test();
    }

    static public void Test(){
        BTreeNode bTreeNode=new BTreeNode(3);
        File  file=new File("Data.txt");
        Scanner scanner=null;
        try {
            scanner=new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNext()){
            int a=scanner.nextInt();
            bTreeNode=bTreeNode.insert(a);
        }
        bTreeNode.print();
        System.out.println(bTreeNode.search(25).values);
        System.out.println(bTreeNode.search(26).values);
        bTreeNode.delete(908);
        bTreeNode.print();
    }
}
