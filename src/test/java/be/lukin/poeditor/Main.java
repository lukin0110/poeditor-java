package be.lukin.poeditor;

import be.lukin.poeditor.models.Project;
import be.lukin.poeditor.models.Term;
import be.lukin.poeditor.models.UploadDetails;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));

        String apiKey = properties.getProperty("poeditor.apiKey");
        String projectId = properties.getProperty("poeditor.testProjectId");
        
        POEditorClient client = new POEditorClient(apiKey);
        List<Project> list = client.getProjects();

        System.out.println("Length: " + list.size());
        System.out.println("Project: " + projectId);
        //System.out.println("Details: " + client.getProject(projectId));
        //System.out.println("Languages: " + client.getProjectLanguages(projectId));
        //System.out.println("Available: " + client.getAvailableLanguages());
        
        //System.out.println("Add Admin: " + client.addAdministrator(projectId, "Maarten Test", "maarten@lukin.be"));
        //System.out.println("Add Contributor: " + client.addContributor(projectId, "Maarten Test 2", "maarten+1@lukin.be", "nl"));
        //System.out.println("Contributors: " + client.getProjectContributors(projectId));

        //File exportedFile = new File("/Users/maartenhuijsmans/android.xml");
        //File file = client.export("29135", "nl", FileTypeEnum.ANDROID_STRINGS, null, exportedFile);
        //System.out.println(file.getAbsolutePath());

        //List<Term> terms = new ArrayList<Term>();
        //terms.add(new Term("test_mh"));
        //terms.add(new Term("test_mh2"));
        //client.addTerms(projectId, terms);

        //System.out.println("Add German :" + client.addProjectLanguage(projectId, "de"));
        //System.out.println("Create Project: " + client.createProject("Butsing"));
        System.out.println("Terms: " + client.viewTerms(projectId));
        
        //URL url = Thread.currentThread().getContextClassLoader().getResource("android.xml");
        //File uploadFile = new File(url.getPath());
        //UploadDetails details = client.upload(projectId, uploadFile);
        //System.out.println("Upload: " + details);
        
        System.out.println("\n\nDone!");
    }
}
