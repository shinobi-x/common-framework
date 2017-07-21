package cn.jixunsoft.cache;

/**
 *
 * @author Danfo Yam
 */
public interface LocalCache<K, V> {

    public V get(Object key);

    public V put(K key, V value);

    public V remove(Object key);

    public boolean containsKey(Object key);

    public void clear();

    public int size();

    public int getMaxCapacity();
}
