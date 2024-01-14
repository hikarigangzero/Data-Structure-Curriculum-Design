public class LinkedList {
    class Node{
        String name;
        long duration;
        long space;
        long endTime;
        Node next;
    }

    private Node head;
    private Node p;
    private int length;

    LinkedList(){
        head=new Node();
        p=head;
        length=0;
    }
}
