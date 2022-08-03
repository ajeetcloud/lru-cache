package basic;

import java.util.HashMap;
import java.util.Map;

class DoublyLinkedListNode<T> {
    DoublyLinkedListNode<T> prev;
    DoublyLinkedListNode<T> next;
    T data;
}

public class LRUCache<K, V> {

    DoublyLinkedListNode<V> head;
    DoublyLinkedListNode<V> tail;
    Map<K, DoublyLinkedListNode<V>> map;

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
        DoublyLinkedListNode<V> node = map.get(k);
        if (node != null) {
            // Node is present, so update the value.
            node.data = v; // Update the value to 'v'.
            get(k); // Brings the node to the head.
        } else {
            // Creates a new node.
            if (size == capacity) {
                evict();
            }
            DoublyLinkedListNode<V> newNode = new DoublyLinkedListNode<>();
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
        DoublyLinkedListNode<V> node = map.get(k);
        // move 'node' to front.
        // X <--> node <-> Y, change it to X <-> Y
        if (node != null) {
            DoublyLinkedListNode<V> x = node.prev;
            DoublyLinkedListNode<V> y = node.next;
            if (x != null) {
                x.next = y;
            }
            if (y != null) {
                y.prev = x;
            }
            // Move the node to head, and update head.
            node.next = head;
            node.prev = null;
            head = node;
            return node.data;
        }
        return null;
    }

    /**
     * Evicts the last item from cache, in case the cache capacity is full.
     */
    private void evict() {
        DoublyLinkedListNode<V> prevNode = tail.prev;
        prevNode.next = null;
        tail.prev = null;
        tail = prevNode;
        size--;
    }
}
