package BTree;

import java.util.*;

class Node<K extends Comparable<K>, V> implements Comparable<Node<K, V>>
{
    private int _maxSize;
    private int _minNodeSize;
    private List<SubNode<K, V>> _subNodes;
    private List<Node<K, V>> _children;
    private Node<K, V> _parent;
    private boolean _isRoot;
    private boolean _isLeaf;
    private K _upperKey;
    private K _lowerKey;

    //region Constructors
    private void init(int maxSize, Node<K, V> parent)
    {
        _subNodes = new ArrayList<SubNode<K, V>>();
        _children = new ArrayList<Node<K, V>>();
        setParent(parent);
        _maxSize = maxSize;
        _minNodeSize = (int) Math.ceil(maxSize / 2.0);
        _isLeaf = true;
        setRoot(false);
    }

    public Node(int maxSize)
    {
        init(maxSize, null);
        setRoot(true);
    }

    public Node(int maxSize, Node<K, V> parent)
    {
        init(maxSize, parent);
    }

    public Node(int maxSize, Node<K, V> parent, K key, V value)
    {
        init(maxSize, parent);
        _subNodes.add(new SubNode<K, V>(key, value));
    }
    //endregion

    //region Public
    //region Getters
    public boolean isRoot()
    {
        return _isRoot;
    }

    public boolean isLeaf()
    {
        return _isLeaf;
    }

    public Node<K, V> getParent()
    {
        return _parent;
    }

    public Node<K, V> getChild(int index)
    {
        return _children.get(index);
    }

    public List<Node<K, V>> getChildren()
    {
        return _children;
    }

    public List<SubNode<K, V>> getSubNodes()
    {
        return _subNodes;
    }

    public K getUpperKey()
    {
        return _upperKey;
    }

    public K getLowerKey()
    {
        return _lowerKey;
    }

    public boolean isEmpty()
    {
        return _subNodes.isEmpty();
    }

    public int size()
    {
        var size = _subNodes.size();
        if (_isLeaf) {
            return size;
        }
        for (var child : _children) {
            size += child.size();
        }
        return size;
    }

    public int ownSize()
    {
        return _subNodes.size();
    }

    public boolean willNotUnderflowIfRemoved()
    {
        return ownSize() != _minNodeSize;
    }

    public Vector<K> getAllKeys()
    {
        var keys = new Vector<K>();
        for (var subNode : _subNodes) {
            keys.add(subNode.getKey());
        }
        if (_isLeaf) {
            return keys;
        }
        for (var child : _children) {
            keys.addAll(Collections.list(child.getAllKeys().elements()));
        }
        return keys;
    }

    public Vector<V> getAllValues()
    {
        var subNodes = getAllSubNodes();
        var values = new Vector<V>();
        for (var subNode : subNodes) {
            values.add(subNode.getValue());
        }
        return values;
    }

    public Vector<SubNode<K, V>> getAllSubNodes()
    {
        var subNodes = new Vector<>(_subNodes);
        if (_isLeaf) {
            return subNodes;
        }
        for (var child : _children) {
            subNodes.addAll(Collections.list(child.getAllSubNodes().elements()));
        }
        subNodes.sort(new SubNodeComparator<K, V>());
        return subNodes;
    }
    //endregion

    //region Setters
    public void setParent(Node<K, V> parent)
    {
        _parent = parent;
    }

    public void setRoot(boolean isRoot)
    {
        _isRoot = isRoot;
    }

    public void setLeaf(boolean isLeaf)
    {
        _isLeaf = isLeaf;
    }
    //endregion

    public void addChild(Node<K, V> child)
    {
        _children.add(child);
        _children.sort(new NodeComparator<K, V>());
    }

    public V get(K key)
    {
        var subNode = new SubNode<K, V>(key, null);
        if (_subNodes.contains(subNode)) {
            int index = _subNodes.indexOf(subNode);
            return _subNodes.get(index).getValue();
        }
        if (_isLeaf) {
            return null;
        }
        var childIndex = findChild(subNode);
        return _children.get(childIndex).get(key);
    }

    public boolean containsValue(V value)
    {
        for (var subNode : _subNodes) {
            if (subNode.getValue().equals(value)) {
                return true;
            }
        }
        if (_isLeaf) {
            return false;
        }
        for (var child : _children) {
            if (child.containsValue(value)) {
                return true;
            }
        }
        return false;
    }

    public V put(K key, V value)
    {
        return put(key, value, false);
    }

    public V insert(SubNode<K, V> subNode)
    {
        _subNodes.add(subNode);
        _subNodes.sort(new SubNodeComparator<K, V>());
        setBoundaryKeys();
        return subNode.getValue();
    }

