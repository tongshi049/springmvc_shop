package com.example.o2o.cache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 */
public class JedisPoolWriper {
    // Redis connection pool object
    private JedisPool jedisPool;

    /**
     * Create a connection pool object given the parameters needed
     * @param poolConfig redis pool config
     * @param host host name, String type
     * @param port port number, int type
     */
    public JedisPoolWriper(final JedisPoolConfig poolConfig,final String host,final int port){
        try{
            jedisPool = new JedisPool(poolConfig,host,port);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
