package com.alkemy.ong.cloud;

import com.alkemy.ong.domain.storage.CustomMultipartFile;
import com.alkemy.ong.domain.storage.Image;
import com.alkemy.ong.domain.storage.StorageGateway;
import com.alkemy.ong.web.exceptions.ServiceUnavailable;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.Base64;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class DefaultStorageGateway implements StorageGateway {
    private AmazonS3 s3client;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    // Almacena el archivo con la fecha y hora de hoy; reemplaza los espacios por guines bajos
    private String generateFileName(MultipartFile multiPart) {
        return new LocalDateTime().now() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    @Override
    public Image save(MultipartFile multipartFile) {

        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = generateFileUrl(fileName);
            uploadFileTos3bucket(fileName, file);
            file.delete();
            return new Image(fileName, fileUrl);
        } catch (AmazonServiceException | IOException e) {
            throw new ServiceUnavailable("s3 amazon not available for save File");
        }
    }

    @Override
    public Image save(String base64, String fileName) {
        MultipartFile image = this.base64ToImage(base64, fileName);
        return this.save(image);
    }

    public MultipartFile base64ToImage(String encoded, String fileName) {
        // Quito "data:image/jpeg;base64" para quedarme solo con los bytes a decodear
        String trimmedEncodedImage = encoded.substring(encoded.indexOf(",") + 1);

        byte[] decodedBytes = Base64.decode(trimmedEncodedImage);

        CustomMultipartFile customMultipartFile = new CustomMultipartFile(decodedBytes, fileName);

        try {
            customMultipartFile.transferTo(customMultipartFile.getFile());
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException : " + e);
        } catch (IOException e) {
            System.out.println("IOException : " + e);
        }

        return customMultipartFile;
    }

    private String generateFileUrl(String fileName) {
        return "https://s3." + s3client.getRegionName() + ".amazonaws.com/" + bucketName + "/" + fileName;
    }

}
