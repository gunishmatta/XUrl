package com.crio.shorturl;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.RandomStringUtils;


class MyMap<K,V> extends HashMap<K, V>{

    Map<V,K> reverseMap = new HashMap<V,K>();

    @Override
    public V put(K key, V value) {
        reverseMap.put(value, key);
        return super.put(key, value);
    }

    public K getKey(V value){
        return reverseMap.get(value);
    }
}

public class XUrlImpl implements XUrl{

    MyMap<String,String> url=new MyMap<>();
    MyMap<String,Integer> counts=new MyMap<>();
    
    @Override
    public String registerNewUrl(String longUrl) {
        if(url.get(longUrl)!=null)
        {
            return url.get(longUrl);
        }

        String newUrl="http://short.url/"+RandomStringUtils.randomAlphanumeric(9);
        url.put(longUrl,newUrl);
        return newUrl;
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if(url.get(longUrl)!=null)
        {
            return null;
        }
        String newUrl="http://short.url/"+RandomStringUtils.randomAlphanumeric(9);
        url.put(longUrl,newUrl);
        return newUrl;

    }

    @Override
    public String getUrl(String shortUrl) {
        String longUrl=url.getKey(shortUrl);
        if(longUrl!=null)
        {
            counts.merge(longUrl, 1, Integer::sum);
        }
        return longUrl;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        return counts.get(longUrl);
    }

    @Override
    public String delete(String longUrl) {
        return url.remove(longUrl);
    }

}