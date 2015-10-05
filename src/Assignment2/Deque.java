

import java.util.Iterator;

/**
 * Created by jianengxi on 15-10-4.
 */
public class Deque<Item> implements Iterable<Item> {
    private int count;  //队列元素个数
    private class Node<Item>{
        public Node pre;
        public Node next;
        public Item value;
        public Node(Item item){
            pre = null;
            next = null;
            value = item;
        }
    }
    private class MyIterator<Item> implements Iterator<Item> {
        private Node<Item> next;
        public MyIterator(){
            next = first;
        }
        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("no next element");
            }
            Item temp = next.value;
            next = next.next;
            return temp;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    private Node first; // 所有方法对first和last进行操作
    private Node last;  // 所有方法对first和last进行操作
    public Deque(){
        // construct an empty deque
        first = null;
        last = null;
        count = 0;
    }
    private void checkItem(Item item){
        // 保证操作数item合法
        if (item == null){
            throw new NullPointerException("操作数item不合法");
        }
    }
    public boolean isEmpty(){
        // is the deque empty?
        return count == 0;
    }
    public int size(){
        //return the number of items on the deque
        return count;
    }
    public void addFirst(Item item){    // add the item to the front
        checkItem(item);
        if (first == null){
            Node temp = new Node(item);
            //temp.value = item;
            first = temp;
            last = temp;
        }
        else{
            Node temp = new Node(item);
            //temp.value = item;
            temp.next = first;
            first.pre = temp;
            first = temp;
        }
        count++;
    }
    public void addLast(Item item){     // add the item to the end
        checkItem(item);
        if (last == null){
            Node temp = new Node(item);
            //temp.value = item;
            first = temp;
            last = temp;
        }
        else{
            Node temp = new Node(item);
            //temp.value = item;
            last.next = temp;
            temp.pre = last;
            last = temp;
        }
        count++;
    }
    public Item removeFirst(){      // remove and return the item from the front
        if (isEmpty()){
            throw new java.util.NoSuchElementException("no element");
        }
        Item item;
        if (first == last){
            item = (Item)first.value;
            first = last = null;
        }
        else{
            item = (Item)first.value;
            first = first.next;
            first.pre = null;
        }
        count--;
        return item;
    }
    public Item removeLast(){       // remove and return the item from the end
        if (isEmpty()){
            throw new java.util.NoSuchElementException("no element");
        }
        Item item;
        if (first == last){
            item = (Item)first.value;
            first = last = null;
        }
        else{
            item = (Item)last.value;
            last = last.pre;
            last.next = null;
        }
        count--;
        return item;
    }
    public Iterator<Item> iterator(){
        // return an iterator over items in order from front to end
        return new MyIterator();
    }
//    public static void main(String[] args){
//        // unit testing
//        Thread t1;
//        t1 = new Thread(new test());
//        t1.start();
//    }
//    static class test implements Runnable{
//        Deque<String> deque;
//        @Override
//        public void run() {
//            deque = new Deque<String>();
//            deque.addFirst("apple");
//            deque.addFirst("orange");
//            deque.addFirst("mellon");
//            deque.removeLast();
//            deque.addLast("banana");
//            deque.addLast("grape");
//            Iterator<String> i = deque.iterator();
//            while(i.hasNext()){
//                System.out.print(i.next()+"->");
//            }
//        }
//    }
}
