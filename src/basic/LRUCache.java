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
    DoublyLinkedListNode<V> tail; // TODO
    Map<K, DoublyLinkedListNode<V>> map = new HashMap<>();

    int capacity;

    public LRUCache(int capacity) {
        head = null;
        tail = null;
        map = new HashMap<>();
        this.capacity = capacity;
    }

    /**
     * Item is not present in the cache, so we are adding it.
     * 1. Adds to the head of the DoublyLinkedList.
     * 2. Puts the node in the map.
     */
    public void put(K k, V v) {
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
    }

    /**
     * If key 'k' is present in the Cache, then:-
     * 1. get the node from DoublyLinkedList.
     * 2. remove the node from between, and put the node in the beginning.
     * Adjust the connections.
     *
     * @param k the key against which we need to fetch the value.
     * @return
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
            node.next = head;
            node.prev = null;
            head = node;
            return node.data;
        }
        return null;
    }

    /**
     * Evicts the last item from cache, in case teh cache capacity is full.
     */
    private void evict() {
        DoublyLinkedListNode<V> prevNode = tail.prev;
        prevNode.next = null;
        tail.prev = null;
        tail = prevNode;
    }
}
