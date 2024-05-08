import com.flextrade.jfixture.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import BTree.BTree;
import BTree.Tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BTreeTests
{
    private List<Tuple<Integer, String>> generateList(int length)
    {
        var fixture = new JFixture();
        List<Tuple<Integer, String>> list = new Vector<Tuple<Integer, String>>();
        for (int i = 0; i < length; i++) {
            list.add(new Tuple<Integer, String>(fixture.create(Integer.class), fixture.create(String.class)));
        }
        return list;
    }

    // constructors

    //region B-Tree stack
    @Test
    public void Organization_CorrectSingleNode()
    {
        var tree = new Tree<Integer>()
        {
            {
                depth = 0;
                values = new ArrayList<Integer>(List.of(1, 2, 3, 4));
            }
        };
        BTree<Integer, String> bTree = new BTree<Integer, String>();
        bTree.put(1, "one");
        bTree.put(2, "two");
        bTree.put(3, "three");
        bTree.put(4, "four");
        assertEquals(tree, bTree.toTree());
    }

    @Test
    public void Organization_CorrectSingleSplit()
    {
        var list = generateList(5);

        BTree<Integer, String> bTree = new BTree<Integer, String>();
        for (var tuple : list) {
            bTree.put(tuple.getKey(), tuple.getValue());
        }

        list.sort(new TupleComparator<Integer, String>());
        var tree = new Tree<Integer>()
        {
            {
                depth = 0;
                children = new ArrayList<Tree<Integer>>(List.of(
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(list.get(0).getKey(), list.get(1).getKey()));
                            }
                        },
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(list.get(3).getKey(), list.get(4).getKey()));
                            }
                        }
                                                               ));
                values = new ArrayList<Integer>(List.of(list.get(2).getKey()));
            }
        };
        assertEquals(tree, bTree.toTree());
    }

    @Test
    public void Organization_CorrectDoubleSplit()
    {
        var list = new ArrayList<Tuple<Integer, String>>(List.of(
                new Tuple<Integer, String>(10, "ten"),
                new Tuple<Integer, String>(2, "two"),
                new Tuple<Integer, String>(4, "four"),
                new Tuple<Integer, String>(9, "nine"),
                new Tuple<Integer, String>(5, "five"),
                new Tuple<Integer, String>(3, "three"),
                new Tuple<Integer, String>(6, "six"),
                new Tuple<Integer, String>(7, "seven"),
                new Tuple<Integer, String>(8, "eight"),
                new Tuple<Integer, String>(1, "one")
                                                                ));

        BTree<Integer, String> bTree = new BTree<Integer, String>();
        for (var tuple : list) {
            bTree.put(tuple.getKey(), tuple.getValue());
            bTree.size();
        }

        list.sort(new TupleComparator<Integer, String>());
        var tree = new Tree<Integer>()
        {
            {
                depth = 0;
                values = new ArrayList<Integer>(List.of(5, 8));
                children = new ArrayList<Tree<Integer>>(List.of(
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(1, 2, 3, 4));
                            }
                        },
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(6, 7));
                            }
                        },
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(9, 10));
                            }
                        }
                                                               ));
            }
        };
        var result = bTree.toTree();
        assertEquals(tree, result);
    }

    @Test
    public void Organization_Depth2()
    {
        var list = new ArrayList<Tuple<Integer, String>>(List.of(
                new Tuple<Integer, String>(10, "ten"),
                new Tuple<Integer, String>(2, "two"),
                new Tuple<Integer, String>(4, "four"),
                new Tuple<Integer, String>(9, "nine"),
                new Tuple<Integer, String>(20, "ten"),
                new Tuple<Integer, String>(22, "two"),
                new Tuple<Integer, String>(24, "four"),
                new Tuple<Integer, String>(5, "five"),
                new Tuple<Integer, String>(3, "three"),
                new Tuple<Integer, String>(6, "six"),
                new Tuple<Integer, String>(28, "eight"),
                new Tuple<Integer, String>(21, "one"),
                new Tuple<Integer, String>(7, "seven"),
                new Tuple<Integer, String>(8, "eight"),
                new Tuple<Integer, String>(29, "nine"),
                new Tuple<Integer, String>(25, "five"),
                new Tuple<Integer, String>(23, "three"),
                new Tuple<Integer, String>(26, "six"),
                new Tuple<Integer, String>(27, "seven"),
                new Tuple<Integer, String>(30, "ten")
                                                                ));

        BTree<Integer, String> bTree = new BTree<Integer, String>();
        for (var tuple : list) {
            bTree.put(tuple.getKey(), tuple.getValue());
            bTree.size();
        }

        list.sort(new TupleComparator<Integer, String>());
        var tree = new Tree<Integer>()
        {
            {
                depth = 0;
                values = new ArrayList<Integer>(List.of(22));
                children = new ArrayList<Tree<Integer>>(List.of(
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(4, 9));
                                children = new ArrayList<Tree<Integer>>(List.of(
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(2, 3));
                                            }
                                        },
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(5, 6, 7, 8));
                                            }
                                        },
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(10, 20, 21));
                                            }
                                        }
                                                                               ));
                            }
                        },
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(25, 28));
                                children = new ArrayList<Tree<Integer>>(List.of(
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(23, 24));
                                            }
                                        },
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(26, 27));
                                            }
                                        },
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(29, 30));
                                            }
                                        }
                                                                               ));
                            }
                        }
                                                               ));
            }
        };
        var result = bTree.toTree();
        assertEquals(tree, result);
    }

    //endregion

    //region size()
    @Test
    public void size_EmptyTree_ReturnsZero()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertEquals(0, tree.size());
    }

    @Test
    public void size_TreeWithOneElement_ReturnsOne()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertEquals(1, tree.size());
    }

    @Test
    public void size_TreeWithMultipleElements_SingleSplit_ReturnsCorrectSize()
    {
        var list = generateList(4);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        assertEquals(4, tree.size());
    }

    @Test
    public void size_TreeWithMultipleElements_DoubleSplit_ReturnsCorrectSize()
    {
        var list = generateList(10);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        assertEquals(10, tree.size());
    }
    //endregion

    //    public boolean isEmpty()

    //    public Collection<V> values()

    //    public Set<Map.Entry<K, V>> entrySet()

    //    public V get(Object key)

    //    public V put(K key, V value)

    //    public void putAll(Map<? extends K, ? extends V> m)

    //    public V remove(Object key)

    //    public boolean remove(Object key, Object value)

    //    public V replace(K key, V newValue)

    //    public boolean replace(K key, V oldValue, V newValue)

    //    public boolean containsKey(Object key)

    //    public boolean containsValue(Object value)

    //    public boolean equals(Object o)

    //    public void clear()

    //    public Set<K> keySet()

}
