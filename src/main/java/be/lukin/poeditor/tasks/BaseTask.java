package be.lukin.poeditor.tasks;

import be.lukin.poeditor.Config;
import be.lukin.poeditor.POEditorClient;

public abstract class BaseTask {
    Config config;
    POEditorClient client;

    public void configure(Config config){
        this.config = config;
        this.client = new POEditorClient(config.getApiKey());
    }
    
    public abstract void handle();
}
