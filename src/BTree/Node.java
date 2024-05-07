package BTree;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

class Node<K extends Comparable<K>,V>
{
    private int _maxSize;
    private List<SubNode<K,V>> _subNodes;
    private List<Node<K,V>> _children;
    private Node<K,V> _parent;
    private boolean _isRoot;

    private void init(int maxSize) {
        _subNodes = new Vector<SubNode<K,V>>();
        _children = new Vector<Node<K,V>>();
        _maxSize = maxSize;
    }

    public Node(int maxSize) {
        init(maxSize);
        setRoot(true);
    }

    public Node(int maxSize, Node<K,V> parent) {
        init(maxSize);
        _parent = parent;
    }

    public Node(int maxSize, K key, V value) {
        init(maxSize);
        _subNodes.add(new SubNode<K, V>(key, value));
    }

    public void setParent(Node<K,V> parent) {
        _parent = parent;
    }

    public void setRoot(boolean isRoot) {
        _isRoot = isRoot;
    }

    public boolean isRoot() {
        return _isRoot;
    }

    public V put(K key, V value) {
        SubNode<K,V> subNode = new SubNode<K,V>(key, value);
        if(_subNodes.isEmpty()) {
            _subNodes.add(subNode);
            return value;
        } else if(_subNodes.size() == _maxSize) {
            return split(subNode);
        }
        return insert(subNode);
    }

    public void split() {
        // split at the middle and push center key up to parent
    }

    public V insert(SubNode<K,V> subNode) {
        // insert key into the correct position
        _subNodes.add(subNode);
        _subNodes.sort(new SubNodeComparator<K,V>());
        return subNode.getValue();
    }
}

class SubNode<K extends Comparable<K>,V> implements Comparable<SubNode<K,V>>, Map.Entry<K,V>
{
    private final K _key;
    private V _value;

    public SubNode(K key, V value)
    {
        _key = key;
        _value = value;
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
    public int compareTo(SubNode<K,V> o)
    {
        return _key.compareTo(o.getKey());
    }
}

class SubNodeComparator<K extends Comparable<K>,V> implements Comparator<SubNode<K,V>>
{
    @Override
    public int compare(SubNode<K,V> o1, SubNode<K,V> o2)
    {
        return o1.compareTo(o2);
    }
}