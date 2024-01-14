import java.util.*;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Map<Integer,Integer> h = new HashMap<>();
        for(int i = 0;i<n;i++){
            int t = sc.nextInt();
            h.put(t,h.getOrDefault(t,0)+1);
        }
        // java中的所有hashMap都是Map.Entry,所以把它转成list，然后排序
        List<Map.Entry<Integer,Integer>> ml = new ArrayList<Map.Entry<Integer,Integer>>(h.entrySet());
        Collections.sort(ml,new Comparator<Map.Entry<Integer,Integer>>(){
            @Override
            public int compare(Map.Entry<Integer,Integer> o1,Map.Entry<Integer,Integer> o2){
                if(o1.getValue() == o2.getValue()){
                    return o1.getKey().compareTo(o2.getKey());
                }else{
                    return o2.getValue().compareTo(o1.getValue());
                }

            }
        });
        for(Map.Entry<Integer,Integer> t:ml){
            System.out.println(t.getKey()+" "+t.getValue());
        }
    }
}


