package be.lukin.poeditor.tasks;

import be.lukin.poeditor.Config;
import be.lukin.poeditor.FileTypeEnum;
import be.lukin.poeditor.POEditorClient;
import be.lukin.poeditor.models.Project;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PullTask extends BaseTask {
    
    @Override
    public void handle() {
        System.out.println("Downloading translations");
        Config config = super.config;
        POEditorClient client = new POEditorClient(config.getApiKey());
        Path current = Paths.get("");
        Project details = client.getProject(config.getProjectId());

        System.out.println("Project: " + details.name + " (id:" + details.id + ", type:" + config.getType() + ")");
        FileTypeEnum fte = FileTypeEnum.valueOf(config.getType().toUpperCase());

        for(String languageKey : config.getLanguageKeys()) {
            String path = config.getLanguage(languageKey);
            File exportFile = new File(current.toAbsolutePath().toString(), path);
            exportFile.getParentFile().mkdirs();
            File f = client.export(config.getProjectId(), languageKey, fte, null, exportFile, null);
            System.out.println(" - Trans " + languageKey + ": " + path);
        }
    }
}
