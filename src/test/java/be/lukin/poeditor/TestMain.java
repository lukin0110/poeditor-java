package be.lukin.poeditor;

import be.lukin.poeditor.models.Project;
import be.lukin.poeditor.models.Term;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestMain {
    
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));

        String apiKey = properties.getProperty("poeditor.apiKey");
        String projectId = properties.getProperty("poeditor.testProjectId");
        
        POEditorClient client = new POEditorClient(apiKey);
        List<Project> list = client.getProjects();

        System.out.println("Length: " + list.size());
        System.out.println("Project: " + projectId);

        List<Term> terms = new ArrayList<Term>();
        terms.add(new Term("test_mh"));
        terms.add(new Term("test_mh2"));
        System.out.println("Add terms: " + client.addTerms(projectId, terms));
        System.out.println("Delete terms: " + client.deleteTerms(projectId, terms));

        System.out.println("\n\nPath: " + Thread.currentThread().getContextClassLoader().getResource("config.properties"));
        System.out.println("\n\nDone!");
    }
}
