package pl.com.kocielapki.cattery.cattery.logic;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.com.kocielapki.cattery.cattery.data.Image;
import pl.com.kocielapki.cattery.cattery.repo.ImageRepository;
import pl.com.kocielapki.cattery.config.ApplicationConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional
public class ImageService {
    private ImageRepository imageRepository;
    private ApplicationConfig applicationConfig;
    private static final Logger log = LoggerFactory.getLogger(ImageService.class);


    public static final String PNG_CONTENT_TYPE = "image/png";
    public static final String PNG_FILE_EXTENSION = ".png";
    public static final String JPG_CONTENT_TYPE = "image/jpeg";
    public static final String JPG_FILE_EXTENSION = ".jpg";


    public ImageService(ImageRepository imageRepository, ApplicationConfig applicationConfig) {
        this.imageRepository = imageRepository;
        this.applicationConfig = applicationConfig;
    }

    public String create(MultipartFile multipartFile) {
        try {
            validate(multipartFile);
            String imageName = UUID.randomUUID().toString() + getFileExtension(multipartFile);
            Image image = new Image(imageName);
            imageRepository.save(image);
            saveOnServer(multipartFile, imageName);
            return imageName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Image get(Long id) {
        return imageRepository.findById(id).get();
    }

    public Image findImageByName(String name) {
        return imageRepository.findByImageFileName(name);
    }

    public byte[] getImage(String fileName) throws IOException {
        InputStream in = null;
        try {
            File file = ResourceUtils.getFile(applicationConfig.getPathToImagesFolder() + fileName);
            in = new FileInputStream(file);
            return IOUtils.toByteArray(in);
        } catch (Exception e) {
            log.error("Error during getting file");
            return null;
        }
        finally {
            in.close();
        }
    }


    private String getFileExtension(MultipartFile file) {

        String contentType = file.getContentType();

        if (contentType == null) {
            throw new IllegalArgumentException("Wrong file");
        }

        switch (contentType) {
            case PNG_CONTENT_TYPE:
                return PNG_FILE_EXTENSION;
            case JPG_CONTENT_TYPE:
                return JPG_FILE_EXTENSION;
        }

        throw new IllegalArgumentException("Incorrect file type: " + contentType);

    }

    public void deleteImageFromServer(String fileName) {
        try {
            Path fileToDeletePath = Paths.get(applicationConfig.getPathToImagesFolder() + fileName);
            Files.delete(fileToDeletePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteImage(String fileName) {
        Image imageToDelete = findImageByName(fileName);
        imageRepository.delete(imageToDelete);
    }

    private void validate(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File can't be empty!");
        }

        if (file.getOriginalFilename() == null || file.getOriginalFilename().equals("")) {
            throw new IllegalArgumentException("Original file name can't be empty");
        } else if (file.getOriginalFilename().length() > 1000) {
            throw new IllegalArgumentException("Original file name too long, max 5000 characters");
        } else if (file.getOriginalFilename().length() < 5) {
            throw new IllegalArgumentException("Original file name is too short, min 5 characters");
        }
    }

    private String saveOnServer(MultipartFile multipartFile, String name) {
        try {
            Path path = Paths.get(applicationConfig.getPathToImagesFolder() + name);
            Files.write(path, multipartFile.getBytes());
            return name;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Błąd w zapisie pliku na serwerze");
        }
    }

}
