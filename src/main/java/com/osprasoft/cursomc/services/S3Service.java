package com.osprasoft.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.osprasoft.cursomc.services.exception.FileException;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    private Logger LOG = LoggerFactory.getLogger(S3Service.class);

    public URI uploadFile(MultipartFile multipartFile) {
        try {
            String nome = multipartFile.getOriginalFilename();
            InputStream inputStream;
            inputStream = multipartFile.getInputStream();
            String tipo = multipartFile.getContentType();
            return uploadFile(inputStream, nome, tipo);
        } catch (IOException e) {
            throw new FileException("Erro de IO: " + e.getMessage());
        }
    }    

    public URI uploadFile(InputStream inputStream, String nome, String tipo) {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(tipo);
        LOG.info("Iniciando upload");
        s3client.putObject(bucketName, nome, inputStream, meta);
        LOG.info("Upload finalizado");
        try {
            return s3client.getUrl(bucketName, nome).toURI();
        } catch (URISyntaxException e) {
            throw new FileException("Erro ao converter URL para URI");
        }
    }
}
