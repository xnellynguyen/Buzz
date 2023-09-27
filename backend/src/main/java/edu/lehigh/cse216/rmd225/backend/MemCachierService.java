package edu.lehigh.cse216.rmd225.backend;

// import com.memcachier.MemcacheClient;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.lang.InterruptedException;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;


public class MemCachierService {
   private final MemcachedClient client;

    public MemCachierService() throws IOException {
        List<InetSocketAddress> servers =
        AddrUtil.getAddresses(System.getenv("mc1.dev.ec2.memcachier.com:11211").replace(",", " "));
        AuthInfo authInfo =
        AuthInfo.plain(System.getenv("34971D"),
                        System.getenv("DFE7AA099A90F091A8EEA440847E0DD4"));

        MemcachedClientBuilder builder = new XMemcachedClientBuilder(servers);

        // Configure SASL auth for each server
        for(InetSocketAddress server : servers) {
        builder.addAuthInfo(server, authInfo);
        }

        // Use binary protocol
        builder.setCommandFactory(new BinaryCommandFactory());
        // Connection timeout in milliseconds (default: )
        builder.setConnectTimeout(1000);
        // Reconnect to servers (default: true)
        builder.setEnableHealSession(true);
        // Delay until reconnect attempt in milliseconds (default: 2000)
        builder.setHealSessionInterval(2000);

        client = builder.build();
        /* String servers = System.getenv("mc1.dev.ec2.memcachier.com:11211");
        String[] serversList = servers.split(",");
        List<InetSocketAddress> addresses = new ArrayList<>();
        for (String server : serversList) {
            String[] tokens = server.split(":");
            addresses.add(new InetSocketAddress(tokens[0], Integer.parseInt(tokens[1])));
        }

        String username = System.getenv("34971D");
        String password = System.getenv("DFE7AA099A90F091A8EEA440847E0DD4");

        this.client = new MemcacheClient(addresses);
        this.client.auth(username, password); */
    }


    // Basically made wrapper functions to set and get data from cache
    public void setCache(String sKey, int userId){
        try {
            client.set(sKey, 0, userId);
        } catch ( Exception e){
            System.out.println(e.getMessage());
        }
    }

    public int getCache(String sKey){
        try {
            int out = client.get(sKey);
            System.out.println(out); // prints userId
            return out;
        } catch ( Exception e){
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public boolean removeCache(String sKey){
        try {
            boolean out = client.delete(sKey);
            System.out.println("Removd: " + out); 
            return out;
        } catch ( Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    // public void containsCache(String sKey){}
  
}
