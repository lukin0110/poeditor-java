package be.lukin.poeditor;

public class JarMain {

    /**
     *  By default read config from current dir
     *
     * @param args cmd
     */
    public static void main(String[] args){
        if(args.length == 0){
            System.out.println("No command given, choose: \n\tinit\n\tpull\n\tpushTerms");
            return;
        }

        if("init".equals(args[0])){
            System.out.println("Initialize project");    

        } else if("pull".equals(args[0])){
            System.out.println("Pull languages");
            
        } else if("pushTerms".equals(args[0])){
            System.out.println("Push terms");
            
        } else if("generate".equals(args[0])){
            System.out.println("Generate config");
            
        }
    }
}
