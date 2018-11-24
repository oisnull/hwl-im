package com.hwl.im.server.redis;

import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

    public final static String HOST = "127.0.0.1";
    public final static int PORT = 6379;
    public final static String AUTH = "123456";

    public final static int MAX_ACTIVE = 1024;
    public final static int MAX_IDLE = 50;
    public final static int MAX_WAIT = 10000;
    public final static int TIMEOUT = 10000;

    public final static boolean TEST_ON_BORROW = true;
    private static JedisPool pool = null;

    private static Logger log = LogManager.getLogger(RedisUtil.class.getName());

    // store db , db config from c# redis config
    public final static int USER_TOKEN_DB = 0;
    public final static int USER_SESSION_DB = 2;
    public final static int GROUP_USER_SET_DB = 11;
    public final static int OFFLINE_MESSAGE_DB = 9;

    static {
        initPoolConfig();
    }

    private static void initPoolConfig() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            pool = new JedisPool(config, HOST, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("redis config init failed : " + e.getMessage());
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
            log.error("exec failed : " + e.getMessage());
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