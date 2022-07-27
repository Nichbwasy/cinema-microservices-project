package com.cinema.films.microservice.storages;

import com.cinema.films.microservice.exceptions.dao.minio.MinIoBucketNotFoundException;

import java.io.InputStream;

public interface ResourcesStorage {
    InputStream getFileByName(String name) throws MinIoBucketNotFoundException;
    Boolean saveFile(String name, InputStream imgData, Long size, String contentType) throws MinIoBucketNotFoundException;
    void deleteFileByName(String name) throws MinIoBucketNotFoundException;
    Boolean fileExists(String name) throws MinIoBucketNotFoundException;

}
