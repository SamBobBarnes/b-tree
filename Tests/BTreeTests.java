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

    //region constructors

    //endregion

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

    //region Removal
    @Test
    public void Removal_CorrectSingleNode()
    {
        var tree = new Tree<Integer>()
        {
            {
                depth = 0;
                values = new ArrayList<Integer>(List.of(2, 3, 4));
            }
        };
        BTree<Integer, String> bTree = new BTree<Integer, String>();
        bTree.put(1, "one");
        bTree.put(2, "two");
        bTree.put(3, "three");
        bTree.put(4, "four");
        bTree.remove(1);
        assertEquals(tree, bTree.toTree());
    }

    @Test
    public void Removal_CorrectSingleSplit_MergeUp()
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
                children = new ArrayList<Tree<Integer>>();
                values = new ArrayList<Integer>(List.of(1,2,4,5));
            }
        };
        bTree.remove(list.get(2).getKey());
        assertEquals(tree, bTree.toTree());
    }

    @Test
    public void Removal_CorrectDoubleSplit_TakeFromSiblingLeft()
    {
        var list = generateList(10, true);

        BTree<Integer, String> bTree = new BTree<Integer, String>();
        for (var tuple : list) {
            bTree.put(tuple.getKey(), tuple.getValue());
        }
        bTree.remove(4);
        bTree.remove(5);

        var tree = new Tree<Integer>()
        {
            {
                depth = 0;
                children = new ArrayList<Tree<Integer>>(List.of(
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(0,1));
                            }
                        },
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(3,6));
                            }
                        },
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(8,9));
                            }
                        }
                                                               ));
                values = new ArrayList<Integer>(List.of(2,7));
            }
        };
        assertEquals(tree, bTree.toTree());
    }

    @Test
    public void Removal_CorrectDoubleSplit_TakeFromSiblingRight()
    {
        var list = generateList(10, true);

        BTree<Integer, String> bTree = new BTree<Integer, String>();
        for (var tuple : list) {
            bTree.put(tuple.getKey(), tuple.getValue());
        }

        bTree.remove(1);
        bTree.remove(2);

        var tree = new Tree<Integer>()
        {
            {
                depth = 0;
                children = new ArrayList<Tree<Integer>>(List.of(
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(0,3));
                            }
                        },
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(5,6));
                            }
                        },
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(8,9));
                            }
                        }
                                                               ));
                values = new ArrayList<Integer>(List.of(4,7));
            }
        };
        assertEquals(tree, bTree.toTree());
    }

    @Test
    public void Removal_CorrectDoubleSplit_MergeSibling()
    {
        var list = generateList(10, true);

        BTree<Integer, String> bTree = new BTree<Integer, String>();
        for (var tuple : list) {
            bTree.put(tuple.getKey(), tuple.getValue());
        }
        bTree.remove(1);
        bTree.remove(2);
        bTree.remove(3);

        var tree = new Tree<Integer>()
        {
            {
                depth = 0;
                children = new ArrayList<Tree<Integer>>(List.of(
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(0,4,5,6));
                            }
                        },
                        new Tree<Integer>()
                        {
                            {
                                depth = 1;
                                values = new ArrayList<Integer>(List.of(8, 9));
                            }
                        }
                                                               ));
                values = new ArrayList<Integer>(List.of(7));
            }
        };
        assertEquals(tree, bTree.toTree());
    }
    //endregion
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

    @SuppressWarnings("SuspiciousMethodCalls")
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
    @Test
    public void put_NullKey_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().put(null, "one"));
    }

    @Test
    public void put_NullValue_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().put(1, null));
    }

    @Test
    public void put_KeyValuePair_AddsNodeToTree()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertEquals("one", tree.get(1));
    }

    @Test
    public void put_KeyValuePair_UpdatesNodeInTree()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(1, "new one");
        assertEquals("new one", tree.get(1));
    }

    @Test
    public void put_KeyValuePair_SingleSplit_AddsNodeToTree()
    {
        var list = generateList(5);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        tree.put(6, "six");
        assertEquals("six", tree.get(6));
    }

    @Test
    public void put_KeyValuePair_DoubleSplit_AddsNodeToTree()
    {
        var list = generateList(10);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        tree.put(11, "eleven");
        assertEquals("eleven", tree.get(11));
    }
    //endregion

    //region putAll(Map<? extends K, ? extends V> m)
    @SuppressWarnings("DataFlowIssue")
    @Test
    public void putAll_NullMap_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().putAll(null));
    }

    @Test
    public void putAll_EmptyMap_DoesNothing()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.putAll(new HashMap<Integer, String>());
        assertEquals("one", tree.get(1));
    }

    @Test
    public void putAll_MapWithElements_AddsAllNodesToTree()
    {
        var nodes = new HashMap<Integer, String>();
        nodes.put(1, "one");
        nodes.put(2, "two");
        nodes.put(3, "three");
        nodes.put(4, "four");
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.putAll(nodes);
        for (var node : nodes.entrySet()) {
            assertEquals(node.getValue(), tree.get(node.getKey()));
        }
    }
    //endregion

    //region remove(Object key)
    @Test
    public void remove_NullKey_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().remove(null));
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Test
    public void remove_WrongObjectType_ReturnsNull()
    {
        assertNull(new BTree<Integer, String>().remove("some string"));
    }

    @Test
    public void remove_KeyNotInTree_ReturnsNull()
    {
        assertNull(new BTree<Integer, String>().remove(1));
    }

    @Test
    public void remove_KeyInTree_ReturnsCorrectValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertEquals("one", tree.remove(1));
    }

    @Test
    public void remove_KeyInTree_RemovesNodeFromTree()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.remove(1);
        assertNull(tree.get(1));
    }

    @Test
    public void remove_TreeWithMultipleElements_SingleSplit_KeyInFirstNode_ReturnsCorrectValues()
    {
        var list = generateList(5);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        assertEquals(list.getFirst().getValue(), tree.remove(list.getFirst().getKey()));
        assertNull(tree.get(list.getFirst().getKey()));
    }
    //endregion

    //region remove(Object key, Object value)
    @Test
    public void removeExact_NullKey_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().remove(null, "one"));
    }

    @Test
    public void removeExact_NullValue_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().remove(1, null));
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Test
    public void removeExact_WrongObjectType_ReturnsFalse()
    {
        assertFalse(new BTree<Integer, String>().remove("some string", "one"));
    }

    @Test
    public void removeExact_KeyNotInTree_ReturnsFalse()
    {
        assertFalse(new BTree<Integer, String>().remove(1, "one"));
    }

    @Test
    public void removeExact_ValueNotInTree_ReturnsFalse()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertFalse(tree.remove(1, "two"));
    }

    @Test
    public void removeExact_KeyInTree_ValueInTree_ReturnsTrue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertTrue(tree.remove(1, "one"));
    }

    @Test
    public void removeExact_KeyInTree_ValueInTree_RemovesNodeFromTree()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.remove(1, "one");
        assertNull(tree.get(1));
    }

    @Test
    public void removeExact_TreeWithMultipleElements_SingleSplit_KeyInFirstNode_ReturnsCorrectValues()
    {
        var list = generateList(5);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        assertTrue(tree.remove(list.getFirst().getKey(), list.getFirst().getValue()));
        assertNull(tree.get(list.getFirst().getKey()));
    }
    //endregion

    //region replace(K key, V newValue)
    @Test
    public void replace_NullKey_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().replace(null, "one"));
    }

    @Test
    public void replace_NullValue_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().replace(1, null));
    }

    @Test
    public void replace_KeyNotInTree_ReturnsNull()
    {
        assertNull(new BTree<Integer, String>().replace(1, "one"));
    }

    @Test
    public void replace_KeyInTree_ReturnsOldValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertEquals("one", tree.replace(1, "new one"));
    }

    @Test
    public void replace_KeyInTree_ReplacesValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.replace(1, "new one");
        assertEquals("new one", tree.get(1));
    }

    @Test
    public void replace_TreeWithMultipleElements_SingleSplit_KeyInFirstNode_ReturnsCorrectValues()
    {
        var list = generateList(5);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        assertEquals(list.getFirst().getValue(), tree.replace(list.getFirst().getKey(), "new one"));
    }
    //endregion

    //region replace(K key, V oldValue, V newValue)
    @Test
    public void replaceExact_NullKey_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().replace(null, "old one", "new one"));
    }

    @Test
    public void replaceExact_NullOldValue_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().replace(1, null, "new one"));
    }

    @Test
    public void replaceExact_NullNewValue_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().replace(1, "old one", null));
    }

    @Test
    public void replaceExact_KeyNotInTree_ReturnsFalse()
    {
        assertFalse(new BTree<Integer, String>().replace(1, "old one", "new one"));
    }

    @Test
    public void replaceExact_OldValueNotInTree_ReturnsFalse()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertFalse(tree.replace(1, "old two", "new one"));
    }

    @Test
    public void replaceExact_KeyInTree_OldValueInTree_ReturnsTrue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertTrue(tree.replace(1, "one", "new one"));
    }

    @Test
    public void replaceExact_KeyInTree_OldValueInTree_ReplacesValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.replace(1, "one", "new one");
        assertEquals("new one", tree.get(1));
    }

    @Test
    public void replaceExact_TreeWithMultipleElements_SingleSplit_KeyInFirstNode_ReturnsCorrectValues()
    {
        var list = generateList(5);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        assertTrue(tree.replace(list.getFirst().getKey(), list.getFirst().getValue(), "new one"));
        assertEquals("new one", tree.get(list.getFirst().getKey()));
    }
    //endregion

    //region containsKey(Object key)
    @Test
    public void containsKey_NullKey_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().containsKey(null));
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Test
    public void containsKey_WrongObjectType_ReturnsFalse()
    {
        assertFalse(new BTree<Integer, String>().containsKey("some string"));
    }

    @Test
    public void containsKey_KeyNotInTree_ReturnsFalse()
    {
        assertFalse(new BTree<Integer, String>().containsKey(1));
    }

    @Test
    public void containsKey_KeyInTree_ReturnsTrue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertTrue(tree.containsKey(1));
    }

    @Test
    public void containsKey_TreeWithMultipleElements_SingleSplit_KeyInFirstNode_ReturnsTrue()
    {
        var list = generateList(5);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        assertTrue(tree.containsKey(list.getFirst().getKey()));
    }
    //endregion

    //region containsValue(Object value)
    @Test
    public void containsValue_NullValue_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class, () -> new BTree<Integer, String>().containsValue(null));
    }

    @Test
    public void containsValue_ValueNotInTree_ReturnsFalse()
    {
        assertFalse(new BTree<Integer, String>().containsValue("one"));
    }

    @Test
    public void containsValue_ValueInTree_ReturnsTrue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertTrue(tree.containsValue("one"));
    }

    @Test
    public void containsValue_TreeWithMultipleElements_SingleSplit_ValueInFirstNode_ReturnsTrue()
    {
        var list = generateList(5);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        assertTrue(tree.containsValue(list.getFirst().getValue()));
    }
    //endregion

    //region equals(Object o)
    @Test
    public void equals_NullObject_ReturnsFalse()
    {
        assertNotEquals(null, new BTree<Integer, String>());
    }

    @Test
    public void equals_SameObject_ReturnsTrue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertEquals(tree, tree);
    }

    @Test
    public void equals_DifferentReferences_ReturnsTrue()
    {
        assertEquals(new BTree<Integer, String>(), new BTree<Integer, String>());
    }

    @Test
    public void equals_DifferentTrees_ReturnsFalse()
    {
        var nodes1 = new HashMap<Integer, String>();
        nodes1.put(1, "one");
        nodes1.put(2, "two");
        nodes1.put(3, "three");
        nodes1.put(4, "four");
        var tree1 = new BTree<Integer, String>(DEFAULT_MAX_SIZE, nodes1);

        var nodes2 = new HashMap<Integer, String>();
        nodes2.put(1, "one");
        nodes2.put(2, "two");
        nodes2.put(3, "three");
        nodes2.put(4, "four");
        nodes2.put(5, "five");
        var tree2 = new BTree<Integer, String>(DEFAULT_MAX_SIZE, nodes2);

        assertNotEquals(tree1, tree2);
    }

    @Test
    public void equals_SameTrees_ReturnsTrue()
    {
        var nodes = new HashMap<Integer, String>();
        nodes.put(1, "one");
        nodes.put(2, "two");
        nodes.put(3, "three");
        nodes.put(4, "four");
        BTree<Integer, String> tree1 = new BTree<Integer, String>(DEFAULT_MAX_SIZE, nodes);
        BTree<Integer, String> tree2 = new BTree<Integer, String>(DEFAULT_MAX_SIZE, nodes);
        assertEquals(tree1, tree2);
    }
    //endregion

    //region clear()
    @Test
    public void clear_TreeWithElements_RemovesAllNodes()
    {
        var nodes = new HashMap<Integer, String>();
        nodes.put(1, "one");
        nodes.put(2, "two");
        nodes.put(3, "three");
        nodes.put(4, "four");
        BTree<Integer, String> tree = new BTree<Integer, String>(DEFAULT_MAX_SIZE, nodes);
        tree.clear();
        assertTrue(tree.isEmpty());
    }
    //endregion

    //region keySet()
    @Test
    public void keySet_EmptyTree_ReturnsEmptySet()
    {
        assertTrue(new BTree<Integer, String>().keySet().isEmpty());
    }

    @Test
    public void keySet_TreeWithElements_ReturnsCorrectKeys()
    {
        var nodes = new HashMap<Integer, String>();
        nodes.put(1, "one");
        nodes.put(2, "two");
        nodes.put(3, "three");
        nodes.put(4, "four");
        BTree<Integer, String> tree = new BTree<Integer, String>(DEFAULT_MAX_SIZE, nodes);
        var keys = tree.keySet();
        for (var node : nodes.entrySet()) {
            assertTrue(keys.contains(node.getKey()));
        }
    }

    @Test
    public void keySet_TreeWithElements_ReturnsSorted()
    {
        var nodes = new HashMap<Integer, String>();
        nodes.put(4, "four");
        nodes.put(3, "three");
        nodes.put(2, "two");
        nodes.put(1, "one");
        BTree<Integer, String> tree = new BTree<Integer, String>(DEFAULT_MAX_SIZE, nodes);
        var keys = tree.keySet();
        var iterator = keys.iterator();
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
        assertEquals(4, iterator.next());
    }

    @Test
    public void keySet_TreeWithMultipleElements_SingleSplit_ReturnsCorrectKeys()
    {
        var list = generateList(4);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        var keys = tree.keySet();
        for (var tuple : list) {
            assertTrue(keys.contains(tuple.getKey()));
        }
    }

    @Test
    public void keySet_TreeWithMultipleElements_SingleSplit_ReturnsSorted()
    {
        var list = generateList(4);
        BTree<Integer, String> tree = new BTree<Integer, String>();
        for (var tuple : list) {
            tree.put(tuple.getKey(), tuple.getValue());
        }
        var keys = tree.keySet();
        list.sort(new TupleComparator<Integer, String>());
        var iterator = keys.iterator();
        for (var tuple : list) {
            assertEquals(tuple.getKey(), iterator.next());
        }
    }
    //endregion

}
