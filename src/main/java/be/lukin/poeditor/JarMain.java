package be.lukin.poeditor;

import be.lukin.poeditor.tasks.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JarMain {

    /**
     *  By default read config from current dir
     *
     * @param args cmd
     * @throws java.io.IOException when the configuration file can't be found
     */
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("No command given, choose: \n\tinit\n\tpull\n\tpushTerms");
            return;
        }
        
        // Read config
        Path current = Paths.get("");
        File configFile = new File(current.toAbsolutePath().toString(), "poeditor.properties");
        Config config = Config.load(configFile);
        
        if("init".equals(args[0])){
            System.out.println("Initialize project");
            InitTask initTask = new InitTask();
            initTask.configure(config);
            initTask.handle();
        } else if("pull".equals(args[0])){
            System.out.println("Pull languages");
            PullTask pullTask = new PullTask();
            pullTask.configure(config);
            pullTask.handle();
        } else if("pushTerms".equals(args[0])){
            System.out.println("Push terms");
            PushTermsTask pushTermsTask = new PushTermsTask();
            pushTermsTask.configure(config);
            pushTermsTask.handle();
        } else if("generate".equals(args[0])){
            System.out.println("Generate config");
            GenerateTask generateTask = new GenerateTask();
            generateTask.configure(config);
            generateTask.handle();
        } else if("status".equals(args[0])){
            System.out.println("Status");
            StatusTask statusTask = new StatusTask();
            statusTask.configure(config);
            statusTask.handle();
        }
    }
}
