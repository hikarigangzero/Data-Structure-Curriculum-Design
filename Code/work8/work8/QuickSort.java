import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class QuickSort {
    public int OneQuickSort(int[] arr, int i, int j) {
        int a=arr[i];
        while(i<j){
            while(i<j&&arr[j]>=a){
                j--;
            }
            arr[i]=arr[j];
            while(i<j&&arr[i]<=a){
                i++;
            }
            arr[j]=arr[i];
        }
        arr[i]=a;
        return i;
    }

    public void QuickSortStart(int []arr,int n){
        int low=0;
        int high=n-1;
        Stack<Integer> stack=new Stack<>();
        int a=OneQuickSort(arr,low,high);
        if(a-1>low){
            stack.push(low);
            stack.push(a-1);
        }
        if(a+1<high){
            stack.push(a+1);
            stack.push(high);
        }
        while(!stack.isEmpty()){
            high=stack.pop();
            low=stack.pop();
            a=OneQuickSort(arr,low,high);
            if(a-1>low){
                stack.push(low);
                stack.push(a-1);
            }
            if(a+1<high){
                stack.push(a+1);
                stack.push(high);
            }
        }
    }

    public static void main(String[] args) {
        //这部分用于生成随机数
        /*
        PrintWriter writer=null;
        Random random=new Random();
        try {
            writer=new PrintWriter("QuickSort.txt");
            for (int i = 0; i < 1000; i++) {
                int a=Math.abs(random.nextInt()%5000);
                writer.print(a+" ");
                if(i%20==19){
                    writer.println();
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
         */
        Scanner scanner=null;
        File file=new File("QuickSort.txt");
        int []arr=new int[1000];
        try {
            scanner=new Scanner(file);
            for (int i = 0; i < 1000; i++) {
                arr[i]= scanner.nextInt();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        QuickSort quickSort=new QuickSort();
        quickSort.QuickSortStart(arr,1000);
        for (int i = 0; i < 1000; i++) {
            System.out.print(arr[i]+" ");
            if(i%20==19){
                System.out.println();
            }
        }
    }
}
