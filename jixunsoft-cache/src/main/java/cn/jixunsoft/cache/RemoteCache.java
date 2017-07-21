package cn.jixunsoft.cache;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Danfo Yam
 */
public interface RemoteCache<K, V> {

    public V get(K key);

    public boolean remove(K key);

    public boolean put(K key, V value, long timeout);

    public boolean replace(K key, V value, long timeout);

    public Map<K, V> getKeys(List<K> keys);

    public Map<K, V> getKeys(K... keys);
}
