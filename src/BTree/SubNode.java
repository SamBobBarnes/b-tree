package BTree;

import java.util.Comparator;
import java.util.Map;

class SubNode<K extends Comparable<K>, V> implements Comparable<SubNode<K, V>>, Map.Entry<K, V>
{
    private final K _key;
    private V _value;

    public SubNode(K key, V value)
    {
        _key = key;
        _value = value;
    }

    public SubNode<K, V> copy()
    {
        return new SubNode<K, V>(_key, _value);
    }

    @Override
    public K getKey()
    {
        return _key;
    }

    @Override
    public V getValue()
    {
        return _value;
    }

    @Override
    public V setValue(V value)
    {
        V oldValue = _value;
        _value = value;
        return oldValue;
    }

    @Override
    public int compareTo(SubNode<K, V> o)
    {
        return _key.compareTo(o.getKey());
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof SubNode) {
            return this.compareTo((SubNode<K, V>) o) == 0;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean equalsFull(Object o)
    {
        if (o instanceof SubNode) {
            SubNode<K, V> other = (SubNode<K, V>) o;
            return _key.equals(other.getKey()) && _value.equals(other.getValue());
        }
        return false;
    }
}

class SubNodeComparator<K extends Comparable<K>, V> implements Comparator<SubNode<K, V>>
{
    @Override
    public int compare(SubNode<K, V> o1, SubNode<K, V> o2)
    {
        return o1.compareTo(o2);
    }
}