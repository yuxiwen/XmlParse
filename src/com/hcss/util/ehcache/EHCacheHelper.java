package com.hcss.util.ehcache;

import java.net.URL;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EHCacheHelper {

    private static Log LOG = LogFactory.getLog(EHCacheHelper.class);

	private static CacheManager CacheManager = null;

	static {
		URL url = null;

		try {
			/**
			 * 允许在根目录下放置，自定义
			 */
			url = EHCacheHelper.class.getClassLoader().getResource("ehcache.xml");

		} catch (Throwable e) {

			url = EHCacheHelper.class.getClassLoader().getResource("com/hcss/common/ehcache/ehcache.xml");
		}
		if(url == null){

			url = EHCacheHelper.class.getClassLoader().getResource(	"com/hcss/common/ehcache/ehcache.xml");
		}

		CacheManager = new CacheManager(url);
	}

	private static String modifyName(String cacheName) {

		if (cacheName.indexOf("?") != -1) {
			cacheName = cacheName.replaceAll("\\?", "W_W_W");
		}
		if (cacheName.indexOf("*") != -1) {
			cacheName = cacheName.replaceAll("\\*", "I_I_I");
		}
		if (cacheName.indexOf("\\") != -1) {
			cacheName = cacheName.replaceAll("\\\\", "U_U_U");
		}
		if (cacheName.indexOf("/") != -1) {
			cacheName = cacheName.replaceAll("/", "O_O_O");
		}

		return cacheName;
	}

	public static boolean exists(String cacheName) {

		cacheName = modifyName(cacheName);

		return CacheManager.cacheExists(cacheName);
	}

	public static void removeCache(String cacheName) {

		if (exists(cacheName)) {

			CacheManager.removeCache(modifyName(cacheName));

			LOG.debug("remove cache[" + cacheName + "]");
		}
	}

	public static Cache getCache(String cacheName) {

		cacheName = modifyName(cacheName);

		if (!CacheManager.cacheExists(cacheName)) {

			synchronized (EHCacheHelper.class) {

				if (!CacheManager.cacheExists(cacheName)) {

					addCache(cacheName);
				}
			}
			LOG.debug("cache[" + cacheName + "] is null,create a new cache");
		}
		Cache cache = CacheManager.getCache(cacheName);

		LOG.debug("return cache[" + cacheName + "]");

		return cache;
	}

	public static Cache addCache(String cacheName,long idle,long live) {

		cacheName = modifyName(cacheName);

		CacheManager.addCache(cacheName);

		Cache cache = CacheManager.getCache(cacheName);
		cache.getCacheConfiguration().setDiskPersistent(false);
		cache.getCacheConfiguration().setTimeToIdleSeconds(idle);
		cache.getCacheConfiguration().setTimeToLiveSeconds(live);

		LOG.debug("add cache[" + cacheName + "]");

		return cache;
	}

	public static Cache addCache(String cacheName) {
		cacheName = modifyName(cacheName);

		CacheManager.addCache(cacheName);

		Cache cache = CacheManager.getCache(cacheName);
		cache.getCacheConfiguration().setTimeToIdleSeconds(600);
		cache.getCacheConfiguration().setTimeToLiveSeconds(600);
		cache.getCacheConfiguration().setMaxElementsInMemory(3000);

		LOG.debug("add cache[" + cacheName + "]");

		return cache;
	}

	public static void put(String cacheName, Object key, Object object) {

	    getCache(cacheName).put(new Element(key, object));

		LOG.debug("add cache[" + cacheName + "] key[" + key + "] value[" + object + "]");
	}

	public static void put(Object key, Object object) {

	    put("mycache", key, object);
	}

	public static Object get(Object key) {

	    return get("mycache", key);
	}

	public static Object get(String cacheName, Object key) {

	    Element ele = getCache(cacheName).get(key);

	    Object value = null;

	    if (ele != null) value = ele.getObjectValue();

		LOG.debug("get cache[" + cacheName + "] key[" + key + "] value[" + value + "]");

		return value;
	}

	public static void remove(String cacheName, Object key) {

	    getCache(cacheName).remove(key);

		LOG.debug("remove cache[" + cacheName + "] key[" + key + "]");
	}

	public static void remove(Object key) {

	    remove("mycache", key);
	}

}