    public V split(SubNode<K, V> subNode)
    {
        insert(subNode);
        var middleIndex = _subNodes.size() / 2;
        var middleSubNode = _subNodes.remove(middleIndex);
        var rightSubNodes = new ArrayList<SubNode<K, V>>();
        for (int i = middleIndex; i < _subNodes.size(); i++) {
            rightSubNodes.add(_subNodes.get(i).copy());
        }
        _subNodes.removeAll(rightSubNodes);
        if (_parent == null) {
            _parent = new Node<K, V>(_maxSize);
            _parent.setRoot(true);
            _parent.setLeaf(false);
            _parent.addChild(this);
            this.setRoot(false);
        }
        var rightNode = new Node<K, V>(_maxSize, _parent);
        rightNode.putAll(rightSubNodes);
        _parent.addChild(rightNode);
        _parent.put(middleSubNode);
        if (!_children.isEmpty()) {
            rightNode.setLeaf(false);
            rightNode._children.addAll(_children.subList(middleIndex + 1, _children.size()));
            _children.removeAll(rightNode._children);
            for (var child : rightNode._children) {
                child.setParent(rightNode);
            }
        }
        setBoundaryKeys();
        return subNode.getValue();
    }

    public void clear()
    {
        _subNodes.clear();
        _children.clear();
        _upperKey = null;
        _lowerKey = null;
        _isLeaf = true;
    }

    public SubNode<K, V> remove(K key)
    {
        if (get(key) == null) {
            return null;
        }
        var subNodeKey = new SubNode<K, V>(key, null);
        if (_isLeaf) {
            var index = _subNodes.indexOf(subNodeKey);
            var old = _subNodes.remove(index);
            setBoundaryKeys();
            if (isUnderflow()) {
                underflow();
            }
            return old;
        }
        if (_subNodes.contains(subNodeKey)) {
            var index = _subNodes.indexOf(subNodeKey);
            var subNode = _subNodes.get(index);
            _subNodes.remove(subNode);
            replaceBranchSubNode(index);
            return subNode;
        }
        var childIndex = findChild(subNodeKey);
        return _children.get(childIndex).remove(key);
    }

    public boolean remove(K key, V value)
    {
        var old = get(key);
        if (old == null || !old.equals(value)) {
            return false;
        }

        remove(key);
        return true;
    }

    public V replace(K key, V newValue)
    {
        var subNode = new SubNode<K, V>(key, null);
        if (_subNodes.contains(subNode)) {
            int index = _subNodes.indexOf(subNode);
            return _subNodes.get(index).setValue(newValue);
        }
        if (_isLeaf) {
            return null;
        }
        var childIndex = findChild(subNode);
        return _children.get(childIndex).replace(key, newValue);
    }

    public boolean replace(K key, V oldValue, V newValue)
    {
        var subNode = new SubNode<K, V>(key, null);
        if (_subNodes.contains(subNode)) {
            int index = _subNodes.indexOf(subNode);
            if (_subNodes.get(index).getValue().equals(oldValue)) {
                _subNodes.get(index).setValue(newValue);
                return true;
            }
        }
        if (_isLeaf) {
            return false;
        }
        var childIndex = findChild(subNode);
        return _children.get(childIndex).replace(key, oldValue, newValue);
    }

    public SubNode<K, V> replaceSubNode(int index, SubNode<K, V> subNode)
    {
        var old = _subNodes.set(index, subNode);
        setBoundaryKeys();
        return old;
    }

    public void underflow()
    {
        if (_isRoot) {
            // Take key from left child
            if (isEmpty()) return;
            var leftChild = _children.getFirst();
            if (leftChild.willNotUnderflowIfRemoved()) {
                var key = leftChild.remove(leftChild.getUpperKey());
                insert(key);
                if (leftChild.isUnderflow()) {
                    leftChild.underflow();
                }
                return;
            }
            // Take key from right child
            var rightChild = _children.getLast();
            if (rightChild.willNotUnderflowIfRemoved()) {
                var key = rightChild.remove(rightChild.getLowerKey());
                insert(key);
                if (rightChild.isUnderflow()) {
                    rightChild.underflow();
                }
                return;
            }
            // merge children and remove root
            var leftSubNodes = leftChild.getAllSubNodes();
            var rightSubNodes = rightChild.getAllSubNodes();
            var leftChildren = leftChild.getChildren();
            var rightChildren = rightChild.getChildren();

            _subNodes.addAll(leftSubNodes);
            _subNodes.addAll(rightSubNodes);

            _children.addAll(leftChildren);
            _children.addAll(rightChildren);

            _children.remove(leftChild);
            _children.remove(rightChild);
            setBoundaryKeys();

            return;
        }
        // check siblings
        var thisIndex = _parent.getChildren().indexOf(this);
        Node<K, V> leftSibling, rightSibling;
        try {
            leftSibling = _parent.getChildren().get(thisIndex - 1);
            rightSibling = _parent.getChildren().get(thisIndex + 1);
        }
        catch (IndexOutOfBoundsException e) {
            leftSibling = null;
            rightSibling = null;
        }
        if (leftSibling != null && leftSibling.willNotUnderflowIfRemoved()) {
            // Take key from left sibling
            var leftKey = leftSibling.remove(leftSibling.getUpperKey());
            var parentKey = _parent.replaceSubNode(thisIndex - 1, leftKey);
            insert(parentKey);
        }
        else if (rightSibling != null && rightSibling.willNotUnderflowIfRemoved()) {
            // Take key from right sibling
            var rightKey = rightSibling.remove(rightSibling.getLowerKey());
            var parentKey = _parent.replaceSubNode(thisIndex, rightKey);
            insert(parentKey);
        }
        else if (leftSibling != null) {
            // Merge with left sibling
            var leftSubNodes = leftSibling.getAllSubNodes();
            var leftChildren = leftSibling.getChildren();
            var parentKey = _parent._subNodes.remove(thisIndex - 1);
            _children.addAll(0, leftChildren);
            _subNodes.addFirst(parentKey);
            _subNodes.addAll(0, leftSubNodes);
            setBoundaryKeys();
            _parent._children.remove(leftSibling);
            if (_parent.isUnderflow()) {
                _parent.underflow();
            }
        }
        else if (rightSibling != null) {
            // Merge with right sibling
            var rightSubNodes = rightSibling.getAllSubNodes();
            var rightChildren = rightSibling.getChildren();
            var parentKey = _parent._subNodes.remove(thisIndex);
            _children.addAll(rightChildren);
            _subNodes.addLast(parentKey);
            _subNodes.addAll(rightSubNodes);
            setBoundaryKeys();
            _parent._children.remove(rightSibling);
            if (_parent.isUnderflow()) {
                _parent.underflow();
            }
        }
    }

