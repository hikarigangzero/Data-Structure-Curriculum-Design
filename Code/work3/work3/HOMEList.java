import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Tree{
    static class Value{
        String name;
        int []start=new int[3];
        int []end=new int[3];
        boolean marry;
        String address;
        boolean exist;
        String father;
        int level;
    }

    static class Node {
        Value data;
        Node child;
        Node brother;

        Node() {
            data = new Value();
            child = null;
            brother = null;
        }
    }

    Node root;
    Node p;
    static Scanner sc=new Scanner(System.in);

    //用于读取数据与创建初始数据
    public void Read(){
        root= new Node();
        Scanner scanner=null;
        File file=new File("HomeData.txt");
        try {
            scanner=new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        root.data.name=scanner.next();
        for (int i = 0; i < 3; i++) {
            root.data.start[i]=scanner.nextInt();
        }
        int a=scanner.nextInt();
        if(a!=0){
            root.data.end[0]=a;
            root.data.end[1]=scanner.nextInt();
            root.data.end[2]=scanner.nextInt();
        }
        else{
            root.data.end[0]=0;
        }
        root.data.marry=scanner.nextBoolean();
        root.data.address=scanner.next();
        root.data.father=scanner.next();
        if(a!=0){
            root.data.exist=false;
        }
        else{
            root.data.exist=true;
        }
        root.data.level=scanner.nextInt();

        while(scanner.hasNext()){
            String name=scanner.next();
            int []start=new int[3];
            int []end=new int[3];
            for (int i = 0; i < 3; i++) {
                start[i]=scanner.nextInt();
            }
            a=scanner.nextInt();
            if(a!=0){
                end[0]=a;
                end[1]=scanner.nextInt();
                end[2]=scanner.nextInt();
            }
            else{
                root.data.end[0]=0;
            }
            boolean marry=scanner.nextBoolean();
            String address=scanner.next();
            String father=scanner.next();
            boolean exist;
            if(a!=0){
                exist=false;
            }
            else{
                exist=true;
            }
            int level=scanner.nextInt();
            p=root;
            Queue<Node>queue=new LinkedList<>();
            queue.offer(root);
            while(!queue.isEmpty()){
                Node temp=queue.poll();
                if(Objects.equals(temp.data.name,father)){
                    p=temp;
                    break;
                }
                if(temp.child!=null){
                    queue.offer(temp.child);
                }
                if(temp.brother!=null){
                    queue.offer(temp.brother);
                }
            }
            queue.clear();
            if(p.child==null){
                p.child= new Node();
                p.child=CreateTree(name,start,end,marry,address,father,exist,level);
            }
            else{
                p=p.child;
                while(p.brother!=null){
                    p=p.brother;
                }
                p.brother= new Node();
                p.brother=CreateTree(name,start,end,marry,address,father,exist,level);
            }
        }
    }

    public Node CreateTree(String name,int []start,int []end, boolean marry,
                           String address,String father,boolean exist,int level){
        Node t= new Node();
        t.data.name=name;
        t.data.start=start;
        t.data.end=end;
        t.data.marry=marry;
        t.data.address=address;
        t.data.father=father;
        t.data.exist=exist;
        t.data.level=level;
        return t;
    }

    //显示家谱结构(1)
    public void StructurePrint(){
        Stack<Node>stack=new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node temp=stack.pop();
            System.out.print("丨");
            for (int i = 0; i < temp.data.level; i++) {
                System.out.print("——");
            }
            System.out.println(temp.data.name);
            if(temp.brother!=null){
                stack.push(temp.brother);
            }
            if(temp.child!=null){
                stack.push(temp.child);
            }
        }
        System.out.println("--------------------分割线--------------------");
    }

    //一般显示
    public void Print(Node node){
        System.out.print("姓名："+node.data.name+"丨 出生日期：");
        for (int i = 0; i < 3; i++) {
            if(i==2){
                System.out.print(node.data.start[i]);
            }
            else{
                System.out.print(node.data.start[i]+".");
            }
        }
        System.out.print("丨 结婚状态：");
        if(node.data.marry){
            System.out.print("已婚");
        }
        else{
            System.out.print("未婚");
        }
        System.out.print("丨 住址："+node.data.address);
        System.out.print("丨 第"+node.data.level+"代丨 ");
        System.out.print("父亲："+node.data.father);
        if(node.data.exist){
            System.out.print("丨 活着");
            System.out.println();
        }
        else{
            System.out.print("丨 已故 死亡日期：");
            for (int i = 0; i < 3; i++) {
                if(i==2){
                    System.out.print(node.data.end[i]);
                }
                else{
                    System.out.print(node.data.end[i]+".");
                }
            }
            System.out.println();
        }
    }

    //显示第n代的所有数据(2)
    public void LevelNPrint(int n){
        System.out.println("以下是第"+n+"代的所有信息");
        Queue<Node>queue=new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node temp=queue.poll();
            if(temp.data.level==n){
                Print(temp);
            }
            if(temp.child!=null){
                queue.offer(temp.child);
            }
            if(temp.brother!=null){
                queue.offer(temp.brother);
            }
        }
        System.out.println("--------------------分割线--------------------");
    }

    //按姓名搜索的函数
    public Node Search(String name){
        Queue<Node>queue=new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            Node temp=queue.poll();
            if(Objects.equals(temp.data.name,name)){
                return temp;
            }
            if(temp.child!=null){
                queue.offer(temp.child);
            }
            if(temp.brother!=null){
                queue.offer(temp.brother);
            }
        }
        return null;
    }

    //按照姓名查询，输出成员信息,包括本人、父亲和孩子(3)
    public void RelevantPrint(String name){
        Node q= new Node();
        System.out.println("此人信息为：");
        q=Search(name);
        Print(q);
        System.out.println("此人父亲的信息为：");
        Print(Search(q.data.father));
        if(q.child!=null){
            System.out.println("此人孩子的信息为：");
            Print(q.child);
            q=q.child;
            while(q.brother!=null){
                Print(q.brother);
                q=q.brother;
            }
        }
        System.out.println("--------------------分割线--------------------");
    }

    //按照出生时间查找成员名单（4）
    public void BirthdayPrint(int []start){
        Queue<Node>queue=new LinkedList<>();
        queue.offer(root);
        int a=0;
        ArrayList<String>arrayList=new ArrayList<>();
        while(!queue.isEmpty()){
            Node temp=queue.poll();
            if(Arrays.equals(temp.data.start,start)){
                arrayList.add(temp.data.name);
                a++;
            }
            if(temp.child!=null){
                queue.offer(temp.child);
            }
            if(temp.brother!=null){
                queue.offer(temp.brother);
            }
        }
        if(a!=0){
            System.out.print("这一天出生的成员名单：");
            for (String s : arrayList) {
                System.out.print(s+" ");
            }
            System.out.println();
            System.out.println("--------------------分割线--------------------");
        }
    }

    //输入两人姓名，确定其关系(5)
    public void SearchRelation(String name1,String name2){
        Node q= new Node();
        q=Search(name1);
        Node t= new Node();
        t=Search(name2);
        if(q.data.father.equals(t.data.name)){
            System.out.println("父子关系丨其中 "+name2+" 是 "+name1+" 的父亲");
            System.out.println("--------------------分割线--------------------");
            return;
        }
        else if(t.data.father.equals(q.data.name)){
            System.out.println("父子关系丨其中 "+name1+" 是 "+name2+" 的父亲");
            System.out.println("--------------------分割线--------------------");
            return;
        }
        Node OneFather= new Node();
        OneFather=Search(q.data.father);
        if(OneFather.child!=null){
            Node TwoFather= new Node();
            TwoFather=Search(t.data.father);
            if(OneFather.data.name.equals(TwoFather.data.name)){
                System.out.println("兄弟关系");
            }
            else{
                System.out.println("没有关系");
            }
        }
        else{
            System.out.println("没有关系");
        }
        System.out.println("--------------------分割线--------------------");
    }

    //某成员添加孩子(6)
    public void AddChild(String name){
        Node q= new Node();
        q=Search(name);
        q.data.marry=true;
        if(q.child==null){
            q.child= new Node();
            System.out.println("请输入孩子姓名:");
            String cName=sc.next();
            System.out.println("请输入孩子的出生日期（以这样的格式* * *）");
            int []cStart=new int[3];
            for (int i = 0; i < 3; i++) {
                cStart[i]=sc.nextInt();
            }
            System.out.println("孩子是否存活:(是---1，否---0)");
            int a=sc.nextInt();
            boolean cExist=true;
            int []cEnd=new int[3];
            if(a==0){
                System.out.println("请输入孩子的死亡日期（以这样的格式* * *）");
                for (int i = 0; i < 3; i++) {
                    cEnd[i]=sc.nextInt();
                }
                cExist=false;
            }
            System.out.println("请输入孩子的住址：");
            String cAddress=sc.next();
            q.child=CreateTree(cName,cStart,cEnd,false,cAddress,
                    q.data.name,cExist,p.data.level+1);
        }
        else{
            Node t= new Node();
            t=q;
            q=q.child;
            while(q.brother!=null){
                q=q.brother;
            }
            q.brother= new Node();
            System.out.println("请输入孩子姓名:");
            String cName=sc.next();
            System.out.println("请输入孩子的出生日期（以这样的格式* * *）");
            int []cStart=new int[3];
            for (int i = 0; i < 3; i++) {
                cStart[i]=sc.nextInt();
            }
            System.out.println("孩子是否存活:(是---1，否---0)");
            int a=sc.nextInt();
            boolean cExist=true;
            int []cEnd=new int[3];
            if(a==0){
                System.out.println("请输入孩子的死亡日期（以这样的格式* * *）");
                for (int i = 0; i < 3; i++) {
                    cEnd[i]=sc.nextInt();
                }
                cExist=false;
            }
            System.out.println("请输入孩子的住址：");
            String cAddress=sc.next();
            q.brother=CreateTree(cName,cStart,cEnd,false,cAddress,
                    t.data.name,cExist,t.data.level+1);
        }
    }

    //删除某成员(7)
    public void Delete(String name){
        Node q= new Node();
        q=Search(name);
        Node qsf= new Node();
        qsf=Search(q.data.father);
        Node node= new Node();
        node=qsf.child;
        if(node!=q){
            while(node.brother!=null){
                if(node.brother==q){
                    node.brother=q.brother;
                    q.brother=null;
                    break;
                }
                node=node.brother;
            }
        }
        else{
            qsf.child=q.brother;
            q.brother=null;
            q.child=null;
        }
    }

    //修改某成员信息(8)
    public void Change(Node q){
        System.out.println("请问想更改的信息类型（出生日期--1，地址--2，死亡时间--3，" +
                "其父亲--4，婚姻状况--5,退出--0）");
        int a=0;
        do{
            a=sc.nextInt();
            switch (a){
                case 1:
                    System.out.println("请输入新的出生日期（以这样的格式* * *）");
                    for (int i = 0; i < 3; i++) {
                        q.data.start[i]=sc.nextInt();
                    }
                    break;
                case 2:
                    System.out.println("请输入新的地址：");
                    q.data.address=sc.next();
                    break;
                case 3:
                    System.out.println("请输入新的死亡日期（以这样的格式* * *）");
                    for (int i = 0; i < 3; i++) {
                        q.data.end[i]=sc.nextInt();
                    }
                    q.data.exist=false;
                    break;
                case 4:
                    System.out.println("请输入新的父亲姓名：");
                    String NewFather=sc.next();
                    Node t= new Node();
                    if(q.brother!=null){
                        Node qsf= new Node();
                        qsf=Search(q.data.father);
                        Node qsfC= new Node();
                        qsfC=qsf.child;
                        if(qsfC!=q){
                            while(qsfC.brother!=null){
                                if(qsfC.brother==q){
                                    qsfC.brother=q.brother;
                                    q.brother=null;
                                    break;
                                }
                                qsfC=qsfC.brother;
                            }
                        }
                        else{
                            qsf.child=q.brother;
                            q.brother=null;
                            break;
                        }
                    }
                    q.data.father=NewFather;
                    t=Search(q.data.father);
                    if(t.child==null){
                        t.child=q;
                    }
                    else{
                        Node tt= new Node();
                        tt=t.child;
                        while(tt.brother!=null){
                            tt=tt.brother;
                        }
                        tt.brother=q;
                    }
                case 5:
                    System.out.println("请输入新的婚姻状况：(已婚--1，未婚--0)");
                    int b=sc.nextInt();
                    if(b==1){
                        q.data.marry=true;
                        AddChild(q.data.name);
                    }
                    if(b==0){
                        q.data.marry=false;
                        q.child=null;
                    }
                case 0:
                default:
                    break;
            }
        }while(a!=0);
    }

    public void Menu(){
        for (int i = 0; i < 2; i++) {
            System.out.println("----------------------------------------");
        }
        System.out.println("----------------- 菜单 ------------------");
        System.out.println("----------------------------------------");
        System.out.println("**********1、输出家谱结构------------------");
        System.out.println("**********2、显示第n代所有数据--------------");
        System.out.println("**********3、按照姓名查找成员及家属信息-------");
        System.out.println("**********4、查找对应生日的成员名单----------");
        System.out.println("**********5、确定两人的关系----------------");
        System.out.println("**********6、添加孩子---------------------");
        System.out.println("**********7、删除成员---------------------");
        System.out.println("**********8、修改成员信息------------------");
        System.out.println("**********0、退出程序---------------------");
        System.out.println("----------------------------------------");
        System.out.println("--------------------分割线--------------------");
    }

    public void Start(){
        int a=0;
        do{
            Menu();
            a=sc.nextInt();
            switch (a){
                case 1:
                    StructurePrint();
                    break;
                case 2:
                    int n=0;
                    System.out.println("请输入要显示第几代的信息");
                    n=sc.nextInt();
                    LevelNPrint(n);
                    break;
                case 3:
                    System.out.println("请输入要查找的成员姓名：");
                    String name=sc.next();
                    RelevantPrint(name);
                    break;
                case 4:
                    System.out.println("请输入要查找的成员生日（以这样的格式* * *）：");
                    int []birthday=new int[3];
                    for (int i = 0; i < 3; i++) {
                        birthday[i]=sc.nextInt();
                    }
                    BirthdayPrint(birthday);
                    break;
                case 5:
                    System.out.println("请输入要查找的两人姓名：");
                    String name1=sc.next();
                    String name2=sc.next();
                    SearchRelation(name1,name2);
                    break;
                case 6:
                    System.out.println("请输入要添加的成员姓名：");
                    String name3=sc.next();
                    AddChild(name3);
                    break;
                case 7:
                    System.out.println("请输入要删除的成员姓名：");
                    String name4=sc.next();
                    Delete(name4);
                    break;
                case 8:
                    System.out.println("请输入要修改的成员姓名：");
                    String name5=sc.next();
                    Node  q= new Node();
                    q=Search(name5);
                    while(q==null){
                        System.out.println("请输入正确的姓名：");
                        name5=sc.next();
                        q=Search(name5);
                    }
                    Change(q);
                    break;
                case 0:
                default:
                    break;
            }
        }while(a!=0);
    }
}



public class HOMEList {
    public static void main(String[] args) {
        Tree tree=new Tree();
        tree.Read();
        tree.Start();
    }
}
