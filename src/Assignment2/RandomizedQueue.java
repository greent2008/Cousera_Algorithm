

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by jianengxi on 15-10-4.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count;                          //随机队列元素个数
    private Item[] queue;                       //所有方法对数组queue进行操作
    private int capacity;                       //数组容量
    public RandomizedQueue(){                   //construct an empty randomized queue
        count = 0;
        capacity = 1;
        queue = (Item[])new Object[1];
    }
    private void expand(){                           //加倍式扩容,分摊分析复杂度位O(1)
        if (capacity <= count){
            capacity = 2 * capacity;
            Item[] newArrary = (Item[])new Object[capacity];
            for (int i = 0; i < count; i++){
                newArrary[i] = queue[i];
            }
            queue = newArrary;
        }
    }
    private void shrink(){
        if (capacity < 4){
            return;
        }
        if (count <= capacity>>2){
            capacity >>= 2;
            Item[] newArray = (Item[])new Object[capacity];
            for (int i = 0; i < count; i++){
                newArray[i] = queue [i];
            }
            queue = newArray;
        }
    }
    private void checkItem(Item item){          //检查item是否合法
        if (item == null){
            throw new NullPointerException("item is null");
        }
    }
    public boolean isEmpty(){                   //is the queue empty?
        return count == 0;
    }
    public int size(){                          //return the number of items on the queue
        return count;
    }
    public void enqueue(Item item){             //add the item
        checkItem(item);
        expand();
        queue[count++] = item;
    }
    public Item dequeue(){                      //remove and return a random item
        if (isEmpty()){
            throw new java.util.NoSuchElementException("queue is empty");
        }
        int index = StdRandom.uniform(count);
        Item value = queue[index];
//        for (int i = index; i < count-1; i++){
//            queue[i] = queue[i+1];
//        }
		  queue[index] = queue[count-1];
        count--;
        shrink();
        return value;
    }
    public Item sample(){                       //return (but do not remove) a random item
        if (isEmpty()){
            throw new java.util.NoSuchElementException("queue is empty");
        }
        int index = StdRandom.uniform(count);
        return queue[index];
    }
    public Iterator<Item> iterator(){           //return an independent iterator over items in random order
        return new MyIterator();
    }
    private class MyIterator<Item> implements Iterator<Item>{
        private int[] indexSequence;
        private int i;
        public MyIterator() {
            indexSequence = new int[count];
            for (int j = 0; j < count; j++) {
                indexSequence[j] = j;
            }
            StdRandom.shuffle(indexSequence);
            i = 0;
        }
        @Override
        public boolean hasNext() {
            return (i < count);
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("no element any more");
            }
            return (Item)queue[indexSequence[i++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
	public static void main(String args[]) {
		Deque d = new Deque();
    }
//    public static void main(String[] args){
//        // unit testing
//        Thread t1;
//        t1 = new Thread(new test());
//        t1.start();
//    }
//    static class test implements Runnable{
//        RandomizedQueue<String> deque;
//        @Override
//        public void run() {
//            deque = new RandomizedQueue<String>();
//            deque.enqueue("apple");
//            deque.enqueue("orange");
//            deque.enqueue("mellon");
//            deque.dequeue();
//            deque.enqueue("banana");
//            deque.enqueue("grape");
//            Iterator<String> i = deque.iterator();
//            while(i.hasNext()){
//                System.out.print(i.next()+"->");
//            }
//        }
//    }
}
