import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.*;
import java.io.*;

public class MyMap {
    final int INF=Integer.MAX_VALUE-200;
    
    public static class Next{
        int weight;
        //权值为两地之间的距离
        String lineID;
        //线路ID，用于区分边
        String key;
        //key所邻接的站点

        Next(String key,String lineID,int weight){
            this.key=key;
            this.lineID=lineID;
            this.weight=weight;
        }
    }
    public static class Node{
        String data;

        Node(String data){
            this.data=data;
        }

        public String getData() {
            return data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(data, node.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }

    static private int NodeNum=0;
    static private int LineNum=0;

    Map<Node, LinkedList<Next>> map = new HashMap<>();

    public void Read(){
        File file=new File("南京公交线路.txt");
        Scanner scanner=null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()){
            String str=scanner.nextLine();
            Scanner sc=new Scanner(str);
            sc.useDelimiter("[,\\s\r\n]+");
            String lineID=sc.next();
            String a=sc.next();
            String b=null;
            int i=0;
            while (sc.hasNext()){
                if(i%2==0){
                    b=sc.next();
                    Next next1=new Next(a,lineID,1);
                    Node node1=new Node(a);
                    Next next2=new Next(b,lineID,1);
                    Node node2=new Node(b);
                    if(!map.containsKey(node1)&& map.containsKey(node2)){
                        map.put(node1,new LinkedList<>());
                        map.get(node1).add(next2);
                        map.get(node2).add(next1);
                        NodeNum++;
                        LineNum++;
                    } else if (map.containsKey(node1)&&!map.containsKey(node2)) {
                        map.put(node2,new LinkedList<>());
                        map.get(node2).add(next1);
                        map.get(node1).add(next2);
                        NodeNum++;
                        LineNum++;
                    }
                    else if(!map.containsKey(node1)&& !map.containsKey(node2)){
                        map.put(node1,new LinkedList<>());
                        map.put(node2,new LinkedList<>());
                        map.get(node1).add(next2);
                        map.get(node2).add(next1);
                        NodeNum+=2;
                        LineNum++;
                    }
                    else{
                        map.get(node1).add(next2);
                        map.get(node2).add(next1);
                        LineNum++;
                    }
                    i++;
                    continue;
                }
                if(i%2==1){
                    a=sc.next();
                    Next next1=new Next(a,lineID,1);
                    Node node1=new Node(a);
                    Next next2=new Next(b,lineID,1);
                    Node node2=new Node(b);
                    if(!map.containsKey(node1)&& map.containsKey(node2)){
                        map.put(node1,new LinkedList<>());
                        map.get(node1).add(next2);
                        map.get(node2).add(next1);
                    } else if (map.containsKey(node1)&& !map.containsKey(node2)) {
                        map.put(node2,new LinkedList<>());
                        map.get(node2).add(next1);
                        map.get(node1).add(next2);
                    }
                    else if(!map.containsKey(node1)&& !map.containsKey(node2)){
                        map.put(node1,new LinkedList<>());
                        map.put(node2,new LinkedList<>());
                        map.get(node1).add(next2);
                        map.get(node2).add(next1);
                    }
                    else{
                        map.get(node1).add(next2);
                        map.get(node2).add(next1);
                    }
                    i++;
                }
            }
        }


        for (Map.Entry<Node,LinkedList<Next>> m:
             map.entrySet()) {
            System.out.print(m.getKey().data+": ");
            for (Next s:
                 m.getValue()) {
                System.out.print("邻接点的值 "+s.key + " 权值为"+s.weight+" "+s.lineID+"丨");
            }
            System.out.println();
        }
        NodeNum=map.size();
        System.out.println("----------分割线----------");
        System.out.println("站点数为"+NodeNum+" 边数为"+LineNum);
        System.out.println("----------分割线----------");
    }

    static class QueueItem {
        String station;  // 站点名称
        int transferCount;  // 换乘次数
        String line;  //所在的线路
        QueueItem parent;  // 前驱站点

        public QueueItem(String station, int transferCount, String line, QueueItem parent) {
            this.station = station;
            this.transferCount = transferCount;
            this.line = line;
            this.parent = parent;
        }
    }

