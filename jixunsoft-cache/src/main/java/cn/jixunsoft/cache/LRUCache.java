/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.jixunsoft.cache;

import java.util.LinkedHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Danfo Yam
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> implements LocalCache<K, V> {

    /**
     * Serial ID
     */
    private static final long serialVersionUID = -8920535562932907269L;

    /**
     * 默认最大容量
     */
    private static final int DEFAULT_MAX_CAPACITY = 2 << 18;
    private static final int INITIALCAPACITY = 2 << 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private final Lock lock = new ReentrantLock();
    private int maxCapacity;

    public LRUCache() {
        this(DEFAULT_MAX_CAPACITY);
    }

    public LRUCache(int maxCapacity) {
        super(INITIALCAPACITY, DEFAULT_LOAD_FACTOR, Boolean.TRUE);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
        return size() > maxCapacity;
    }

    @Override
    public V get(Object key) {
        try {
            lock.lock();
            return super.get(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        try {
            lock.lock();
            return super.put(key, value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public V remove(Object key) {
        try {
            lock.lock();
            return super.remove(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        try {
            lock.lock();
            return super.containsKey(key);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        try {
            lock.lock();
            super.clear();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int size() {
        try {
            lock.lock();
            return super.size();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获得maxCapacity
     *
     * @return the maxCapacity
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * 设置maxCapacity
     *
     * @param maxCapacity the maxCapacity to set
     */
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
