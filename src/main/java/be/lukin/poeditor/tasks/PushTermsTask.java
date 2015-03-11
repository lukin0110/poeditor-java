package be.lukin.poeditor.tasks;

import be.lukin.poeditor.models.UploadDetails;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PushTermsTask extends BaseTask {
    
    @Override
    public void handle() {
        System.out.println("Pushing terms");

        if(config.getTerms() != null) {
            Path current = Paths.get("");
            File termsFile = new File(current.toAbsolutePath().toString(), config.getTerms());
            UploadDetails details = client.uploadTerms(config.getProjectId(), termsFile);
            System.out.println("Synced: " + details);
        } else {
            System.out.println("No terms defined");
        }   
    }
}
