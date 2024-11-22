package org.bsdro.cache;

import static org.bsdro.cache.CacheEvictionStrategy.*;

/**
 * Cache Simulator
 *
 */
public class CacheSim
{
    public static void main( String[] args )
    {

        String[] addrs = {
                "0xbfffff00",
                "0x440",
                "0xbffffef0",
                "0xbffffee0",
                "0xbffffed0",
                "0xbffffec4",
                "0xbffffec0",
                "0xbffffec8",
                "0x4c4",
                "0x440",
                "0x54f",
                "0x4d7",
                "0x440",
                "0x5c3",
                "0x55555555",
                "0x5a0",
                "0xbffffec4",
                "0xbffffed0",
                "0xbffffee0",
                "0xbffffef0",
                "0xbfffff00",
        };
//        Cache cache = new Cache(4, 4, 32, LRU);
//        Cache cache = new Cache(4, 4, 32, LFU);
//        Cache cache = new Cache(4, 4, 32, FIFO);
        Cache cache = new Cache(2, 8, 16, LRU);

        for (String addr : addrs) {
            long ad = Long.decode(addr);
            System.out.print("put " + addr);
            cache.put(ad);
            System.out.println(cache);
        }

        System.out.println("Final");
        System.out.println(cache);
    }
}
