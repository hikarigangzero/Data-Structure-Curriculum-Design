public class MergeSort {
    public void MergeSort(int[]arr,int high,int low,int mid){
        int[]temp=new int[high+1];
        int i=low;
        int j=mid+1;
        int k=0;
        while(i<=mid&&j<=high) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while(i<=mid){
            temp[k++]=arr[i++];
        }
        while(j<=high){
            temp[k++]=arr[j++];
        }
        for (k=0,i=low; i <= high; i++,k++) {
            arr[i]=temp[k];
        }
    }

    public void MSort(int []arr,int n){
        for (int lin = 1; lin <= n; lin*=2) {
            int i=0;
            while(i+2*lin-1<n){
                MergeSort(arr,i+2*lin-1,i,i+lin-1);
                i+=2*lin;
            }
            if(i+lin<n){
                MergeSort(arr,n-1,i,i+lin-1);
            }
        }
    }

    public static void main(String[] args) {

    }
}
