package be.lukin.poeditor;

import be.lukin.poeditor.models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

/**
 * System.out.println("Create Project: " + client.createProject("Butsing"));
 */
public class TestClient {

    private POEditorClient client;
    private String projectId;

    private static final Logger logger = Logger.getLogger(TestClient.class.getName());
    
    @Before
    public void setUp() throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
        String apiKey = properties.getProperty("poeditor.apiKey");
        projectId = properties.getProperty("poeditor.testProjectId");
        client = new POEditorClient(apiKey);
        logger.info("Client: " + client);
    }
    
    @After
    public void tearDown(){
        //client.deleteProjectLanguage(projectId, "fi");
    }
    
    @Test 
    public void projectDetails(){
        Project project = client.getProject(projectId);
        logger.info("Project " + project);
        
        assertEquals(project.id, projectId);
        assertEquals(project.name, "Test project");
        assertEquals(project.isPublic, "0");
        assertEquals(project.open, "0");
        
        
        /*
                System.out.println("Length: " + list.size());
        System.out.println("Project: " + projectId);
        //System.out.println("Languages: " + client.getProjectLanguages(projectId));
        //System.out.println("Available: " + client.getAvailableLanguages());
         */
    }
    
    @Test
    public void projectLanguages(){
        List<Language> list = client.getProjectLanguages(projectId);
        logger.info("List: " + list);
        assertTrue(list != null);
        assertTrue(list instanceof ArrayList);
        
        List<Language> list2 = client.getAvailableLanguages();
        logger.info("List2: " + list2);
        assertTrue(list2 != null);
        assertTrue(list2 instanceof ArrayList);
    }
    
    @Test
    public void administrator(){
        boolean succeeded = client.addAdministrator(projectId, "Chuck Test", "maarten+poeditor@lukin.be");
        logger.info("Succeeded: " + succeeded);
        assertFalse(succeeded);
        
        List<Contributor> list = client.getProjectContributors(projectId);
        logger.info("Contributors: " + list);
        assertTrue(list.size() > 0);
    }
    
    @Test
    public void export(){
        UUID uuid = UUID.randomUUID();
        File exportedFile = new File("./tmp-" + uuid.toString() + "-test=export.xml");
        logger.info("PATH: " + exportedFile.getAbsolutePath());
        assertFalse(exportedFile.exists());
        
        File result = client.export(projectId, "de", FileTypeEnum.ANDROID_STRINGS, null, exportedFile);
        assertTrue(result.exists());
        assertTrue(result.delete());
    }

    @Test
    public void viewTerms(){
        List<Term> list = client.viewTerms(projectId);
        logger.info("Terms: " + list);
        assertTrue(list != null);
        assertTrue(list instanceof ArrayList);
    }
    
    @Test
    public void editTerms(){
        List<Term> terms = new ArrayList<Term>();
        terms.add(new Term("test_term1"));
        terms.add(new Term("test_term2"));
        
        TermsDetails details = client.addTerms(projectId, terms);
        logger.info("Details: " + details);
        assertEquals(2, details.parsed);
        assertEquals(2, details.added);
        List<Term> terms2 = client.viewTerms(projectId);
        assertTrue(terms2.size() > 1);
        
        TermsDetails details2 = client.deleteTerms(projectId, terms);
        logger.info("Details2: " + details2);
        assertEquals(2, details2.parsed);
        assertEquals(2, details2.deleted);
    }
    
    @Test
    public void editLanguage(){
        boolean succeeded = client.addProjectLanguage(projectId, "fi");
        assertTrue(succeeded);
        
        List<Language> languages = client.getProjectLanguages(projectId);
        int index = Collections.binarySearch(languages,  new Language("foo", "fi"), new Comparator<Language>() {
            @Override
            public int compare(Language o1, Language o2) {
                return o1.code.equals(o2.code) ? 0 : 1;
            }
        });
        
        // The language is found
        assertTrue(index != -1);
        
        succeeded = client.deleteProjectLanguage(projectId, "fi");
        assertTrue(succeeded);
    }

    @Test
    public void upload(){
        URL url = Thread.currentThread().getContextClassLoader().getResource("android.xml");
        File uploadFile = new File(url.getPath());
        logger.info("Path: " + uploadFile.getAbsolutePath());
        UploadDetails details = client.upload(projectId, uploadFile);
        logger.info("Upload: " + details);
        
        assertEquals(3, details.terms.parsed);
        assertEquals(0, details.terms.deleted);
        
        assertEquals(0, details.definitions.parsed);
        assertEquals(0, details.definitions.added);
        assertEquals(0, details.definitions.deleted);
    }
}
