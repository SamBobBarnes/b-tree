package BTree;

import java.util.*;

@SuppressWarnings("unchecked")
public class BTree<K extends Comparable<K>, V> implements Map<K, V>
{
    private Node<K, V> _root;

    public BTree()
    {
        init(4);
    }

    public BTree(int maxNodeSize, Map<K, V> map)
    {
        init(maxNodeSize);
        putAll(map);
    }

    public BTree(int maxNodeSize)
    {
        init(maxNodeSize);
    }

    private void init(int maxNodeSize)
    {
        _root = new Node<K, V>(maxNodeSize);
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
    public Collection<V> values()
    {
        return _root.getAllValues();
    }

    @Override
    public Set<Entry<K, V>> entrySet()
    {
        var list = new ArrayList<>(_root.getAllSubNodes());
        return new HashSet<Entry<K, V>>(list);
    }

    @Override
    public V get(Object key)
    {
        if (key == null) {
            throw new NullPointerException();
        }

        K k;
        try {
            k = (K) key;
        }
        catch (ClassCastException e) {
            return null;
        }
        return _root.get(k);
    }

    @Override
    public V put(K key, V value)
    {
        if (key == null || value == null) {
            throw new NullPointerException();
        }

        var newValue = _root.put(key, value);
        checkForSplit();
        return newValue;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m)
    {
        for (var node : m.entrySet()) {
            put(node.getKey(), node.getValue());
        }
    }

    @Override
    public V remove(Object key)
    {
        if (key == null) {
            throw new NullPointerException();
        }

        K k;
        try {
            k = (K) key;
        }
        catch (ClassCastException e) {
            return null;
        }
        return _root.remove(k);
    }

    @Override
    public boolean remove(Object key, Object value)
    {
        if (key == null || value == null) {
            throw new NullPointerException();
        }

        return _root.remove((K) key, (V) value);
    }

    @Override
    public V replace(K key, V newValue)
    {
        if (key == null || newValue == null) {
            throw new NullPointerException();
        }

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
        if (key == null) {
            throw new NullPointerException();
        }

        return _root.get((K) key) != null;
    }

    @Override
    public boolean containsValue(Object value)
    {
        if (value == null) {
            throw new NullPointerException();
        }

        return _root.containsValue((V) value);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof BTree) {
            return _root.equals(((BTree<K, V>) o)._root);
        }
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
        return new HashSet<K>(_root.getAllKeys());
    }

    public Tree<K> toTree()
    {
        return _root.toTree(0);
    }

    private void checkForSplit()
    {
        while (!_root.isRoot()) {
            _root = _root.getParent();
        }
    }
}
