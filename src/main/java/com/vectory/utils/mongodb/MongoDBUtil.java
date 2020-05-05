package com.vectory.utils.mongodb;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.vectory.utils.mongodb.connector.MongoDBConnector;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.*;
import java.util.*;

public class MongoDBUtil {

    public static boolean isExits(String dbName, String collectionName, Map<String, Object> filterMap) {
        MongoClient mongoClient = null;
        try {
            if(filterMap != null) {
                mongoClient = MongoDBConnector.getMongoClient();
                FindIterable<Document> docs = mongoClient.getDatabase(dbName)
                        .getCollection(collectionName)
                        .find(new Document(filterMap));
                Document doc = docs.first();
                return doc != null;
            }
            return false;
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static boolean insert(String dbName, String collectionName, Map<String, Object> insertMap) {
        MongoClient mongoClient = null;
        try {
            if (insertMap != null) {
                mongoClient = MongoDBConnector.getMongoClient();
                Document doc = mongoClient.getDatabase(dbName)
                        .getCollection(collectionName)
                        .find()
                        .sort(new BasicDBObject("_id", -1))
                        .limit(1)
                        .first();
                int id = 1;
                if (doc != null) {
                    id = Integer.parseInt(doc.get("_id").toString()) + 1;
                }
                insertMap.put("_id", id);
                MongoDBConnector.getMongoClient()
                        .getDatabase(dbName)
                        .getCollection(collectionName)
                        .insertOne(new Document(insertMap));
                return true;
            }
            return false;
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static boolean deleteById(String dbName, String collectionName, String _id) {
        MongoClient mongoClient = null;
        try {
            ObjectId objectId = new ObjectId(_id);
            mongoClient = MongoDBConnector.getMongoClient();
            Bson filter = Filters.eq("_id", objectId);
            DeleteResult deleteResult = mongoClient.getDatabase(dbName)
                    .getCollection(collectionName)
                    .deleteOne(filter);
            long deletedCount = deleteResult.getDeletedCount();
            return deletedCount > 0;
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static boolean deleteById(String dbName, String collectionName, Integer _id) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            Bson filter = Filters.eq("_id", _id);
            DeleteResult deleteResult = mongoClient.getDatabase(dbName)
                    .getCollection(collectionName)
                    .deleteOne(filter);
            long deletedCount = deleteResult.getDeletedCount();
            return deletedCount > 0;
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static boolean delete(String dbName, String collectionName, Map<String, Object> map) {
        MongoClient mongoClient = null;
        try {
            if (map != null) {
                mongoClient = MongoDBConnector.getMongoClient();
                DeleteResult result = mongoClient.getDatabase(dbName)
                        .getCollection(collectionName)
                        .deleteMany(new Document(map));
                long deletedCount = result.getDeletedCount();
                return deletedCount > 0;
            }
            return false;
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static boolean updateOne(String dbName, String collectionName, Map<String, Object> filterMap, Map<String, Object> updateMap) {
        MongoClient mongoClient = null;
        try {
            if (filterMap != null && filterMap.size() > 0 && updateMap != null) {
                mongoClient = MongoDBConnector.getMongoClient();
                UpdateResult result = mongoClient.getDatabase(dbName)
                        .getCollection(collectionName)
                        .updateOne(new Document(filterMap), new Document("$set", new Document(updateMap)));
                long modifiedCount = result.getModifiedCount();
                return modifiedCount > 0;
            }
            return false;
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static boolean updateById(String dbName, String collectionName, String _id, Document updateDoc) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            ObjectId objectId = new ObjectId(_id);
            Bson filter = Filters.eq("_id", objectId);
            UpdateResult result = mongoClient.getDatabase(dbName)
                    .getCollection(collectionName)
                    .updateOne(filter, new Document("$set", updateDoc));
            long modifiedCount = result.getModifiedCount();
            return modifiedCount > 0;
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static boolean updateById(String dbName, String collectionName, Integer _id, Document updateDoc) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            Bson filter = Filters.eq("_id", _id);
            UpdateResult result = mongoClient.getDatabase(dbName)
                    .getCollection(collectionName)
                    .updateOne(filter, new Document("$set", updateDoc));
            long modifiedCount = result.getModifiedCount();
            return modifiedCount > 0;
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static List<Document> find(String dbName, String collectionName, Bson filter) {
        MongoClient mongoClient = null;
        try {
            final List<Document> resultList = new ArrayList<Document>();
            if (filter != null) {
                mongoClient = MongoDBConnector.getMongoClient();
                FindIterable<Document> docs = mongoClient.getDatabase(dbName)
                        .getCollection(collectionName)
                        .find(filter);
                docs.forEach(new Block<Document>() {
                    public void apply(Document document) {
                        resultList.add(document);
                    }
                });
            }
            return resultList;
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static Document findById(String dbName, String collectionName, String _id) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            ObjectId objectId = new ObjectId(_id);
            return mongoClient.getDatabase(dbName)
                    .getCollection(collectionName)
                    .find(Filters.eq("_id", objectId))
                    .first();
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static Document findById(String dbName, String collectionName, Integer _id) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            FindIterable<Document> docs = mongoClient.getDatabase(dbName)
                    .getCollection(collectionName)
                    .find(new Document().append("_id", _id));
            return docs.first();
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static List<Document> findByPage(String dbName, String collectionName, Bson filter, int pageIndex, int pageSize) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            Bson orderBy = new BasicDBObject("_id", -1);
            final List<Document> resultList = new ArrayList<Document>();
            FindIterable<Document> docs = mongoClient.getDatabase(dbName)
                    .getCollection(collectionName)
                    .find(filter)
                    .sort(orderBy)
                    .skip((pageIndex - 1) * pageSize)
                    .limit(pageSize);
            docs.forEach(new Block<Document>() {
                public void apply(Document document) {
                    resultList.add(document);
                }
            });
            return resultList;
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static MongoCollection getCollection(String dbName, String collectionName) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            return mongoClient.getDatabase(dbName)
                    .getCollection(collectionName);
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static long getCount(String dbName, String collectionName) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            return mongoClient.getDatabase(dbName)
                    .getCollection(collectionName)
                    .count();
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static List<String> getAllCollections(String dbName) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            MongoIterable<String> cols = mongoClient.getDatabase(dbName)
                    .listCollectionNames();
            List<String> _list = new ArrayList<>();
            for(String s : cols) {
                _list.add(s);
            }
            return _list;
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static MongoIterable<String> getAllDatabaseName() {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            return mongoClient.listDatabaseNames();
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static void dropDatabase(String dbName) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            mongoClient.getDatabase(dbName).drop();
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static void dropCollection(String dbName, String collectionName) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            mongoClient.getDatabase(dbName)
                    .getCollection(collectionName)
                    .drop();
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
    }

    public static ObjectId uploadFile(File file, String dbFileName) {
        MongoClient mongoClient = null;
        InputStream in = null;
        ObjectId fileId = null;
        try {
            in = new FileInputStream(file);
            mongoClient = MongoDBConnector.getMongoClient();
            GridFSBucket bucket = GridFSBuckets.create(mongoClient.getDatabase(dbFileName));
            GridFSUploadOptions options = new GridFSUploadOptions();
            Document metadata = new Document();
            metadata.append("contentType", FileUtil.getExtensionName(file.getName()));
            options.metadata(metadata);
            fileId = bucket.uploadFromStream(UUID.randomUUID().toString().replaceAll("-", ""), in, options);
            System.out.println("上传完成，文件ID：" + fileId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileId;
    }

    public static void downloadFile(String path, String objectId, String dbFileName) {
        MongoClient mongoClient = null;
        OutputStream os = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            GridFSFile gridFSFile = getGridFSFile(mongoClient, objectId, dbFileName);
            String extensionName = gridFSFile.getMetadata().get("contentType").toString();
            File file = new File(path + "\\" + UUID.randomUUID().toString().replaceAll("-", "") + "." + extensionName);
            GridFSBucket bucket = GridFSBuckets.create(mongoClient.getDatabase(dbFileName));
            os = new FileOutputStream(file);
            bucket.downloadToStream(new ObjectId(objectId), os);
            System.out.println("下载完成");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static GridFSFile getGridFSFile(MongoClient mongoClient, String objectId, String dbFileName) {
        GridFSFile gridFSFile = null;
        GridFSBucket bucket = GridFSBuckets.create(mongoClient.getDatabase(dbFileName));
        GridFSFindIterable gridFSFindIterable = bucket.find(Filters.eq("_id", new ObjectId(objectId)));
        gridFSFile = gridFSFindIterable.first();
        return gridFSFile;
    }

    public static String searchFile(String objectId, String dbFileName) {
        MongoClient mongoClient = null;
        String fileName = null;
        try {
            mongoClient = MongoDBConnector.getMongoClient();
            GridFSBucket bucket = GridFSBuckets.create(mongoClient.getDatabase("my_file"));
            GridFSFindIterable gridFSFindIterable = bucket.find(Filters.eq("_id", new ObjectId(objectId)));
            GridFSFile gridFSFile = gridFSFindIterable.first();
            fileName = gridFSFile.getFilename();
            System.out.println("filename: " + fileName);
        } finally {
            if(mongoClient != null) {
                MongoDBConnector.returnMongoClient(mongoClient);
            }
        }
        return fileName;
    }

    public static boolean deleteFile(String objectId, String dbFileName) {
        MongoClient mongoClient = null;
        mongoClient = MongoDBConnector.getMongoClient();
        GridFSBucket bucket = GridFSBuckets.create(mongoClient.getDatabase(dbFileName));
        bucket.delete(new ObjectId(objectId));
        System.out.println("删除完成");
        return true;
    }
}