    @Override
    public int compareTo(Node<K, V> o)
    {
        return _lowerKey.compareTo(o.getLowerKey());
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Node)) {
            return false;
        }

        Node<K, V> other = (Node<K, V>) o;
        if (_subNodes.size() != other._subNodes.size()) {
            return false;
        }
        for (int i = 0; i < _subNodes.size(); i++) {
            if (!_subNodes.get(i).equalsFull(other._subNodes.get(i))) {
                return false;
            }
        }
        if (_children.size() != other._children.size()) {
            return false;
        }
        for (int i = 0; i < _children.size(); i++) {
            if (!_children.get(i).equals(other._children.get(i))) {
                return false;
            }
        }
        return true;
    }

    public Tree<K> toTree(int depth)
    {
        var keys = _subNodes.stream().map(SubNode::getKey).toList();
        var tree = new Tree<K>(depth, keys);
        for (var child : _children) {
            tree.children.add(child.toTree(depth + 1));
        }
        return tree;
    }
    //endregion

    //region Private

    private void put(SubNode<K, V> subNode)
    {
        put(subNode.getKey(), subNode.getValue(), true);
    }

    private V put(K key, V value, boolean onSplit)
    {
        SubNode<K, V> subNode = new SubNode<K, V>(key, value);
        if (_subNodes.isEmpty()) {
            return insert(subNode);
        }

        if (onSplit) {
            if (_subNodes.size() < _maxSize)
                return insert(subNode);
            return split(subNode);
        }

        if (_subNodes.contains(subNode)) {
            int index = _subNodes.indexOf(subNode);
            return _subNodes.get(index).setValue(value);
        }

        if (_isLeaf && _subNodes.size() < _maxSize) {
            return insert(subNode);
        }

        if (_isLeaf && _subNodes.size() == _maxSize) {
            return split(subNode);
        }

        int childIndex = findChild(subNode);

        var childNode = _children.get(childIndex);
        return childNode.put(key, value);
    }

    private void putAll(List<SubNode<K, V>> subNodes)
    {
        for (var subNode : subNodes) {
            put(subNode.getKey(), subNode.getValue());
        }
    }

    private void replaceBranchSubNode(int index)
    {
        //TODO: Implement replaceBranchSubNode
    }

    private int findChild(SubNode<K, V> subNode)
    {
        if (_isLeaf) {
            return -1;
        }
        for (int i = 0; i < _subNodes.size(); i++) {
            var result = _subNodes.get(i).compareTo(subNode);
            if (_subNodes.get(i).compareTo(subNode) > 0) return i;
        }
        return _children.size() - 1;
    }

    private void setBoundaryKeys()
    {
        _lowerKey = _subNodes.getFirst().getKey();
        _upperKey = _subNodes.getLast().getKey();
    }

    private boolean isUnderflow()
    {
        if (_isRoot) {
            return _subNodes.isEmpty();
        }
        return _subNodes.size() < _minNodeSize;
    }

    private int sizeOfChildren()
    {
        int size = 0;
        for (var child : _children) {
            size += child.size();
        }
        return size;
    }
    //endregion
}

class NodeComparator<K extends Comparable<K>, V> implements Comparator<Node<K, V>>
{
    @Override
    public int compare(Node<K, V> o1, Node<K, V> o2)
    {
        return o1.compareTo(o2);
    }
}

class KeyComparator<K extends Comparable<K>> implements Comparator<K>
{
    @Override
    public int compare(K o1, K o2)
    {
        return o1.compareTo(o2);
    }
}