package be.lukin.poeditor.tasks;

import be.lukin.poeditor.Config;
import be.lukin.poeditor.POEditorClient;

public abstract class BaseTask {
    Config config;
    POEditorClient client;

    public BaseTask configure(Config config){
        this.config = config;
        this.client = new POEditorClient(config.getApiKey());
        return this;
    }
    
    public abstract void handle();
}
