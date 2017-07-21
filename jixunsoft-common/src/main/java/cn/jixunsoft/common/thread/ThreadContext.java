package cn.jixunsoft.common.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程容器
 * 
 * @author Danfo Yam
 *
 * 2013年12月18日
 *
 */
public class ThreadContext {

    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    private static ThreadLocal<Map<String, Object>> getThreadLocal() {
        if (threadLocal.get() == null) {
            Map<String, Object> map = new ConcurrentHashMap<String, Object>();
            threadLocal.set(map);
        }
        return threadLocal;
    }

    public static Object get(String key) {
        return getContainer().get(key);
    }

    public static Map<String, Object> getContainer() {
        return (Map<String, Object>) getThreadLocal().get();
    }

    public static boolean contains(String key) {
        return getContainer().containsKey(key);
    }

    public static boolean put(String key, Object value, boolean isForce) {
        if (contains(key) && !isForce) {
            return false;
        }
        getContainer().put(key, value);
        return true;
    }

    public static boolean put(String key, Object value) {
        return put(key, value, true);
    }

    public static boolean remove(String key) {
        return getContainer().remove(key) == null;
    }

    public static void destory() {
        getContainer().clear();
        getThreadLocal().set(null);
    }

    public static void init(Map<String, Object> container) {
        ThreadContext.destory();
        if (container == null) {
            return;
        }
        threadLocal.set(container);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public static String format2String() {
        Map<String, Object> map = threadLocal.get();
        return "ThreadContext: [" + map.toString() + "]";
    }
}
