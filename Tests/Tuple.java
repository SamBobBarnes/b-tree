import java.util.Comparator;

class Tuple<K, V>
{
    private final K _key;
    private V _value;

    public Tuple(K key, V value)
    {
        _key = key;
        _value = value;
    }

    public K getKey()
    {
        return _key;
    }

    public V getValue()
    {
        return _value;
    }

    public void setValue(V value)
    {
        _value = value;
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object o)
    {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (o.getClass() != this.getClass()) {
            return false;
        }

        Tuple<K, V> other = (Tuple<K, V>) o;
        return _key.equals(other.getKey()) && _value.equals(other.getValue());
    }

    public int hashCode()
    {
        return _key.hashCode() ^ _value.hashCode();
    }

    public String toString()
    {
        return "(" + _key + ", " + _value + ")";
    }
}

class TupleComparator<K extends Comparable<K>, V> implements Comparator<Tuple<K, V>>
{
    public int compare(Tuple<K, V> a, Tuple<K, V> b)
    {
        return a.getKey().compareTo(b.getKey());
    }
}
