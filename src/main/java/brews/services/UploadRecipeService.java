package brews.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by Steve on 9/07/2017.
 */
@Service
public class UploadRecipeService {

    @Autowired
    private AmazonS3 amazonS3;

    public void uploadFileToS3(String fileName) {
        TransferManager transferManager = new TransferManager(this.amazonS3);
        transferManager.upload("MyBucker","Key",new File(fileName));
    }

}
