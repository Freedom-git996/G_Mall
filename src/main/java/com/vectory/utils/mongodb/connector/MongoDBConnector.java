package com.vectory.utils.mongodb.connector;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.vectory.utils.PropertiesUtil;

public class MongoDBConnector {

    private static MongoClient mongoClient;
    private static Integer maxWaitTime = Integer.parseInt(PropertiesUtil.getProperty("mongodb.max.wait.time", "120000"));
    private static Integer connectTimeout = Integer.parseInt(PropertiesUtil.getProperty("mongodb.connect.timeout", "120000"));
    private static Integer socketTimeout = Integer.parseInt(PropertiesUtil.getProperty("mongodb.socket.timeout", "0"));
    private static Integer connectionsPerHost = Integer.parseInt(PropertiesUtil.getProperty("mongodb.connection.per.host", "300"));
    private static Integer threadsAllowedToBlockForConnectionMultiplier = Integer.parseInt(PropertiesUtil.getProperty("mongodb.threads.allowed.Block.for.connection.multiplier", "5000"));
    private static String ip = PropertiesUtil.getProperty("mongodb.ip");
    private static int port = Integer.parseInt(PropertiesUtil.getProperty("mongodb.port", "27017"));

    private static void init() {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.maxWaitTime(maxWaitTime);
        builder.connectTimeout(connectTimeout);
        builder.socketTimeout(socketTimeout);
        builder.connectionsPerHost(connectionsPerHost);
        builder.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
        MongoClientOptions options = builder.build();
        mongoClient = new MongoClient(new ServerAddress(ip, port), options);
    }

    static{
        init();
    }

    public static MongoClient getMongoClient() {
        return mongoClient;
    }

    public static void returnMongoClient(MongoClient client) {
        client.close();
    }
}
