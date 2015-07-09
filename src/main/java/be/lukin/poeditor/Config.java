package be.lukin.poeditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Config {
    private String apiKey;
    private String projectId;
    private String type;
    private String terms;
    private Map<String, String> translations = new HashMap<String, String>();
    private Map<String, String> filters = new HashMap<String, String>();    // Filters are stored per language
    private String[] tagsAll;
    private String[] tagsNew;
    private String[] tagsObsolete;
    private String[] tagsPull;

    public Config() {
    }

    public Config(String apiKey, String projectId, String type, String terms, Map<String, String> translations) {
        this.apiKey = apiKey;
        this.projectId = projectId;
        this.type = type;
        this.terms = terms;
        this.translations = translations;
    }

    public Config(String apiKey, 
                  String projectId, 
                  String type, 
                  String terms, 
                  Map<String, String> translations, 
                  Map<String, String> filters, 
                  String[] tagsAll, 
                  String[] tagsNew, 
                  String[] tagsObsolete, 
                  String[] tagsPull) {
        this.apiKey = apiKey;
        this.projectId = projectId;
        this.type = type;
        this.terms = terms;
        this.translations = translations;
        this.filters = filters;
        this.tagsAll = tagsAll;
        this.tagsNew = tagsNew;
        this.tagsObsolete = tagsObsolete;
        this.tagsPull = tagsPull;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getType() {
        return type;
    }

    public String getTerms() {
        return terms;
    }
    
    public String getLanguage(String language){
        return translations.get(language);
    }
    
    public int getLanguageCount(){
        return translations.size();
    }
    
    public Set<String> getLanguageKeys(){
        return this.translations.keySet();
    }

    public FilterByEnum[] getFilters(String language){
        String f = this.filters.get(language);
        
        if(f != null){
            String[] array = f.split(",");    
            return FilterByEnum.toArray(array);
        }
        
        return null;
    }
    
    public String[] getTagsAll() {
        return tagsAll;
    }

    public String[] getTagsNew() {
        return tagsNew;
    }

    public String[] getTagsObsolete() {
        return tagsObsolete;
    }

    public String[] getTagsPull() {
        return tagsPull;
    }

    @Override
    public String toString() {
        return "Config{" +
                "apiKey='" + apiKey + '\'' +
                ", projectId='" + projectId + '\'' +
                ", type='" + type + '\'' +
                ", terms='" + terms + '\'' +
                ", translations=" + translations +
                '}';
    }
    
    public static Config load(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        return load(properties);
    }
    
    public static Config load(File file) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));
        return load(properties);
    }
    
    public static Config load(Properties properties) {
        Config config = new Config();
        config.apiKey = properties.getProperty("poeditor.apiKey");
        Objects.requireNonNull(config.apiKey, "'poeditor.apiKey' is required");
        config.projectId = properties.getProperty("poeditor.projectId");
        Objects.requireNonNull(config.projectId, "'poeditor.projectId' is required");
        config.type = properties.getProperty("poeditor.type");
        Objects.requireNonNull(config.type, "'poeditor.type' is required");
        config.terms = properties.getProperty("poeditor.terms");

        String f = properties.getProperty("poeditor.filters");
        if(f != null){
            //config.filters = f.split(",");
        }
        
        for(String key : properties.stringPropertyNames()){

            // Fetch translation files
            if(key.startsWith("poeditor.trans.")){
                config.translations.put(key.substring(15), properties.getProperty(key));
            }
            
            // Fetch filters
            if(key.startsWith("poeditor.filters.")){
                config.filters.put(key.substring(17), properties.getProperty(key));
            }
        }

        if(config.translations.size() == 0){
            throw new RuntimeException("No languages detected, you should provide at least on 'poeditor.trans.<lang> key'");
        }
        
        return config;
    }
}
