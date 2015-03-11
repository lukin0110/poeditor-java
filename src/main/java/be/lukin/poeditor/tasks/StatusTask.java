package be.lukin.poeditor.tasks;

import be.lukin.poeditor.models.Project;

/**
 * StatusTask is a simple task that displays the existing project configuration in a more human readable format. It 
 * lists all resources that have been initialized under the local project and all their associated translation files. 
 */
public class StatusTask extends BaseTask {
    @Override
    public void handle() {
        System.out.println("\n");
        System.out.println("Api key: " + config.getApiKey());
        Project project = client.getProject(config.getProjectId());
        System.out.println("Project: " + project.name);
        System.out.println("Terms: " + config.getTerms());
        System.out.println("\nLanguages:");
        for(String key : config.getLanguageKeys()){
            String path = config.getLanguage(key);
            System.out.println(" - Language " + key + ": " + path);
        }
    }
}
