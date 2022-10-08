package basic;

public class TestLRUCache {

    public static void main(String[] args) {

        // Enable -ea in VM arguments before testing.
        testSingleNodeCache();
        testDoubleNodeCache();
        testTripleNodeCache();
        testTripleNodeCache_overflowingCapacity();
    }

    public static void testSingleNodeCache() {
        LRUCache<String, Object> cache = new LRUCache<>(3);
        cache.put("1", 1);
        cache.put("1", 1);
        cache.get("1");
        Object data = cache.get("1");

        // h -> 1 <- t
        assert cache.map.size() == 1;
        assert cache.head.next == null;
        assert cache.tail.prev == null;
        assert cache.head == cache.tail;
    }

    public static void testDoubleNodeCache() {
        LRUCache<String, Object> cache = new LRUCache<>(3);
        cache.put("1", 1);
        cache.put("1", 1);
        cache.put("1", 1);
        cache.put("2", 2);
        Object data = cache.get("2");

        // h -> 2 <-> 1 <- t
        assert cache.map.size() == 2;
        assert (Integer) cache.head.data == 2;
        assert (Integer) cache.tail.data == 1;
        assert cache.head.prev == null;
        assert cache.tail.next == null;
        assert cache.head.next == cache.tail;
        assert cache.tail.prev == cache.head;
    }

    public static void testTripleNodeCache() {
        LRUCache<String, Object> cache = new LRUCache<>(3);
        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);
        Object data = cache.get("3");

        // h -> 3 <-> 2 <-> 1 <- t
        assert cache.map.size() == 3;
        assert (Integer) cache.head.data == 3;
        assert (Integer) cache.tail.data == 1;
        assert cache.head.next == cache.tail.prev;
        assert cache.head.prev == null;
        assert cache.tail.next == null;
    }

    public static void testTripleNodeCache_overflowingCapacity() {
        LRUCache<String, Object> cache = new LRUCache<>(3);
        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);

        Object data = cache.get("2");
        cache.get("3");
        cache.put("4", 4);

        // h -> 4 <-> 3 <-> 2 <- t
        assert cache.map.size() == 3;
        assert (Integer) cache.head.data == 4;
        assert (Integer) cache.tail.data == 2;
        assert (Integer) cache.head.next.data == 3;
        assert cache.head.next == cache.tail.prev;
        assert cache.head.prev == null;
        assert cache.tail.next == null;
        assert cache.get("1") == null;
    }
}
