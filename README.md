# lru-cache

Here we implement a **Generic LRU Cache** in Java. You can read in detail about the implementation approach in my blog
post:
https://javaninja.io/designing-lru-cache

The following operations are implemented in **O(1)**:-

1. Get
2. Put
3. Evict

For implementation, I have used hashMap & DoublyLinkedList custom class. Tests are written to prove the working state of
the cache.

In order to run the tests please enable **-ea** in VM options, else assert statements will have no effect.