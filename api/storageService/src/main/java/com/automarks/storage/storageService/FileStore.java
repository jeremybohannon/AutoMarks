package com.automarks.storage.storageService;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.File;
import java.io.IOException;

public class FileStore {

    Mongo mongo = new Mongo("localhost", 27017);
    DB db = mongo.getDB("automarks");
    private final String collection = "files";

    public void save(String fileName, File file){
        try {
            GridFS gfs = new GridFS(db, collection);
            GridFSInputFile gfsFile = null;
            gfsFile = gfs.createFile(file);
            gfsFile.setFilename(fileName);
            gfsFile.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public GridFSDBFile get(String fileName){
        GridFSDBFile file = null;
        try {
            GridFS gfs = new GridFS(db, collection);
            file = gfs.findOne(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

}
