package be.lukin.poeditor;

import be.lukin.poeditor.tasks.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JarMain {

    /**
     *  By default read config from current dir
     *
     * @param args cmd
     * @throws java.io.IOException when the configuration file can't be found
     */
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("No command given, choose: \n\tinit\n\tpull\n\tpush\n\tpushTerms");
            return;
        }
        
        Map<String, String> parameters = new HashMap<String, String>();
        if(args.length > 1){
            for(String s : args){
                String[] splitted = s.split("=");
                if(splitted.length==2){
                    parameters.put(splitted[0], splitted[1]);
                }
            }
        }
        
        // Read config
        Path current = Paths.get("");
        File configFile = new File(current.toAbsolutePath().toString(), "poeditor.properties");
        Config config = Config.load(configFile);
        BaseTask task = null;
        
        if("init".equals(args[0])){
            System.out.println("Initialize project");
            task = new InitTask();
            
        } else if("pull".equals(args[0])) {
            System.out.println("Pull languages");
            task = new PullTask();

        } else if("push".equals(args[0])){
            System.out.println("Push languages");
            task = new PushTask();
            
        } else if("pushTerms".equals(args[0])){
            System.out.println("Push terms");
            task = new PushTermsTask();
            
        } else if("generate".equals(args[0])){
            System.out.println("Generate config");
            task = new GenerateTask();
            
        } else if("status".equals(args[0])){
            System.out.println("Status");
            task = new StatusTask();
        }
        
        if(task != null) {
            task.configure(config, parameters);
            task.handle();
        }
    }
}
