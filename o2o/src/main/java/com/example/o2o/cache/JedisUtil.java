package com.example.o2o.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

import java.util.Set;

public class JedisUtil {
    // manipulating on keys and strings  (one type key-value pair)
    public Keys KEYS;
    public Strings STRINGS;


    //public Strings STRINGS;
    private JedisPool jedisPool; //dependency injection by Spring Bean factory

    public JedisPool getJedisPool(){
        return jedisPool;
    }

    public void setJedisPool(JedisPoolWriper jedisPoolWriper) {
        this.jedisPool = jedisPoolWriper.getJedisPool();
    }

    /**
     * get jedis object from connection pool
     *
     * @return
     */
    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public Keys getKEYS() {
        return KEYS;
    }

    public void setKEYS(Keys KEYS) {
        this.KEYS = KEYS;
    }

    public Strings getSTRINGS() {
        return STRINGS;
    }

    public void setSTRINGS(Strings STRINGS) {
        this.STRINGS = STRINGS;
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

        /**
         * judge if an input key exists
         * @param key
         * @return
         */
        public boolean exists(String key){
            Jedis jedis = getJedis();
            boolean exis = jedis.exists(key);
            jedis.close();
            return exis;
        }

        /**
         * get all the keys which match the input pattern
         * @param pattern  expression * represents any multiple characters
         *                             ? represents any one character
         * @return
         */
        public Set<String> keys(String pattern){
            Jedis jedis = getJedis();
            Set<String> set = jedis.keys(pattern);
            jedis.close();
            return set;
        }
    }

    //****************************INNER CLASS String *************************************//
    public class Strings{
        /**
         * Given a key, return its value
         * @param key
         * @return
         */
        public String get(String key){
            Jedis jedis = getJedis();
            String value = jedis.get(key);
            jedis.close();
            return value;
        }

        /**
         * Given a key, update or add its value
         * @param key
         * @param value
         * @return
         */
        public String set(String key, String value){
            return set(SafeEncoder.encode(key),SafeEncoder.encode(value));
        }

        /**
         * Given
         * @param key
         * @param value
         * @return
         */
        public String set(byte[] key,byte[] value){
            Jedis jedis =getJedis();
            String status = jedis.set(key,value);
            jedis.close();
            return status;
        }

    }


}
