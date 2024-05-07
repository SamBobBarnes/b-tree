package BTree;

import java.util.*;

@SuppressWarnings("unchecked")
public class BTree<K extends Comparable<K>,V> extends Dictionary<K,V> implements Map<K,V>
{
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
        _root = new Node<K,V>(maxNodeSize);
    }

    @Override
    public int size()
    {
        return _root.size();
    }

    @Override
    public boolean isEmpty()
    {
        return _root.isEmpty();
    }

    @Override
    public Enumeration<K> keys()
    {
        var keys = _root.getAllKeys();
        keys.sort(new KeyComparator<K>());
        return Collections.enumeration(keys);
    }

    @Override
    public Enumeration<V> elements()
    {
        var values = values();
        return Collections.enumeration(values);
    }

    @Override
    public Collection<V> values()
    {
        return _root.getAllValues();
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        return Set.of();
    }

    @Override
    public V get(Object key)
    {
        K k;
        try {
            k = (K) key;
        } catch (ClassCastException e) {
            return null;
        }
        return _root.get(k);
    }

    @Override
    public V put(K key, V value)
    {
        var newValue = _root.put(key, value);
        checkForSplit();
        return newValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        for(var node: m.entrySet()) {
            put(node.getKey(), node.getValue());
        }
    }

    @Override
    public V remove(Object key)
    {
        K k;
        try {
            k = (K) key;
        } catch (ClassCastException e) {
            return null;
        }
        return _root.remove(k);
    }

    @Override
    public boolean remove(Object key, Object value)
    {
        K k;
        V v;
        try {
            k = (K) key;
            v = (V) value;
        } catch (ClassCastException e) {
            return false;
        }
        return _root.remove(k, v);
    }

    @Override
    public V replace(K key, V newValue)
    {
        return _root.replace(key, newValue);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue)
    {
        return _root.replace(key, oldValue, newValue);
    }

    @Override
    public boolean containsKey(Object key)
    {
        return _root.get((K) key) != null;
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
    public void clear()
    {
        _root.clear();
    }

    @Override
    public Set<K> keySet()
    {
        return Set.of();
    }

    private void checkForSplit() {
        while (!_root.isRoot()) {
            _root = _root.getParent();
        }
    }
}
