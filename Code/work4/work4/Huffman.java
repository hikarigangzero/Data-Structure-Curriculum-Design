import java.io.*;
import java.util.*;

class HFList{
    char data;
    int id;
    int weight;
    int parent;
    int lChild;
    int rChild;
}

//用于记录文章中各个字符种类和数目
class Record{
    int num;
    char value;
}

//用于记录各个字符编码信息，以及编码长度，以及编码字符串
class Code{
    char data;
    ArrayList<Boolean> bCode=new ArrayList<>();
}

public class Huffman {
    ArrayList<HFList> hfList = new ArrayList<>();
    //用于存储各个字符的哈夫曼编码表格信息
    ArrayList<Character> charList = new ArrayList<>();
    //用于存储文章中各个字符
    ArrayList<Record> recordList= new ArrayList<>();
    //用于存储文章中各个字符种类和数目
    ArrayList<Code> codeList= new ArrayList<>();
    //用于存储各个字符编码信息，以及编码长度，以及编码字符串
    BitSet bitSet;
    //用于存文章的比特数组
    
    long BiteLength=0;
    //文章的比特数组的长度

    public void Read(){
        FileReader in=null;
        BufferedReader bin=null;
        File file=new File("source.txt");
        try {
            in=new FileReader(file);
            bin=new BufferedReader(in);
            int c=0;
            while ((c=bin.read())!=-1){
                charList.add((char)c);
            }
            bin.close();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        char end='#';
        charList.add(end);
    }

    public void SortElement(){
        for (Character c : charList) {
            if(recordList.isEmpty()){
                Record record = new Record();
                record.value=c;
                record.num=1;
                recordList.add(record);
                continue;
            }
            for (int i=0;i<recordList.size();i++) {
                if(recordList.get(i).value==c){
                    recordList.get(i).num++;
                    break;
                }
                if(i==recordList.size()-1){
                    Record record = new Record();
                    record.value=c;
                    record.num=1;
                    recordList.add(record);
                    break;
                }
            }
        }

        Comparator<Record> collection =new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return Integer.compare(o1.num,o2.num);
            }
        };
        recordList.sort(collection);
        Collections.reverse(recordList);
    }

    public void EditorCode(){
        HFList blank=new HFList();
        blank.weight=-1;
        hfList.add(blank);
        for (Record r : recordList) {
            HFList lin=new HFList();
            lin.data=r.value;
            lin.weight=r.num;
            lin.id=hfList.size();
            hfList.add(lin);
        }
        int size=hfList.size()-1;
        for (int i = 0; i < size-1; i++) {
            int TRMin=Integer.MAX_VALUE;
            int SeMin=Integer.MAX_VALUE;
            int indexTRMin=Integer.MAX_VALUE;
            int indexSeMin=Integer.MAX_VALUE;
            for (HFList h : hfList) {
                if(h.weight<TRMin&&h.parent==0&&h.weight!=-1){
                    SeMin=TRMin;
                    indexSeMin=indexTRMin;
                    TRMin=h.weight;
                    indexTRMin=h.id;
                }
                else if(h.weight<SeMin&&h.parent==0&&h.weight!=-1){
                    SeMin=h.weight;
                    indexSeMin=h.id;
                }
            }
            HFList lin=new HFList();
            lin.weight=TRMin+SeMin;
            lin.lChild=indexTRMin;
            lin.rChild=indexSeMin;
            lin.id=hfList.size();
            hfList.get(indexTRMin).parent=lin.id;
            hfList.get(indexSeMin).parent=lin.id;
            hfList.add(lin);
        }

        //现在把哈夫曼编码的结果写进布尔数组里作为对照表
        for (int i = 1; i <= size; i++) {
            int a=i;
            int j=0;
            Code code=new Code();
            code.data=hfList.get(i).data;
            while(hfList.get(a).parent!=0){
                int b=hfList.get(a).parent;
                if(hfList.get(b).lChild==a){
                    code.bCode.add(false);
                }
                if(hfList.get(b).rChild==a){
                    code.bCode.add(true);
                }
                j++;
                a=b;
            }
            Collections.reverse(code.bCode);
            codeList.add(code);
        }
    }

    public void EditorAchieve(){
        for (Code c:codeList){
            BiteLength+=c.bCode.size();
        }
        bitSet=new BitSet((int)BiteLength);

        //现在对整个文件进行编码
        int index=0;
        for (Character c :charList) {
            for (Code code:codeList) {
                if(code.data==c){
                    for (int i = 0; i < code.bCode.size(); i++,index++) {
                        bitSet.set(index,code.bCode.get(i));
                    }
                    break;
                }
            }
        }
    }

    public void ConsequenceWrite(){
        File bFile=new File("code.dat");
        byte[] byteArray = bitSet.toByteArray();
        FileOutputStream out=null;
        try {
            out=new FileOutputStream(bFile);
            out.write(byteArray);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void EditorWayWrite(){
        try {
            File file=new File("Huffman.txt");
            FileWriter out=new FileWriter(file);
            BufferedWriter bout=new BufferedWriter(out);
            for (Code c : codeList) {
                if(c.data=='\r'){
                    continue;
                }
                else if(c.data=='\n'){
                    bout.write("回车符 ");
                }
                else if(c.data==' '){
                    bout.write("空格符 ");
                }
                else {
                    if(c.data=='#'){
                        c.bCode.add(true);
                    }
                    bout.write(c.data+" ");
                }
                for (int i = 0; i < c.bCode.size(); i++) {
                    bout.write(c.bCode.get(i)?"1":"0");
                }
                bout.write("\n");
            }
            bout.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BitSet ConsequenceRead(){
        File file=new File("code.dat");
        FileInputStream in=null;
        BitSet reBitSet;
        try {
            in=new FileInputStream(file);
            byte[] byteArray=new byte[(int)file.length()];
            in.read(byteArray);
            reBitSet= BitSet.valueOf(byteArray);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return reBitSet;
    }

    public void Decode(BitSet set){
        //这里建立一个查找表辅助解码
        HashMap<ArrayList<Boolean>,Character> map=new HashMap<>();
        for(Code code : codeList){
            map.put(code.bCode,code.data);
        }

        int index=0;
        ArrayList<Character> charList=new ArrayList<>();
        ArrayList<Boolean> boolList=new ArrayList<>();
        while(index<set.length()){
            boolList.add(set.get(index));
            if(map.containsKey(boolList)){
                charList.add(map.get(boolList));
                boolList.clear();
            }
            index++;
        }

        File file=new File("recode.txt");
        FileWriter out=null;
        BufferedWriter bout=null;
        try {
            out=new FileWriter(file);
            bout=new BufferedWriter(out);
            for (Character c : charList) {
                if(c=='#'){
                    break;
                }
                bout.write(c);
            }
            bout.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void CLPrint(){
        for (Character o : charList) {
            System.out.print(o);
        }
    }

    public void RCPrint(){
        for(Record r : recordList){
            if(r.value=='\r'){
                continue;
            }
            if(r.value=='\n'){
                System.out.println("回车符 "+r.num);
                continue;
            }
            if(r.value==' '){
                System.out.println("空格符 "+r.num);
                continue;
            }
            System.out.println(r.value+" "+r.num);
        }
    }

    public void HFPrint(){
        for (HFList h : hfList) {
            if(hfList.indexOf(h)==0){
                continue;
            }
            if(h.data=='\r'){
                continue;
            }
            if(h.data=='\n'){
                System.out.println("回车符 "+h.id+" "+h.weight+" "
                        +h.parent+" "+h.lChild+" "+h.rChild+" ");
                continue;
            }
            if(h.data==' '){
                System.out.println("空格符 "+h.id+" "+h.weight+" "
                        +h.parent+" "+h.lChild+" "+h.rChild+" ");
                continue;
            }
            System.out.println(h.data+" "+h.id+" "+h.weight+" "
                    +h.parent+" "+h.lChild+" "+h.rChild+" ");
        }
    }

    public static void main(String[] args) {
        Huffman  hf = new Huffman();
        hf.Read();
        hf.CLPrint();
        hf.SortElement();
        hf.EditorCode();
        hf.RCPrint();
        hf.HFPrint();
        hf.EditorWayWrite();
        hf.EditorAchieve();
        hf.ConsequenceWrite();
        BitSet a=hf.ConsequenceRead();
        hf.Decode(a);
    }
}