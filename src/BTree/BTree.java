package BTree;

import java.util.*;

//region Exceptions
import BTree.Exceptions.HowDidYouGetHereException;
//endregion

public class BTree<K extends Comparable<K>,V> extends Dictionary<K,V> implements Map<K,V>
{
    private int _maxNodeSize;
    private Node<K,V> _root;

    public BTree() {
        init(4);
    }

    public BTree(int maxNodeSize, Map<K,V> map) {
        init(maxNodeSize);
        putAll(map);
    }

    public BTree(int maxNodeSize) {
        init(maxNodeSize);
    }

    private void init(int maxNodeSize) {
        _maxNodeSize = maxNodeSize;
        _root = new Node<K,V>(_maxNodeSize);
    }

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public Enumeration<K> keys()
    {
        return null;
    }

    @Override
    public Enumeration<V> elements()
    {
        return null;
    }

    @Override
    public Collection<V> values()
    {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return Set.of();
    }

    @Override
    public V get(Object key)
    {
        return null;
    }

    @Override
    public V put(K key, V value)
    {
        var newValue = _root.put(key, value);
        checkForSplit();
        return newValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for(var node: m.entrySet()) {
            put(node.getKey(), node.getValue());
        }
    }

    @Override
    public V remove(Object key)
    {
        return null;
    }

    @Override
    public boolean remove(Object key, Object value)
    {
        return false;
    }

    @Override
    public V replace(K key, V newValue)
    {
        return null;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue)
    {
        return false;
    }

    @Override
    public boolean containsKey(Object key)
    {
        return false;
    }

    @Override
    public boolean containsValue(Object value)
    {
        return false;
    }

    @Override
    public boolean equals(Object o)
    {
        return false;
    }

    @Override
    public void clear() {}

    @Override
    public Set<K> keySet()
    {
        return Set.of();
    }

    @Override
    public String toString() {
        return null;
    }

    private void checkForSplit() {
        while (!_root.isRoot()) {
            _root = _root.getParent();
        }
    }
}
