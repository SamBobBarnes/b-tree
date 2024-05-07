import org.junit.jupiter.api.Test;
import BTree.BTree;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BTreeTests
{
    @Test
    public void Constructor_EmptyTree()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertTrue(tree.isEmpty());
    }

    @Test
    public void Put_SingleElement()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertEquals(1, tree.size());
    }

    @Test
    public void Put_MultipleElements()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        assertEquals(3, tree.size());
    }

    @Test
    public void Get_SingleElement()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertEquals("one", tree.get(1));
    }

    @Test
    public void Get_MultipleElements()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        assertEquals("one", tree.get(1));
        assertEquals("two", tree.get(2));
        assertEquals("three", tree.get(3));
    }

    @Test
    public void Remove_SingleElement()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.remove(1);
        assertTrue(tree.isEmpty());
    }

    @Test
    public void Remove_MultipleElements()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        tree.remove(1);
        tree.remove(2);
        tree.remove(3);
        assertTrue(tree.isEmpty());
    }

    @Test
    public void Contains_SingleElement()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertTrue(tree.containsValue("one"));
    }

    @Test
    public void Contains_MultipleElements()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        assertTrue(tree.containsValue("one"));
        assertTrue(tree.containsValue("two"));
        assertTrue(tree.containsValue("three"));
    }

    @Test
    public void Replace_SingleElement()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.replace(1, "two");
        assertEquals("two", tree.get(1));
    }

    @Test
    public void Replace_MultipleElements()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        tree.replace(1, "four");
        tree.replace(2, "five");
        tree.replace(3, "six");
        assertEquals("four", tree.get(1));
        assertEquals("five", tree.get(2));
        assertEquals("six", tree.get(3));
    }

    @Test
    public void Keys_SingleElement()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        var keys = Collections.list(tree.keys()).toArray(new Integer[0]);
        var expected = new Integer[] {1};
        assertArrayEquals(expected, keys);
    }

    @Test
    public void Keys_MultipleElements()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        var keys = Collections.list(tree.keys()).toArray(new Integer[0]);
        var expected = new Integer[] {1, 2, 3};
        assertArrayEquals(expected, keys);
    }

    @Test
    public void Values_SingleElement()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        var values = Collections.list(tree.elements()).toArray(new String[0]);
        var expected = new String[] {"one"};
        assertArrayEquals(expected, values);
    }

    @Test
    public void Values_MultipleElements()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        var values = Collections.list(tree.elements()).toArray(new String[0]);
        var expected = new String[] {"one", "two", "three"};
        assertArrayEquals(expected, values);
    }

    @Test
    public void PutAll()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        var map = Map.of(1, "one", 2, "two", 3, "three");
        tree.putAll(map);
        assertEquals(3, tree.size());
        assertEquals("one", tree.get(1));
        assertEquals("two", tree.get(2));
        assertEquals("three", tree.get(3));
    }

    @Test
    public void Remove_KeyValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        assertTrue(tree.remove(1, "one"));
        assertTrue(tree.remove(2, "two"));
        assertTrue(tree.remove(3, "three"));
        assertTrue(tree.isEmpty());
    }

    @Test
    public void Remove_KeyValue_False()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        assertFalse(tree.remove(1, "two"));
        assertFalse(tree.remove(2, "three"));
        assertFalse(tree.remove(3, "one"));
        assertEquals(3, tree.size());
    }

    @Test
    public void Clear()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        tree.clear();
        assertTrue(tree.isEmpty());
    }

    @Test
    public void ContainsValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        assertTrue(tree.containsValue("one"));
        assertTrue(tree.containsValue("two"));
        assertTrue(tree.containsValue("three"));
    }

    @Test
    public void ContainsValue_False()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        assertFalse(tree.containsValue("four"));
    }

    @Test
    public void ContainsKey()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        assertTrue(tree.containsKey(1));
        assertTrue(tree.containsKey(2));
        assertTrue(tree.containsKey(3));
    }

    @Test
    public void ContainsKey_False()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        assertFalse(tree.containsKey(4));
    }

    @Test
    public void Values()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        var values = tree.values().toArray(new String[0]);
        var expected = new String[] {"one", "two", "three"};
        assertArrayEquals(expected, values);
    }

    @Test
    public void Size()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertEquals(0, tree.size());
        tree.put(1, "one");
        assertEquals(1, tree.size());
        tree.put(2, "two");
        assertEquals(2, tree.size());
        tree.put(3, "three");
        assertEquals(3, tree.size());
        tree.remove(1);
        assertEquals(2, tree.size());
        tree.remove(2);
        assertEquals(1, tree.size());
        tree.remove(3);
        assertEquals(0, tree.size());
    }

    @Test
    public void IsEmpty()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertTrue(tree.isEmpty());
        tree.put(1, "one");
        assertFalse(tree.isEmpty());
        tree.remove(1);
        assertTrue(tree.isEmpty());
    }

    @Test
    public void Put_NullKey()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.put(null, "one"));
    }

    @Test
    public void Put_NullValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.put(1, null));
    }

    @Test
    public void Get_NullKey()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.get(null));
    }

    @Test
    public void Remove_NullKey()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.remove(null));
    }

    @Test
    public void Remove_KeyValue_NullKey()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.remove(null, "one"));
    }

    @Test
    public void Remove_KeyValue_NullValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.remove(1, null));
    }

    @Test
    public void Replace_NullKey()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.replace(null, "one"));
    }

    @Test
    public void Replace_NullValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.replace(1, null));
    }

    @Test
    public void Contains_NullValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.containsValue(null));
    }

    @Test
    public void ContainsKey_NullKey()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.containsKey(null));
    }

    @Test
    public void PutAll_NullMap()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.putAll(null));
    }

    @Test
    public void ContainsValue_NullValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, () -> tree.containsValue(null));
    }

    @Test
    public void Values_NullValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, tree::values);
    }

    @Test
    public void ContainsKey_False_NullKey()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertFalse(tree.containsKey(null));
    }

    @Test
    public void ContainsValue_False_NullValue()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertFalse(tree.containsValue(null));
    }

    @Test
    public void Keys_NullKey()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, tree::keys);
    }

    @Test
    public void Values_NullKey()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertThrows(NullPointerException.class, tree::elements);
    }

    @Test
    public void Equals_Null()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertNotEquals(null, tree);
    }

    @Test
    public void Clear_EmptyTree()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.clear();
        assertTrue(tree.isEmpty());
    }

    @Test
    public void ToString_EmptyTree()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertEquals("{}", tree.toString());
    }

    @Test
    public void ToString_SingleElement()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        assertEquals("{1=one}", tree.toString());
    }

    @Test
    public void ToString_MultipleElements()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(1, "one");
        tree.put(2, "two");
        tree.put(3, "three");
        assertEquals("{1=one, 2=two, 3=three}", tree.toString());
    }

    @Test
    public void ToString_MultipleElements_Sorted()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        tree.put(3, "three");
        tree.put(2, "two");
        tree.put(1, "one");
        assertEquals("{1=one, 2=two, 3=three}", tree.toString());
    }

    @Test
    public void Equals_SameTree()
    {
        BTree<Integer, String> tree1 = new BTree<Integer, String>();
        BTree<Integer, String> tree2 = new BTree<Integer, String>();
        assertEquals(tree1, tree2);
    }

    @Test
    public void Equals_SameElements()
    {
        BTree<Integer, String> tree1 = new BTree<Integer, String>();
        tree1.put(1, "one");
        tree1.put(2, "two");
        tree1.put(3, "three");
        BTree<Integer, String> tree2 = new BTree<Integer, String>();
        tree2.put(1, "one");
        tree2.put(2, "two");
        tree2.put(3, "three");
        assertEquals(tree1, tree2);
    }

    @Test
    public void Equals_DifferentElements()
    {
        BTree<Integer, String> tree1 = new BTree<Integer, String>();
        tree1.put(1, "one");
        tree1.put(2, "two");
        tree1.put(3, "three");
        BTree<Integer, String> tree2 = new BTree<Integer, String>();
        tree2.put(1, "one");
        tree2.put(2, "two");
        tree2.put(4, "four");
        assertNotEquals(tree1, tree2);
    }

    @Test
    public void Equals_DifferentKeys()
    {
        BTree<Integer, String> tree1 = new BTree<Integer, String>();
        tree1.put(1, "one");
        tree1.put(2, "two");
        tree1.put(3, "three");
        BTree<Integer, String> tree2 = new BTree<Integer, String>();
        tree2.put(1, "one");
        tree2.put(3, "three");
        tree2.put(2, "two");
        assertNotEquals(tree1, tree2);
    }

    @Test
    public void Equals_DifferentValues()
    {
        BTree<Integer, String> tree1 = new BTree<Integer, String>();
        tree1.put(1, "one");
        tree1.put(2, "two");
        tree1.put(3, "three");
        BTree<Integer, String> tree2 = new BTree<Integer, String>();
        tree2.put(1, "one");
        tree2.put(2, "two");
        tree2.put(3, "four");
        assertNotEquals(tree1, tree2);
    }

    @Test
    public void Equals_DifferentTypes()
    {
        BTree<Integer, String> tree = new BTree<Integer, String>();
        assertNotEquals(tree, new Object());
    }
}
