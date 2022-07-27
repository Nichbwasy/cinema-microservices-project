package com.cinema.films.microservice.storages.implimentations;

import com.cinema.films.microservice.exceptions.dao.minio.MinIoBucketNotFoundException;
import com.cinema.films.microservice.storages.ResourcesStorage;
import io.minio.*;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class ResourcesStorageImpl implements ResourcesStorage {

    @Value("${storage.minio.endpoint}")
    private static String ENDPOINT;

    @Value("${storage.minio.keys.access}")
    private static String ACCESS_KEY;

    @Value("${storage.minio.keys.secret}")
    private static String SECRET_KEY;

    @Value("${storage.minio.buckit.name}")
    private static String BUCKET_NAME;

    private final MinioClient minioClient;

    public ResourcesStorageImpl() {
        this.minioClient = MinioClient.builder()
                .endpoint(ENDPOINT)
                .credentials(ACCESS_KEY, SECRET_KEY)
                .build();
    }

    @Override
    public InputStream getFileByName(String name) throws MinIoBucketNotFoundException {
        try {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build())) {
                return minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(BUCKET_NAME)
                                .object(name)
                                .build()
                );
            } else {
                log.error("Bucket with name '{}' not found at the endpoint!.", BUCKET_NAME);
                throw new MinIoBucketNotFoundException(String.format("Bucket with name '%s' not found at the endpoint!.", BUCKET_NAME));
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            log.error("Minio error occurred! {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean saveFile(String name, InputStream imgData, Long size, String contentType)throws MinIoBucketNotFoundException {
        try {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build())) {
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(name)
                        .stream(imgData, size, 0)
                        .contentType(contentType)
                        .build());
                return true;
            } else {
                log.error("Bucket with name '{}' not found at the endpoint!.", BUCKET_NAME);
                throw new MinIoBucketNotFoundException(String.format("Bucket with name '%s' not found at the endpoint!.", BUCKET_NAME));
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            log.error("Minio error occurred! {}", e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteFileByName(String name) throws MinIoBucketNotFoundException {
        try {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build())) {
                minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(BUCKET_NAME)
                        .object(name)
                        .build());
            } else {
                log.error("Bucket with name '{}' not found at the endpoint!.", BUCKET_NAME);
                throw new MinIoBucketNotFoundException(String.format("Bucket with name '%s' not found at the endpoint!.", BUCKET_NAME));
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            log.error("Minio error occurred! {}", e.getMessage());
        }
    }

    @Override
    public Boolean fileExists(String name) throws MinIoBucketNotFoundException {
        try {
            if (minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build())) {
                return minioClient.statObject(StatObjectArgs.builder().bucket(BUCKET_NAME).object(name).build()) != null;
            } else {
                log.error("Bucket with name '{}' not found at the endpoint!.", BUCKET_NAME);
                throw new MinIoBucketNotFoundException(String.format("Bucket with name '%s' not found at the endpoint!.", BUCKET_NAME));
            }
        } catch (MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException e) {
            log.error("Minio error occurred! {}", e.getMessage());
        }
        return false;
    }

}