    //最少换乘路径
    public void MinTestPath(String start,String end){
        Queue<QueueItem> queue=new LinkedList<>();//实现BFS的队列
        Map<String,Integer> changeNum=new HashMap<>();//映射每个点的换乘次数
        Set<String> visited=new HashSet<>();//记录已经访问的点
        Map<String,String> pre=new HashMap<>();//记录每个点的前一个点

        queue.offer(new QueueItem(start,0,null,null));
        changeNum.put(start,0);
        visited.add(start);
        int a=0;//记录是否为第一层

        while (!queue.isEmpty()){
            QueueItem cur=queue.poll();
            String curData=cur.station;
            int curChange=cur.transferCount;

            if(cur.station.equals(end)){
                //生成路线
                CreatePath(end,pre);
                return;
            }

            LinkedList<Next> nextList = map.get(new Node(curData));
            for (Next nx:nextList){
                int nextChange=curChange;
                /*
                LinkedList<Next> nextList1 = map.get(new Node(cur.parent.station));
                String linLine=null;
                for(Next nx1:nextList1){
                    if(nx1.key.equals(curData)){
                        linLine=nx1.lineID;
                        break;
                    }
                }
                if(!nx.lineID.equals(linLine)){
                    nextChange++;
                }
                */

                if(a==0){
                    cur.line=nx.lineID;
                }
                if(!Objects.equals(cur.line, nx.lineID)){
                    nextChange++;
                }

                if(!visited.contains(nx.key)|| nextChange < changeNum.get(nx.key)){
                    changeNum.put(nx.key,nextChange);
                    visited.add(nx.key);
                    pre.put(nx.key,curData);
                    queue.offer(new QueueItem(nx.key,nextChange,nx.lineID,cur));
                }
            }
            a++;
        }
    }

    public static void CreatePath(String end,Map<String,String> pre){
        System.out.print("最少换乘：");
        List<String> path=new ArrayList<>();//路线
        String curData=end;
        path.add(0, curData);
        while (pre.get(curData)!=null){
            curData=pre.get(curData);
            path.add(0, curData);
        }

        for(String n:path){
            System.out.print(n+" ");
        }
        System.out.println();
    }

    public int getWeight(String str1,String str2){
        for(Next nx:map.get(new Node(str1))){
            if(nx.key.equals(str2)){
                return nx.weight;
            }
        }
        return INF;
    }

    //经过最少站点的路径
    public void MinPath(String start,String end){
        Set<String> flag=new HashSet<>();
        Map<String,String> path=new HashMap<>();
        Map<String,Integer> distance=new HashMap<>();

        flag.add(start);
        path.put(start,start);
        for (Map.Entry<Node,LinkedList<Next>> m:
             map.entrySet()) {
            distance.put(m.getKey().data,INF);
        }
        distance.put(start,0);
        for(Next nx:map.get(new Node(start))){
            distance.put(nx.key,nx.weight+distance.get(start));
            path.put(nx.key,start);
        }

        for (int i = 0; i < NodeNum-1; i++) {
            int min=INF;
            String index=start;
            for (Map.Entry<Node,LinkedList<Next>> m:
                    map.entrySet()) {
                if(!flag.contains(m.getKey().data)&&distance.get(m.getKey().data)<min){
                    min=distance.get(m.getKey().data);
                    index=m.getKey().data;
                }
            }
            flag.add(index);

            for(Map.Entry<Node,LinkedList<Next>> m:
                    map.entrySet()) {
                String op=m.getKey().data;
                if(!flag.contains(op)&&distance.get(op)>distance.get(index)+getWeight(op,index)){
                    distance.put(op,distance.get(index)+getWeight(op,index));
                    path.put(op,index);
                }
            }
        }

        /*while (flag.size()!=NodeNum){
            int min=INF;
            String index=null;
            for (Map.Entry<Node,LinkedList<Next>> m:
                    map.entrySet()) {
                if(flag.contains(m.getKey().data)){
                    continue;
                }
                if(distance.get(m.getKey().data)<min){
                    min=distance.get(m.getKey().data);
                    index=m.getKey().data;
                }
            }
            System.out.println(min+index);
            if(index==null){
                continue;
            }
            flag.add(index);
            //System.out.println(index);
            for(Next nx:map.get(new Node(index))){
                if(distance.get(nx.key)>distance.get(index)+nx.weight){
                    distance.put(nx.key,distance.get(index)+nx.weight);
                    path.put(nx.key,index);
                }
            }
        }*/

        System.out.print("最少站点：");
        Stack<String> stack=new Stack<>();
        String cur=end;
        stack.push(cur);
        while (!Objects.equals(cur, start)){
            cur=path.get(cur);
            stack.push(cur);
        }
        while (!stack.isEmpty()){
            String s=stack.pop();
            System.out.print(s+" ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MyMap map=new MyMap();
        map.Read();
        map.MinTestPath("明故宫站","翠屏山站");
        map.MinPath("明故宫站","翠屏山站");
    }
}
