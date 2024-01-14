
import java.util.*;

public class Main {
    static Scanner scanner=new Scanner(System.in);
    static class Tu{
        class Next{
            int id;
            int weight;
            Next next;
        }
        class Node extends Next{
            int data;
        }

        Node []arr;
        private int NodeNum;
        private int LineNum;

        Tu(int n,int m){
            NodeNum=n;
            LineNum=m;
            arr=new Node[n];
        }

        public void Create(){
            for (int i = 0; i < NodeNum; i++) {
                arr[i]=new Node();
                arr[i].data=i+1;
            }
            for (int i = 0; i < LineNum; i++) {
                int a=scanner.nextInt();
                int b=scanner.nextInt();
                int c=scanner.nextInt();
                Next p;
                p=arr[a-1];
                while(p.next!=null){
                    p=p.next;
                }
                p.next=new Next();
                p.next.weight=c;
                p.next.id=b-1;
                p=arr[b-1];
                while(p.next!=null){
                    p=p.next;
                }
                p.next=new Next();
                p.next.weight=c;
                p.next.id=a-1;
            }
        }
/*
        public void DFS(){
            boolean []visited=new boolean[NodeNum];
            ArrayList<Integer>list=new ArrayList<>();
            ArrayList<ArrayList<Integer>>lists=new ArrayList<>();
            Stack<Integer>stack=new Stack<>();
            stack.push(0);
            list.add(0);
            while(!stack.isEmpty()){
                int a=stack.pop();
                Next p=arr[a];
                visited[a]=true;
                if(a==NodeNum-1){
                    lists.add(new ArrayList<>(list));
                }
                else{
                    boolean alVisit=true;

                    while (p.next!=null){
                        if(!visited[p.next.id]){
                            stack.push(p.next.id);
                            list.add(p.next.id);
                            alVisit=false;
                        }
                        p=p.next;
                    }
                    if(alVisit){
                        list.remove(list.size()-1);
                    }
                }
                visited[a]=false;
            }

            for (ArrayList<Integer> list1 : lists) {
                System.out.print("Path from " + 0 + " to " + (NodeNum-1) + ": ");
                for (int node : list1) {
                    System.out.print(node + " ");
                }
                System.out.println();
            }
        }*/


        class Value{
            int data;
            int weight=0;
        }
        ArrayList<Integer>visit=new ArrayList<>();
        ArrayList<Value>path=new ArrayList<>();
        ArrayList<ArrayList<Value>>paths=new ArrayList<>();
        public void DFS(int a){
            if(a==0){
                Value Avalue=new Value();
                Avalue.data=1;
                path.add(Avalue);
            }
            visit.add(a);
            if(a==NodeNum-1){
                paths.add(new ArrayList<>(path));
                visit.remove(visit.size()-1);
                path.remove(path.size()-1);
                return;
            }
            Next p=arr[a];
            while(p.next!=null){
                if(!visit.contains(p.next.id)){
                    Value value=new Value();
                    value.data=p.next.id+1;
                    value.weight=p.next.weight;
                    path.add(value);
                    DFS(p.next.id);
                }
                p=p.next;
            }
            visit.remove(visit.size()-1);
            path.remove(path.size()-1);
        }

        public void StartDFS(){
            DFS(0);

            for (ArrayList<Value> list : paths) {
                System.out.print("Path from " + 1 + " to " + (NodeNum) + ": ");
                for (Value node : list) {
                    System.out.print(node.data + " ");
                }
                System.out.println();
            }

            int []max=new int[paths.size()];
            int Trmin=Integer.MAX_VALUE;
            for (int i = 0; i < paths.size(); i++) {
                max[i]=0;
                for (Value value : paths.get(i)) {
                    if (value.weight > max[i]) {
                        max[i] = value.weight;
                    }
                }
                if(max[i]<Trmin){
                    Trmin=max[i];
                }
            }
            System.out.println(Trmin);
        }
    }

    public static void main(String[] args) {
        int n= scanner.nextInt();
        int m= scanner.nextInt();
        Tu tu=new Tu(n,m);
        tu.Create();
        System.out.println("---");
        tu.StartDFS();
    }
}
/*
6 7
1 2 4
2 3 4
3 6 7
1 4 2
4 5 5
5 6 6
3 5 2
 */
