package brews.services;

import brews.beerxml.ImportedRecipe;
import brews.domain.Recipe;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Steve on 9/07/2017.
 */
@Service
public class UploadRecipeService {

    private static final Logger logger = LoggerFactory.getLogger(UploadRecipeService.class);
    private final String bucketName = "";
    private final String keyName = "";
    private final ImportRecipeService importRecipeService;

    @Autowired
    public UploadRecipeService(ImportRecipeService importRecipeService) {
        this.importRecipeService = importRecipeService;
    }

    public List<Recipe> uploadFile(InputStream file) {
        return importRecipeService.importBeerXml(file);
    }

    public String uploadFileToS3(String fileName) {

        File file = new File(fileName);
        logger.debug("Loading file from " + fileName);

        if (file == null) {
            logger.error("Attempted to upload file :" + fileName + " that doesn't exist");
            return "File does not exist";
        }

        TransferManager transferManager = new TransferManager();

        try {
            logger.debug("Beginning transfer");
            Upload transfer = transferManager.upload(bucketName, keyName, file);
            transfer.waitForCompletion();
            logger.debug("Finished Transfer");

        } catch (AmazonServiceException e) {
            logger.error("Exception transferring file to S3 ",e);
            return "An error has occurred transferring the file";
        } catch (InterruptedException e) {
            logger.error("Transfer to S3 interrupted",e);
            return "Transfer has been interrupted";
        }
        logger.debug("Finished, shutting down transfer manager");
        transferManager.shutdownNow();

        return "File successfully uploaded";
    }

}
