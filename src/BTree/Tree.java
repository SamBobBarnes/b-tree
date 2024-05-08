package BTree;

import java.util.ArrayList;
import java.util.List;

public class Tree<K>
{
    public int depth;
    public List<Tree<K>> children;
    public List<K> values;

    public Tree()
    {
        this(0, new ArrayList<>());
    }

    public Tree(int depth, List<K> values)
    {
        this.depth = depth;
        this.children = new ArrayList<>();
        this.values = values;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Tree) {
            Tree<K> tree = (Tree<K>) obj;
            return depth == tree.depth && values.equals(tree.values) && children.equals(tree.children);
        }
        return false;
    }
}
