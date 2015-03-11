package be.lukin.poeditor;

import be.lukin.poeditor.tasks.PullTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class JarMain {

    /**
     *  By default read config from current dir
     *
     * @param args cmd
     */
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("No command given, choose: \n\tinit\n\tpull\n\tpushTerms");
            return;
        }
        Path current = Paths.get("");
        File configFile = new File(current.toAbsolutePath().toString(), "poeditor.properties");
        Config config = Config.load(configFile);
        
        if("init".equals(args[0])){
            System.out.println("Initialize project");    

        } else if("pull".equals(args[0])){
            System.out.println("Pull languages");
            PullTask pullTask = new PullTask();
            pullTask.configure(config);
            pullTask.handle();
            
        } else if("pushTerms".equals(args[0])){
            System.out.println("Push terms");
            
        } else if("generate".equals(args[0])){
            System.out.println("Generate config");
            
        }
    }
}
