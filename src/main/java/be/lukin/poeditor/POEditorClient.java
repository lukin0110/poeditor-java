package be.lukin.poeditor;

import be.lukin.poeditor.models.*;
import be.lukin.poeditor.response.*;
import com.google.gson.Gson;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/*
TODO:
    def create_project(self, name, description=None):
    def view_project_terms(self, project_id, language_code=None):
    def delete_terms(self, project_id, data):
    def sync_terms(self, project_id, data):
    def update_project_language(self, project_id, language_code, data):
    def update_terms(self, project_id, file_path=None, language_code=None,
                     overwrite=False, sync_terms=False, tags=None):
    def update_terms_definitions(self, project_id, file_path=None,
                                 language_code=None, overwrite=False,
                                 sync_terms=False, tags=None):
    def update_definitions(self, project_id, file_path=None,
                           language_code=None, overwrite=False):
 */
public class POEditorClient {
    
    public static final String HOST = "https://poeditor.com/api/";
    
    public static class Action {
        public static final String LIST_PROJECTS = "list_projects";
        public static final String VIEW_PROJECT = "view_project";
        public static final String LIST_LANGUAGES = "list_languages";
        public static final String AVAILABLE_LANGUAGES = "available_languages";
        public static final String LIST_CONTRIBUTORS = "list_contributors";
        public static final String ADD_CONTRIBUTOR = "add_contributor";
        public static final String ADD_TERMS = "add_terms";
        public static final String ADD_LANGUAGE = "add_language";
        public static final String DELETE_LANGUAGE = "delete_language";
        public static final String SET_REFERENCE_LANGUAGE = "set_reference_language";
        public static final String CLEAR_REFERENCE_LANGUAGE = "clear_reference_language";
        public static final String EXPORT = "export";
    }
    
    private String apiKey;
    private POEditorService service;

    public POEditorClient(String apiKey) {
        this.apiKey = apiKey;
        init();
    }
    
    private void init(){
        if (service == null) {
            service = getAdapter().create(POEditorService.class);
        }
    }

    private RestAdapter getAdapter() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
                requestFacade.addHeader("Accept", "application/json");
            }
        };

        return new RestAdapter.Builder()
                //.setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(requestInterceptor)
                .setClient(new OkClient())
                .setEndpoint(HOST).build();
    }
    
    public List<Project> getProjects(){
        return service.getProjects(Action.LIST_PROJECTS, apiKey).list;
    }
    
    public Project getProject(String projectId){
        ProjectDetailResponse pdr = service.getProject(Action.VIEW_PROJECT, apiKey, projectId);
        return pdr.item;
    }
    
    public List<Language> getAvailableLanguages(){
        Map<String, String> map = service.getAvailableLanguages(Action.AVAILABLE_LANGUAGES, apiKey).list;
        List<Language> list = new ArrayList<Language>();
        
        for(Map.Entry<String, String> e : map.entrySet()){
            list.add(new Language(e.getKey(), e.getValue()));
        }

        return list;
    }
    
    public List<Language> getProjectLanguages(String projectId){
        LanguagesResponse lr = service.getProjectLanguages(Action.LIST_LANGUAGES, apiKey, projectId);
        return lr.list;
    }
    
    public boolean addProjectLanguage(String projectId, String language){
        ResponseWrapper wrapper = service.editProjectLanguage(Action.ADD_LANGUAGE, apiKey, projectId, language);
        return "200".equals(wrapper.response.code);
    }
    
    public boolean deleteProjectLanguage(String projectId, String language){
        ResponseWrapper wrapper = service.editProjectLanguage(Action.DELETE_LANGUAGE, apiKey, projectId, language);
        return "200".equals(wrapper.response.code);
    }

    public boolean setProjectReferenceLanguage(String projectId, String language){
        ResponseWrapper wrapper = service.editProjectLanguage(Action.SET_REFERENCE_LANGUAGE, apiKey, projectId, language);
        return "200".equals(wrapper.response.code);
    }

    public boolean clearProjectReferenceLanguage(String projectId, String language){
        ResponseWrapper wrapper = service.clearProjectReferenceLanguage(Action.SET_REFERENCE_LANGUAGE, apiKey, projectId);
        return "200".equals(wrapper.response.code);
    }
    
    public List<Contributor> getProjectContributors(String projectId){
        ContributorsResponse cr = service.getContributors(Action.LIST_CONTRIBUTORS, apiKey, projectId);
        return cr.list;
    }

    /**
     * https://poeditor.com/api_reference/#Contributors
     * 
     * - language - language code (Required if adding a contributor)
     * - admin - 0 / 1 (Default 0; 1 for adding as administrator)
     * 
     * @param projectId
     * @param name
     * @param email
     * @return
     */
    public boolean addAdministrator(String projectId, String name, String email){
        Response response = service.addProjectMember(Action.ADD_CONTRIBUTOR, apiKey, projectId, name, email, null, 1);
        return "200".equals(response.code);
    }

    /**
     * https://poeditor.com/api_reference/#Contributors
     *
     * - language - language code (Required if adding a contributor)
     * - admin - 0 / 1 (Default 0; 1 for adding as administrator)
     *
     * @param projectId
     * @param name
     * @param email
     * @return
     */
    public boolean addContributor(String projectId, String name, String email, String language){
        Response response = service.addProjectMember(Action.ADD_CONTRIBUTOR, apiKey, projectId, name, email, language, 0);
        return "200".equals(response.code);
    }

    public TermsDetails addTerms(String projectId, List<Term> terms){
        String jsonTerms = new Gson().toJson(terms);
        AddTermsResponse atr = service.addTerms(Action.ADD_TERMS, apiKey, projectId, jsonTerms);
        return atr.details;
    }

    public File export(String projectId, String language, FileTypeEnum fte, FileTypeEnum[] filters){
        return export(projectId, language, fte, filters, null);
    }
    
    public File export(String projectId, String language, FileTypeEnum fte, FileTypeEnum[] filters, File exportFile){
        FileExport fe = service.export(Action.EXPORT, apiKey, projectId, language, fte.name().toLowerCase(), null);
        System.out.println(fe.item);
        
        try {
            if(exportFile != null){
                exportFile.createNewFile();
            } else {
                exportFile = File.createTempFile("poeditor-export-file-", ".tmp");
            }
            
            URL website = new URL(fe.item);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            
            FileOutputStream fos = new FileOutputStream(exportFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            return exportFile;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public UploadDetails upload(String projectId, File translationFile){
        //- updating - options (terms, terms_definitions, definitions) 
        TypedFile typedFile = new TypedFile("application/xml", translationFile);
        UploadResponse ur = service.upload("upload", apiKey, projectId, "terms", typedFile);
        return ur.details;
    }
}
