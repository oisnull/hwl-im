package com.hwl.im.server.redis;

import java.util.function.Function;

import com.hwl.im.IMConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    public final static int TIMEOUT = 10000;
    private static JedisPool pool = null;

    // store db , db config from c# redis config
    public final static int USER_TOKEN_DB = 0;
    public final static int USER_SESSION_DB = 2;
    public final static int USER_SESSION_OFFLINE_DB = 2;
    public final static int GROUP_USER_SET_DB = 11;
    public final static int OFFLINE_MESSAGE_DB = 9;

    static {
        initPoolConfig();
    }

    private static void initPoolConfig() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            // 可用连接实例的最大数目，默认值为8；
            // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(1024);
            config.setMaxIdle(50);
            config.setMinIdle(8);
            config.setMaxWaitMillis(10000);
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);
            //Idle时进行连接扫描
            config.setTestWhileIdle(true);
            //表示idle object evitor两次扫描之间要sleep的毫秒数
            config.setTimeBetweenEvictionRunsMillis(30000);
            //表示idle object evitor每次扫描的最多的对象数
            config.setNumTestsPerEvictionRun(10);
            //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
            config.setMinEvictableIdleTimeMillis(60000);
            pool = new JedisPool(config, IMConfig.REDIS_HOST, IMConfig.REDIS_PORT, TIMEOUT, IMConfig.REDIS_AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T exec(int db, Function<Jedis, T> func, boolean autoClose) {
        Jedis client = client();
        try {
            if (db > 0) {
                client.select(db);
            }
            return func.apply(client);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (autoClose)
                close(client);
        }
        return null;
    }

    public static <T> T exec(int db, Function<Jedis, T> func) {
        return exec(db, func, true);
    }

    // public static <T> T exec(Function<Jedis, T> func) {
    //     return exec(0, func, true);
    // }

    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public static Jedis client() {
        return pool.getResource();
    }
}