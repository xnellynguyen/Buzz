package edu.lehigh.cse216.rmd225.backend;
import java.util.Map;
import java.io.IOException;
import java.util.HashMap;

public class MyHashMap {
    private static Map<String, Integer> sessionMap = new HashMap<>();
    // private static MemCachierService cacheService; //  = new MemCachierService();

    /* static {
        try {
          cacheService = new MemCachierService();
        } catch (IOException e) {
          // handle the exception
          System.out.println(e.getMessage());
        }
      } */

    // not used currently
    public static Map<String, Integer> getSessionMap() throws IOException{
        return sessionMap; // cacheService;
    }

    public static void addToSessionMap(String key, Integer value) throws IOException{
        sessionMap.put(key, value);
        // cacheService.setCache(key,value);
    }

    public static Integer getFromSessionMap(String key) throws IOException{
        return sessionMap.get(key);
        // return cacheService.getCache(key);
    }

    public static Integer deleteFromSessionMap(String key) throws IOException{
        return sessionMap.remove(key);
        // return cacheService.removeCache(key);
    }

    // not used currently
    public static boolean containsInSessionMap(String key) {
        return sessionMap.containsKey(key);
    }

    // not used currently
    public static void printAll() {
        System.out.println("Session Map:");
        for (Map.Entry<String, Integer> entry : sessionMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
