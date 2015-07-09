package be.lukin.poeditor.tasks;

import be.lukin.poeditor.Config;
import be.lukin.poeditor.POEditorClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseTask {
    
    class Params {
        private Map<String, String> parameters = new HashMap<String, String>();

        public Params(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        public String getString(String key){
            return parameters.get(key);
        }
        
        public String[] getStringArray(String key){
            String s = parameters.get(key);
            
            if(s != null){
                return s.split(",");
            }
            
            return null;
        }
        
        public List<String> getStringList(String key){
            String[] s = getStringArray(key);
            
            if(s != null) {
                return Arrays.asList(s);
            }
            return null;
        }
        
        public boolean getBoolean(String key){
            return Boolean.parseBoolean(parameters.get(key));
        }
    }
    
    Params params;
    Config config;
    POEditorClient client;

    public BaseTask configure(Config config){
        return configure(config, null);
    }
    
    public BaseTask configure(Config config, Map<String, String> parameters){
        this.config = config;
        this.params = new Params(parameters != null ? parameters : new HashMap<String, String>());
        this.client = new POEditorClient(config.getApiKey());
        return this;
    }
    
    public abstract void handle();
}
