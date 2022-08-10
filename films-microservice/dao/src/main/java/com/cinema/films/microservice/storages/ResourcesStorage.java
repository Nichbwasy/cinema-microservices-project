package com.cinema.films.microservice.storages;

import com.cinema.films.microservice.exceptions.dao.minio.MinIoBucketNotFoundException;

import java.io.InputStream;

public interface ResourcesStorage {
    /**
     * Returns file from MinIO storage by file name.
     * @param name Name of file.
     * @return Stream of file data.
     * @throws MinIoBucketNotFoundException Throws when MinIO bucket not found.
     */
    InputStream getFileByName(String name) throws MinIoBucketNotFoundException;

    /**
     * Saves a new file into MinIO resource storage.
     * @param name Saved file name.
     * @param imgData Stream of file data to save in storage.
     * @param size file size.
     * @param contentType Content type of saved file.
     * @return True if file was saved successfully, otherwise false.
     * @throws MinIoBucketNotFoundException Throws when MinIO bucket not found.
     */
    Boolean saveFile(String name, InputStream imgData, Long size, String contentType) throws MinIoBucketNotFoundException;

    /**
     * Removes file from MinIO resource storage be file name.
     * @param name Removed file name
     * @throws MinIoBucketNotFoundException Throws when MinIO bucket not found.
     */
    void deleteFileByName(String name) throws MinIoBucketNotFoundException;

    /**
     * Checks if file with selected name exists in MinIO resource storage.
     * @param name File name to check.
     * @return True if file with selected name exist in resource storage, otherwise false.
     * @throws MinIoBucketNotFoundException
     */
    Boolean fileExists(String name) throws MinIoBucketNotFoundException;

}
