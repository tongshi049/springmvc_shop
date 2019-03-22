package com.example.o2o.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class JedisUtil {
    //
    public Keys KEYS;
    //public Strings STRINGS;
    private JedisPool jedisPool;

    public JedisPool getJedisPool(){
        return jedisPool;
    }

    public void setJedisPool(JedisPoolWriper jedisPoolWriper) {
        this.jedisPool = jedisPoolWriper.getJedisPool();
    }

    /**
     * 从jedis连接池中获取获取jedis对象
     *
     * @return
     */
    public Jedis getJedis() {
        return jedisPool.getResource();
    }




    //****************************INNER CLASS Keys *************************************//
    public class Keys{
        /**
         * clean up all the keys
         * @return
         */
        public String flushAll(){
            Jedis jedis= getJedis();
            String stata = jedis.flushAll();
            jedis.close();
            return stata;
        }

        /**
         * delete key-value pairs
         * @param keys  multiple keys  are  allowed  at each time
         * @return how many keys you successfully deleted
         */
        public long del(String...keys){
            Jedis jedis= getJedis();
            long count = jedis.del(keys);
            jedis.close();
            return count;
        }

        public boolean exists(String key){
            Jedis jedis = getJedis();
            boolean exis = jedis.exists(key);
            jedis.close();
            return exis;
        }

        public Set<String> keys(String pattern){
            Jedis jedis = getJedis();
            Set<String> set = jedis.keys(pattern);
            jedis.close();
            return set;
        }
    }

    //****************************INNER CLASS String *************************************//


}
