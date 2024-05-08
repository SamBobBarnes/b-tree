import com.flextrade.jfixture.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import BTree.BTree;
import BTree.Tree;

import java.util.*;

public class BTreeTests
{
    private final int DEFAULT_MAX_SIZE = 4;

    private List<Tuple<Integer, String>> generateList(int length, boolean shuffle)
    {
        long seed = 12345; // You can change this seed value
        Random random = new Random(seed);
        List<Tuple<Integer, String>> list = new Vector<Tuple<Integer, String>>();
        for (int i = 0; i < length; i++) {
            list.add(new Tuple<Integer, String>(i, Integer.toString(i)));
        }
        if (shuffle) Collections.shuffle(list, random);
        return list;
    }

    private List<Tuple<Integer, String>> generateList(int length)
    {
        return generateList(length, false);
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
        var list = generateList(5, false);

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

    @Test
    public void Organization_Depth3()
    {
        var list = generateList(100, true);

        BTree<Integer, String> bTree = new BTree<Integer, String>();
        for (var tuple : list) {
            bTree.put(tuple.getKey(), tuple.getValue());
        }

        list.sort(new TupleComparator<Integer, String>());
        var tree = new Tree<Integer>()
        {
            {
                depth = 0;
                values = new ArrayList<Integer>(List.of(47));
                children = new ArrayList<Tree<Integer>>(List.of(
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(11, 21, 33));
                                children = new ArrayList<Tree<Integer>>(List.of(
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(3, 8));
                                                children = new ArrayList<Tree<Integer>>(List.of(
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(0, 1, 2));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(4, 5, 6, 7));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(9, 10));
                                                            }
                                                        }
                                                                                               ));
                                            }
                                        },
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(15, 18));
                                                children = new ArrayList<Tree<Integer>>(List.of(
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(12, 13, 14));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(16, 17));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(19, 20));
                                                            }
                                                        }
                                                                                               ));
                                            }
                                        },
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(24, 27, 30));
                                                children = new ArrayList<Tree<Integer>>(List.of(
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(22, 23));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(25, 26));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(28, 29));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(31, 32));
                                                            }
                                                        }
                                                                                               ));
                                            }
                                        },
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(38, 43));
                                                children = new ArrayList<Tree<Integer>>(List.of(
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(34, 35, 36, 37));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(39, 40, 41, 42));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(44, 45, 46));
                                                            }
                                                        }
                                                                                               ));
                                            }
                                        }
                                                                               ));
                            }
                        },
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(63, 76, 87));
                                children = new ArrayList<Tree<Integer>>(List.of(
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(50, 55, 59));
                                                children = new ArrayList<Tree<Integer>>(List.of(
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(48, 49));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(51, 52, 53, 54));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(56, 57, 58));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(60, 61, 62));
                                                            }
                                                        }
                                                                                               ));
                                            }
                                        },
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(67, 72));
                                                children = new ArrayList<Tree<Integer>>(List.of(
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(64, 65, 66));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(68, 69, 70, 71));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(73, 74, 75));
                                                            }
                                                        }
                                                                                               ));
                                            }
                                        },
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(80, 83));
                                                children = new ArrayList<Tree<Integer>>(List.of(
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(77, 78, 79));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(81, 82));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(84, 85, 86));
                                                            }
                                                        }
                                                                                               ));
                                            }
                                        },
                                        new Tree<Integer>()
                                        {
                                            {
                                                depth = 2;
                                                values = new ArrayList<Integer>(List.of(91, 96));
                                                children = new ArrayList<Tree<Integer>>(List.of(
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(88, 89, 90));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(92, 93, 94, 95));
                                                            }
                                                        },
                                                        new Tree<Integer>()
                                                        {
                                                            {
                                                                depth = 3;
                                                                values = new ArrayList<Integer>(List.of(97, 98, 99));
                                                            }
                                                        }
                                                                                               ));
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
        assertEquals(0, new BTree<Integer, String>().size());
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

    //region isEmpty()
    @Test
    public void isEmpty_EmptyTree_ReturnsTrue()
    {
        assertTrue(new BTree<Integer, String>().isEmpty());
    }

    @Test
    public void isEmpty_TreeWithElements_ReturnsFalse()
    {
        var nodes = new HashMap<Integer, String>();
        nodes.put(1, "one");
        assertFalse(new BTree<Integer, String>(DEFAULT_MAX_SIZE, nodes).isEmpty());
    }
    //endregion

    //region values()
    @Test
    public void values_EmptyTree_ReturnsEmptyCollection()
    {
        assertTrue(new BTree<Integer, String>().values().isEmpty());
    }

    @Test
    public void values_TreeWithElements_ReturnsCorrectValues()
    {
        var nodes = new HashMap<Integer, String>();
        nodes.put(1, "one");
        nodes.put(2, "two");
        nodes.put(3, "three");
        nodes.put(4, "four");
        var tree = new BTree<Integer, String>(DEFAULT_MAX_SIZE, nodes);
        var values = tree.values();
        assertTrue(values.contains("one"));
        assertTrue(values.contains("two"));
        assertTrue(values.contains("three"));
        assertTrue(values.contains("four"));
    }

    @Test
    public void values_TreeWithElements_ReturnsSorted()
    {
        var nodes = new HashMap<Integer, String>();
        nodes.put(4, "four");
        nodes.put(3, "three");
        nodes.put(2, "two");
        nodes.put(1, "one");
        var tree = new BTree<Integer, String>(DEFAULT_MAX_SIZE, nodes);
        var values = tree.values();
        var iterator = values.iterator();
        assertEquals("one", iterator.next());
        assertEquals("two", iterator.next());
        assertEquals("three", iterator.next());
        assertEquals("four", iterator.next());
    }

    @Test
    public void values_TreeWithMultipleElements_SingleSplit_ReturnsCorrectValues()
    {
        var list = generateList(4);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        var values = tree.values();
        for (var tuple : list) {
            assertTrue(values.contains(tuple.getValue()));
        }
    }

    @Test
    public void values_TreeWithMultipleElements_SingleSplit_ReturnsSorted()
    {
        var list = generateList(4);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        var values = tree.values();
        list.sort(new TupleComparator<Integer, String>());
        var iterator = values.iterator();
        for (var tuple : list) {
            assertEquals(tuple.getValue(), iterator.next());
        }
    }

    @Test
    public void values_TreeWithMultipleElements_DoubleSplit_ReturnsCorrectValues()
    {
        var list = generateList(10);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        var values = tree.values();
        for (var tuple : list) {
            assertTrue(values.contains(tuple.getValue()));
        }
    }

    @Test
    public void values_TreeWithMultipleElements_DoubleSplit_ReturnsSorted()
    {
        var list = generateList(10);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        var values = tree.values();
        list.sort(new TupleComparator<Integer, String>());
        var iterator = values.iterator();
        for (var tuple : list) {
            assertEquals(tuple.getValue(), iterator.next());
        }
    }
    //endregion

    //region entrySet()
    @Test
    public void entrySet_EmptyTree_ReturnsEmptySet()
    {
        assertTrue(new BTree<Integer, String>().entrySet().isEmpty());
    }

    @Test
    public void entrySet_TreeWithElements_ReturnsCorrectEntries()
    {
        var nodes = new HashMap<Integer, String>();
        nodes.put(1, "one");
        nodes.put(2, "two");
        nodes.put(3, "three");
        nodes.put(4, "four");
        var tree = new BTree<Integer, String>(DEFAULT_MAX_SIZE, nodes);
        var entries = tree.entrySet();
        for (var node : nodes.entrySet()) {
            assertTrue(entries.contains(node));
        }
    }
    //endregion

    //region get(Object key)
    @Test
    public void get_NullKey_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().get(null));
    }

    @Test
    public void get_WrongObjectType_ReturnsNull()
    {
        assertNull(new BTree<Integer, String>().get("some string"));
    }

    @Test
    public void get_KeyNotInTree_ReturnsNull()
    {
        assertNull(new BTree<Integer, String>().get(1));
    }

    @Test
    public void get_KeyInTree_ReturnsCorrectValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertEquals("one", tree.get(1));
    }

    @Test
    public void get_TreeWithMultipleElements_SingleSplit_KeyInFirstNode_ReturnsCorrectValues()
    {
        var list = generateList(5);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        assertEquals(list.get(0).getValue(), tree.get(list.get(0).getKey()));
        assertEquals(list.get(2).getValue(), tree.get(list.get(2).getKey()));
        assertEquals(list.get(4).getValue(), tree.get(list.get(4).getKey()));
    }
    //endregion

    //region put(K key, V value)

    //endregion

    //region putAll(Map<? extends K, ? extends V> m)

    //endregion

    //region remove(Object key)

    //endregion

    //region remove(Object key, Object value)

    //endregion

    //region replace(K key, V newValue)

    //endregion

    //region replace(K key, V oldValue, V newValue)

    //endregion

    //region containsKey(Object key)

    //endregion

    //region containsValue(Object value)

    //endregion

    //region equals(Object o)

    //endregion

    //region clear()

    //endregion

    //region keySet()

    //endregion

}
