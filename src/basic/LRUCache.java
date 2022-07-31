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

    public LRUCache() {
        head = null;
        tail = null;
        map = new HashMap<>();
    }

    /**
     * Item is not present in the cache, so we are adding it.
     * 1. Add to the head of the DoublyLinkedList.
     * 2. Put the node in the map.
     */
    public void add(K k, V v) {
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

    public V get(K k) {
        DoublyLinkedListNode<V> node = map.get(k);
        // move to front.
        if (node != null) {
            return node.data;
        }
        return null;
    }


}
