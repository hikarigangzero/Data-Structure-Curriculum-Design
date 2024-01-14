import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HeapSort {
    final static int Num=1000;
    public void Heap(int []arr,int low,int high){
        int a=arr[low];
        int i=low;
        int j=2*i;
        while(j<=high){
            if(j+1<=high&&arr[j]<arr[j+1]){
                j++;
            }
            if(a>=arr[j]){
                break;
            }
            arr[i]=arr[j];
            i=j;
            j=2*i;
        }
        arr[i]=a;
    }

    public void StartHeap(int []arr,int n){
        for (int i = n/2; i >0 ; i--) {
            Heap(arr,i,n);
        }
        for (int i = n; i > 1 ; i--) {
            int a=arr[1];
            arr[1]=arr[i];
            arr[i]=a;
            Heap(arr,1,i-1);
        }
    }

    public void Print(int []arr,int n){
        for (int i = 1; i <= n; i++) {
            System.out.print(arr[i]+" ");
            if(i%20==0){
                System.out.println();
            }
        }
    }


    public static void main(String[] args) {
        int []arr=new int[Num+1];
        Scanner scanner=null;
        File file=new File("HeapSort.txt");
        try {
            scanner=new Scanner(file);
            for (int i = 1; i <= Num; i++) {
                arr[i]=scanner.nextInt();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        HeapSort heapSort=new HeapSort();
        heapSort.StartHeap(arr,Num);
        heapSort.Print(arr,Num);
    }
}
