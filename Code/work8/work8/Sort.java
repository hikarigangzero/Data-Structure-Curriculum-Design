import java.io.*;
import java.util.*;

public class Sort {
    //阅读文件的函数
    public void readFile() {
        File []files=new File[10];
        int []arr=new int[50000];
        for (int i = 0; i < 10; i++) {
            String sStr="./file"+(i+1)+"的排序";
            File folder=new File(sStr);
            if(!folder.exists()){
                folder.mkdir();
            }
        }

        String [][]str=new String[10][8];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 8; j++) {
                str[i][j]="./file"+(i+1)+"的排序/第"+(j+1)+"种方法.txt";
            }
        }
        File []file=new File[10];
        for (int i = 0; i < 10; i++) {
            files[i]=new File("file"+(i+1)+".txt");
            Scanner scanner= null;
            try {
                scanner = new Scanner(files[i]);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            for (int j = 0; j < 50000; j++) {
                arr[j]=scanner.nextInt();
            }
            scanner.close();
            System.out.println("file"+(i+1)+".txt各种方法的排序时间:");
            for (int j = 0; j < 8; j++) {
                switch (j){
                    case 0:
                        insertSort(arr,str[i][j]);
                        break;
                    case 1:
                        shellSort(arr,str[i][j]);
                        break;
                    case 2:
                        bubbleSort(arr,str[i][j]);
                        break;
                    case 3:
                        quickSort(arr,str[i][j]);
                        break;
                    case 4:
                        selectSort(arr,str[i][j]);
                        break;
                    case 5:
                        heapSort(arr,str[i][j]);
                        break;
                    case 6:
                        mergeSort(arr,str[i][j]);
                        break;
                    case 7:
                        radixSort(arr,str[i][j]);
                        break;
                    default:
                        break;
                }
            }
        }
    }


    //直接插入排序
    public void insertSort(int[] arr,String str) {
        int []brr=new int[arr.length];
        System.arraycopy(arr,0,brr,0,arr.length);
        long start=System.currentTimeMillis();
        for (int i = 1; i < brr.length; i++) {
            int temp = brr[i];
            int j = i - 1;
            while (j >= 0 && brr[j] > temp) {
                brr[j + 1] = brr[j];
                j--;
            }
            brr[j + 1] = temp;
        }
        long end=System.currentTimeMillis();
        File file=new File(str);
        PrintWriter pw=null;
        try {
            pw = new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < brr.length; i++) {
            pw.print(brr[i]+" ");
            if(i%20==19){
                pw.println();
            }
        }
        pw.close();
        System.out.println("直接插入排序用时："+(end-start)+"ms");
    }

    //希尔排序
    public void shellSort(int[] arr,String str) {
        int []brr=new int[arr.length];
        System.arraycopy(arr,0,brr,0,arr.length);
        long start=System.currentTimeMillis();
        int n = brr.length;
        int gap = n / 2;
        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int temp = brr[i];
                int j = i;
                while (j >= gap && brr[j - gap] > temp) {
                    brr[j] = brr[j - gap];
                    j -= gap;
                }
                brr[j] = temp;
            }
            gap /= 2;
        }
        long end=System.currentTimeMillis();
        File file=new File(str);
        PrintWriter pw=null;
        try {
            pw = new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < brr.length; i++) {
            pw.print(brr[i]+" ");
            if(i%20==19){
                pw.println();
            }
        }
        pw.close();
        System.out.println("希尔排序用时："+(end-start)+"ms");
    }

    //冒泡排序
    public void bubbleSort(int[] arr,String str) {
        int []brr=new int[arr.length];
        System.arraycopy(arr,0,brr,0,arr.length);
        long start=System.currentTimeMillis();
        for (int i = 0; i < brr.length; i++) {
            for (int j = 0; j < brr.length-1; j++) {
                if(brr[j]>brr[j+1]){
                    int temp=brr[j];
                    brr[j]=brr[j+1];
                    brr[j+1]=temp;
                }
            }
        }
        long end=System.currentTimeMillis();
        File file=new File(str);
        PrintWriter pw=null;
        try {
            pw = new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < brr.length; i++) {
            pw.print(brr[i]+" ");
            if(i%20==19){
                pw.println();
            }
        }
        pw.close();
        System.out.println("冒泡排序用时："+(end-start)+"ms");
    }

    //快速排序
    public void quickSort(int[] arr,String str) {
        int []brr=new int[arr.length];
        System.arraycopy(arr,0,brr,0,arr.length);
        long start=System.currentTimeMillis();
        QuickSort qs=new QuickSort();
        qs.QuickSortStart(brr,brr.length);
        long end=System.currentTimeMillis();
        File file=new File(str);
        PrintWriter pw=null;
        try {
            pw = new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < brr.length; i++) {
            pw.print(brr[i]+" ");
            if(i%20==19){
                pw.println();
            }
        }
        pw.close();
        System.out.println("快速排序用时："+(end-start)+"ms");
    }

    //选择排序
    public void selectSort(int[] arr,String str) {
        int []brr=new int[arr.length];
        System.arraycopy(arr,0,brr,0,arr.length);
        long start=System.currentTimeMillis();
        for (int i = 0; i < brr.length; i++) {
            for (int j = i+1; j < brr.length; j++) {
                if(brr[i]>brr[j]){
                    int temp=brr[i];
                    brr[i]=brr[j];
                    brr[j]=temp;
                }
            }
        }
        long end=System.currentTimeMillis();
        File file=new File(str);
        PrintWriter pw=null;
        try {
            pw = new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < brr.length; i++) {
            pw.print(brr[i]+" ");
            if(i%20==19){
                pw.println();
            }
        }
        pw.close();
        System.out.println("选择排序用时："+(end-start)+"ms");
    }

    //堆排序
    public void heapSort(int[] arr,String str) {
        int []brr=new int[arr.length+1];
        System.arraycopy(arr,0,brr,1,arr.length);
        long start=System.currentTimeMillis();
        HeapSort hs=new HeapSort();
        hs.StartHeap(brr,arr.length);
        long end=System.currentTimeMillis();
        File file=new File(str);
        PrintWriter pw=null;
        try {
            pw = new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 1; i <= arr.length; i++) {
            pw.print(brr[i]+" ");
            if(i%20==0){
                pw.println();
            }
        }
        pw.close();
        System.out.println("堆排序用时："+(end-start)+"ms");
    }

    //归并排序
    public void mergeSort(int[] arr,String str) {
        int []brr=new int[arr.length];
        System.arraycopy(arr,0,brr,0,arr.length);
        long start=System.currentTimeMillis();
        MergeSort ms=new MergeSort();
        ms.MSort(brr,brr.length);
        long end=System.currentTimeMillis();
        File file=new File(str);
        PrintWriter pw=null;
        try {
            pw = new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < brr.length; i++) {
            pw.print(brr[i]+" ");
            if(i%20==19){
                pw.println();
            }
        }
        pw.close();
        System.out.println("归并排序用时："+(end-start)+"ms");
    }

    //基数排序
    public void radixSort(int[] arr,String str) {
        int []brr=new int[arr.length];
        System.arraycopy(arr,0,brr,0,arr.length);
        long start=System.currentTimeMillis();
        Queue<Integer>[]queue=new Queue[10];
        for (int i = 0; i < 10; i++) {
            queue[i]=new LinkedList<>();
        }
        int radix=1;
        for (int i = 1; i <= 5; i++) {
            radix*=10;
            for (int j = 0; j < brr.length; j++) {
                int m=(brr[j]%radix)/(radix/10);
                queue[m].offer(brr[j]);
            }
            for (int j = 0,m = 0; m < 10; m++) {
                while (!queue[m].isEmpty()){
                    brr[j]=queue[m].poll();
                    j++;
                }
            }
        }
        long end=System.currentTimeMillis();
        File file=new File(str);
        PrintWriter pw=null;
        try {
            pw = new PrintWriter(file);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < brr.length; i++) {
            pw.print(brr[i]+" ");
            if(i%20==19){
                pw.println();
            }
        }
        pw.close();
        System.out.println("基数排序用时："+(end-start)+"ms");
    }

    public static void main(String[] args) {
        //这一部分是用来生成测试数据的代码
        /*
        Random rand = new Random();
        String []str=new String[10];
        for(int i=0;i<10;i++){
            str[i]="file"+(i+1)+".txt";
        }
        try {
            for (int i = 0; i < 10; i++) {
                File file = new File(str[i]);
                PrintWriter pw =new PrintWriter(file);
                if(i==1){
                    continue;
                }
                for (int j = 0; j < 50000; j++) {
                    int num=Math.abs(rand.nextInt()%100000);
                    pw.print(num+" ");
                    if(j%20==19){
                        pw.println();
                    }
                }
                pw.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Scanner sc=new Scanner(new File("file1.txt"));
            int []arr=new int[50000];
            for (int i = 0; i < 50000; i++) {
                arr[i]=sc.nextInt();
            }
            PrintWriter pw=new PrintWriter(new File("file2.txt"));
            for (int i = 49999; i >=0 ; i--) {
                pw.print(arr[i]+" ");
                if(i%20==19){
                    pw.println();
                }
            }
            pw.close();
            sc.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }*/


        Sort sort=new Sort();
        sort.readFile();

        /*File file=new File("file2.txt");

        FileOutputStream out=null;
        BufferedOutputStream bout=null;
        try {
            out=new FileOutputStream(file);
            bout=new BufferedOutputStream(out);
            String []arr= new String[50000];
            for (int i = 49999; i >= 0; i++) {
                arr[i]= String.valueOf(i);
            }
            byte[]str=arr.getBytes();
            bout.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        /*PrintWriter pw=null;
        try {
            pw=new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (int i = 50000; i >0 ; i--) {
            pw.print(i+" ");
            if(i%20==19){
                pw.println();
            }
        }*/
    }
}
