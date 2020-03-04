package com.sise.pet.service.impl;

import com.sise.pet.exception.RedisConnectException;
import com.sise.pet.function.JedisExecutor;
import com.sise.pet.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * @ClassName RedisServiceImpl
 * @Description TODO
 * @Version 1.0
 **/
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    JedisPool jedisPool;

    private static String separator = System.getProperty("line.separator");

    private <T> T excuteByJedis(JedisExecutor<Jedis, T> j) throws RedisConnectException {
        try (Jedis jedis = jedisPool.getResource()) {
            return j.excute(jedis);
        } catch (Exception e) {
            throw new RedisConnectException(e.getMessage());
        }
    }


    @Override
    public Map<String, Object> getKeysSize() throws RedisConnectException {
        Long dbSize = this.excuteByJedis(
                j -> {
                    Client client = j.getClient();
                    client.dbSize();
                    return client.getIntegerReply();
                }
        );
        Map<String, Object> map = new HashMap<>();
        map.put("create_time", System.currentTimeMillis());
        map.put("dbSize", dbSize);
        return map;
    }

    @Override
    public Map<String, Object> getMemoryInfo() throws RedisConnectException {
        String info = this.excuteByJedis(
                j -> {
                    Client client = j.getClient();
                    client.info();
                    return client.getBulkReply();
                }
        );
        String[] strs = Objects.requireNonNull(info).split(separator);
        Map<String, Object> map = null;
        for (String s : strs) {
            String[] detail = s.split(":");
            if ("used_memory".equals(detail[0])) {
                map = new HashMap<>();
                map.put("used_memory", detail[1].substring(0, detail[1].length() - 1));
                map.put("create_time", System.currentTimeMillis());
                break;
            }
        }
        return map;
    }

    @Override
    public Set<String> getKeys(String pattern) throws RedisConnectException {
        return this.excuteByJedis(j -> j.keys(pattern));
    }

    @Override
    public String get(String key) throws RedisConnectException {
        return this.excuteByJedis(j -> j.get(key.toLowerCase()));
    }

    @Override
    public String set(String key, String value) throws RedisConnectException {
        return this.excuteByJedis(j -> j.set(key.toLowerCase(), value));
    }

    @Override
    public String set(String key, String value, Long milliscends) throws RedisConnectException {
        String result = this.set(key.toLowerCase(), value);
        this.pexpire(key, milliscends);
        return result;
    }

    @Override
    public Long del(String... key) throws RedisConnectException {
        return this.excuteByJedis(j -> j.del(key));
    }

    @Override
    public Boolean exists(String key) throws RedisConnectException {
        return this.excuteByJedis(j -> j.exists(key));
    }

    @Override
    public Long pttl(String key) throws RedisConnectException {
        return this.excuteByJedis(j -> j.pttl(key));
    }

    @Override
    public Long pexpire(String key, Long milliseconds) throws RedisConnectException {
        return this.excuteByJedis(j -> j.pexpire(key, milliseconds));
    }

    @Override
    public Long zadd(String key, Double score, String member) throws RedisConnectException {
        return this.excuteByJedis(j -> j.zadd(key, score, member));
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max) throws RedisConnectException {
        return this.excuteByJedis(j -> j.zrangeByScore(key, min, max));
    }

    @Override
    public Long zremrangeByScore(String key, String start, String end) throws RedisConnectException {
        return this.excuteByJedis(j -> j.zremrangeByScore(key, start, end));
    }

    @Override
    public Long zrem(String key, String... members) throws RedisConnectException {
        return this.excuteByJedis(j -> j.zrem(key, members));
    }
}
