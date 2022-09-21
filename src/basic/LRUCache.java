package basic;

import java.util.HashMap;
import java.util.Map;

class DoublyLinkedListNode<K, V> {
    DoublyLinkedListNode<K, V> prev;
    DoublyLinkedListNode<K, V> next;
    K key;
    V data;
}

public class LRUCache<K, V> {

    DoublyLinkedListNode<K, V> head;
    DoublyLinkedListNode<K, V> tail;
    Map<K, DoublyLinkedListNode<K, V>> map;

    int capacity;

    int size;

    public LRUCache(int capacity) {
        this.map = new HashMap<>();
        this.capacity = capacity;
        this.size = 0;
    }

    /**
     * Item is not present in the cache, so we are adding it.
     * 1. Adds to the head of the DoublyLinkedList.
     * 2. Puts the node in the map.
     */
    public void put(K k, V v) {
        DoublyLinkedListNode<K, V> node = map.get(k);
        if (node != null) {
            // Node is present, so update the value.
            node.data = v; // Update the value to 'v'.
            get(k); // Brings the node to the head.
        } else {
            // Creates a new node.
            if (size == capacity) {
                evict();
            }
            DoublyLinkedListNode<K, V> newNode = new DoublyLinkedListNode<>();
            newNode.key = k;
            newNode.data = v;
            newNode.next = head;
            if (head == null) {
                tail = newNode;
            } else {
                head.prev = newNode;
            }
            head = newNode;
            map.put(k, newNode);
            size++;
        }
    }

    /**
     * If key 'k' is present in the Cache, then:-
     * 1. Get the node from DoublyLinkedList.
     * 2. Remove the node from between, and put the node in the beginning.
     * 3. Adjust the connections.
     * else return null.
     */
    public V get(K k) {
        DoublyLinkedListNode<K, V> node = map.get(k);
        // move 'node' to head.
        // c1 <-> c2 <-> c3, and 'c2' is the node to be found.
        if (node != null) {
            DoublyLinkedListNode<K, V> c1 = node.prev;
            DoublyLinkedListNode<K, V> c2 = node;
            DoublyLinkedListNode<K, V> c3 = node.next;
            if (c2 == head) {
                return c2.data;
            } else if (c2 == tail) {
                c1.next = null;
                tail = c1;
            } else {
                c1.next = c3;
                c3.prev = c1;
            }
            head.prev = c2;
            c2.next = head;
            c2.prev = null;
            head = c2; // Brings c2 to the head.
            return c2.data;
        }
        return null;
    }

    /**
     * Evicts the last item from cache, in case the cache capacity is full.
     */
    private void evict() {
        map.remove(tail.key);
        DoublyLinkedListNode<K, V> prevNode = tail.prev;
        prevNode.next = null;
        tail.prev = null;
        tail = prevNode;
        size--;
    }
}
