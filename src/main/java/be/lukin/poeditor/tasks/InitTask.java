package be.lukin.poeditor.tasks;

import be.lukin.poeditor.models.Project;
import be.lukin.poeditor.models.UploadDetails;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InitTask extends BaseTask {
    
    @Override
    public void handle() {
        System.out.println("Initializing");
        Project details = client.getProject(config.getProjectId());
        
        if(details != null){
            Path current = Paths.get("");
            
            // Uploading terms
            if(config.getTerms() != null) {
                File termsFile = new File(current.toAbsolutePath().toString(), config.getTerms());
                UploadDetails ud = client.uploadTerms(config.getProjectId(), termsFile);
                System.out.println("- terms uploaded: " + ud);
            } else {
                System.out.println("- no terms defined");
            }

            // Create languages
            for(String lang : config.getLanguageKeys()){
                client.addProjectLanguage(config.getProjectId(), lang);
                System.out.println("- lang added: " + lang);
                File langFile = new File(current.toAbsolutePath().toString(), config.getLanguage(lang));
                client.uploadLanguage(config.getProjectId(), langFile, lang, true);
                System.out.println("- lang uploaded: " + lang);
            }
        } else {
            System.out.println("Project with id '" + config.getProjectId() + "' doesn't exist.");
        }
    }
}  
