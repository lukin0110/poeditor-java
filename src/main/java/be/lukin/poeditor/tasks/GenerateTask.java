package be.lukin.poeditor.tasks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class GenerateTask extends BaseTask {
    @Override
    public void handle() {
        Properties properties = new Properties();
        properties.put("poeditor.apiKey", "your api key");
        properties.put("poeditor.projectId", "your project id");
        properties.put("poeditor.type", "properties");
        properties.put("poeditor.terms", "src/resources/en.properties");
        properties.put("poeditor.trans.en", "src/resources/en.properties");
        properties.put("poeditor.trans.nl", "src/resources/nl.properties");
        properties.put("poeditor.trans.fr", "src/resources/fr.properties");

        Path current = Paths.get("");
        File generated = new File(current.toAbsolutePath().toString(), "poeditor-example.properties");

        try {
            properties.store(new FileOutputStream(generated), "Example configuration");
        } catch (IOException e) {
            System.out.println("Oops, could not generate example config");
        }
    }
}
