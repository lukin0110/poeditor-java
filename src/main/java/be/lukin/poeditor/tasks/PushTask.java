package be.lukin.poeditor.tasks;

import be.lukin.poeditor.Config;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PushTask extends BaseTask {

    @Override
    public void handle() {
        // params init
        System.out.println("Uploading translations");
        Config config = super.config;
        Path current = Paths.get("");

        boolean override = super.params.getBoolean("override");
        boolean noSleep = super.params.getBoolean("noSleep");
        List<String> languages = super.params.getStringList("languages");
        
        if(noSleep){
            System.out.println("- Sleep disabled");
        }
        
        if(override){
            System.out.println("- override translations");
        }

        boolean first = true;
        
        for(String lang : config.getLanguageKeys()){
            if(languages != null && !languages.contains(lang)){
                continue;
            }

            if(!noSleep && !first) {
                /**
                 * Before a new file upload we'll sleep for 30 seconds to avoid API Exceptions.
                 *
                 * 4048 - Too many upload requests in a short period of time
                 * https://poeditor.com/api_reference/error_codes#code-4048 
                 * */
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            File langFile = new File(current.toAbsolutePath().toString(), config.getLanguage(lang));
            client.uploadLanguage(config.getProjectId(), langFile, lang, override);
            System.out.println("- lang uploaded: " + lang);
            first = false;
        }
    }
}
