package be.lukin.poeditor;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.*;

public class TestConfig {

    @Test
    public void readInputStream() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("poeditor.properties");
        Config config = Config.read(is);
        
        assertNotNull(config.getApiKey());
        assertNotNull(config.getProjectId());
        assertNotNull(config.getTerms());
        assertEquals("android_strings", config.getType());
        assertEquals(3, config.getLanguageCount());
        assertEquals("App/src/main/res/values/strings.xml", config.getTranslation("en"));
        assertEquals("App/src/main/res/values-nl/strings.xml", config.getTranslation("nl"));
        assertEquals("App/src/main/res/values-fr/strings.xml", config.getTranslation("fr"));
    }
    
    @Test
    public void readProperties(){
        String[] toCheck = new String[]{
                "poeditor.apiKey",
                "poeditor.projectId",
                "poeditor.type",
        };
        
        Properties properties = new Properties();
        for(String prop : toCheck){
            try {
                Config.read(properties);
            } catch(NullPointerException npe) {
                assertEquals(String.format("'%s' is required", prop), npe.getMessage());
            }
            properties.put(prop, "foo");
        }
        
        // Should fail on missing languages
        try {
            Config.read(properties);
        }catch (RuntimeException re){
           assertTrue(true);
        }
        
        properties.put("poeditor.trans.en", "foo");
        Config config = Config.read(properties);
        assertNotNull(config);
        assertEquals(1, config.getLanguageCount());
    }
    
}
