package be.lukin.poeditor;

import be.lukin.poeditor.exceptions.PermissionDeniedException;
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
import java.util.Objects;

/**
 * Client to access the API of POEditor. Your API key can be found on My Account &gt; API Access.
 * 
 * @see <a href="https://poeditor.com/api/">API Endpoint</a>
 * @see <a href="https://poeditor.com/api_reference/">API Documentation</a>
 */
public class POEditorClient {
    
    public static final String HOST = "https://poeditor.com/api/";
    
    public static class Action {
        public static final String LIST_PROJECTS = "list_projects";
        public static final String VIEW_PROJECT = "view_project";
        public static final String VIEW_TERMS = "view_terms";
        public static final String CREATE_PROJECT = "create_project";
        public static final String LIST_LANGUAGES = "list_languages";
        public static final String AVAILABLE_LANGUAGES = "available_languages";
        public static final String LIST_CONTRIBUTORS = "list_contributors";
        public static final String ADD_CONTRIBUTOR = "add_contributor";
        public static final String ADD_TERMS = "add_terms";
        public static final String DELETE_TERMS = "delete_terms";
        public static final String ADD_LANGUAGE = "add_language";
        public static final String DELETE_LANGUAGE = "delete_language";
        public static final String SET_REFERENCE_LANGUAGE = "set_reference_language";
        public static final String CLEAR_REFERENCE_LANGUAGE = "clear_reference_language";
        public static final String EXPORT = "export";
    }
    
    private String apiKey;
    private String endpoint;
    private RestAdapter.LogLevel logLevel = RestAdapter.LogLevel.NONE;
    private POEditorService service;

    /**
     * Create a client instance. 
     *  
     * @param apiKey api key to authenticate
     */
    public POEditorClient(String apiKey) {
        this(apiKey, HOST, RestAdapter.LogLevel.NONE);
    }

    /**
     * Create a client instance with a custom endpoint. This allows you to access a stubbed endpoint, used for testing.
     *  
     * @param apiKey api key
     * @param endpoint custom endpoint
     * @param logLevel desired logLevel for Retrofit
     */
    public POEditorClient(String apiKey, String endpoint, RestAdapter.LogLevel logLevel){
        Objects.requireNonNull(apiKey, "apiKey is required");
        Objects.requireNonNull(endpoint, "endpoint is required");
        this.apiKey = apiKey;
        this.endpoint = endpoint;
        this.logLevel = logLevel != null ? logLevel : RestAdapter.LogLevel.NONE;
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
                //.setLogLevel(RestAdapter.LogLevel.FULL)
                .setLogLevel(logLevel)
                .setRequestInterceptor(requestInterceptor)
                .setClient(new OkClient())
                .setEndpoint(this.endpoint).build();
    }
    
    public List<Project> getProjects(){
        return service.getProjects(Action.LIST_PROJECTS, apiKey).list;
    }
    
    public Project getProject(String projectId){
        ProjectDetailResponse pdr = service.getProject(Action.VIEW_PROJECT, apiKey, projectId);
        ApiUtils.checkResponse(pdr.response);
        return pdr.item;
    }
    
    public Project createProject(String name){
        ResponseWrapper wrapper = service.createProject(Action.CREATE_PROJECT, apiKey, name);
        ApiUtils.checkResponse(wrapper.response);
        
        if("200".equals(wrapper.response.code) && wrapper.response.item != null) {
            return getProject(Integer.toString(wrapper.response.item.id));
        }
        
        return null;
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
        ApiUtils.checkResponse(lr.response);
        return lr.list;
    }
    
    public boolean addProjectLanguage(String projectId, String language){
        ResponseWrapper wrapper = service.editProjectLanguage(Action.ADD_LANGUAGE, apiKey, projectId, language);
        return "200".equals(wrapper.response.code);
    }
    
    public boolean deleteProjectLanguage(String projectId, String language){
        ResponseWrapper wrapper = service.editProjectLanguage(Action.DELETE_LANGUAGE, apiKey, projectId, language);
        ApiUtils.checkResponse(wrapper.response);
        return "200".equals(wrapper.response.code);
    }

    public boolean setProjectReferenceLanguage(String projectId, String language){
        ResponseWrapper wrapper = service.editProjectLanguage(Action.SET_REFERENCE_LANGUAGE, apiKey, projectId, language);
        ApiUtils.checkResponse(wrapper.response);
        return "200".equals(wrapper.response.code);
    }

    public boolean clearProjectReferenceLanguage(String projectId, String language){
        ResponseWrapper wrapper = service.clearProjectReferenceLanguage(Action.CLEAR_REFERENCE_LANGUAGE, apiKey, projectId);
        ApiUtils.checkResponse(wrapper.response);
        return "200".equals(wrapper.response.code);
    }
    
    public List<Contributor> getProjectContributors(String projectId){
        ContributorsResponse cr = service.getContributors(Action.LIST_CONTRIBUTORS, apiKey, projectId);
        ApiUtils.checkResponse(cr.response);
        return cr.list;
    }

    /**
     * Create a new admin for a project
     *  
     * @param projectId id of the project
     * @param name name of the admin
     * @param email email of the admin
     * @return boolean if the administrator has been created
     */
    public boolean addAdministrator(String projectId, String name, String email){
        ResponseWrapper wrapper = service.addProjectMember(Action.ADD_CONTRIBUTOR, apiKey, projectId, name, email, null, 1);
        return "200".equals(wrapper.response.code);
    }

    /**
     * Add/create a contributor for a language of a project
     * 
     * @param projectId id of the project
     * @param name name of the contributor
     * @param email email of the contributor
     * @param language language for the contributor              
     * @return boolean if the contributor has been added
     */
    public boolean addContributor(String projectId, String name, String email, String language){
        ResponseWrapper wrapper = service.addProjectMember(Action.ADD_CONTRIBUTOR, apiKey, projectId, name, email, language, 0);
        ApiUtils.checkResponse(wrapper.response);
        return "200".equals(wrapper.response.code);
    }

    /**
     * Add new terms to a project
     *  
     * @param projectId id of the project
     * @param terms list of terms
     * @return details about the operation
     */
    public TermsDetails addTerms(String projectId, List<Term> terms){
        String jsonTerms = new Gson().toJson(terms);
        EditTermsResponse atr = service.editTerms(Action.ADD_TERMS, apiKey, projectId, jsonTerms);
        ApiUtils.checkResponse(atr.response);
        return atr.details;
    }
    
    public TermsDetails deleteTerms(String projectId, List<Term> terms){
        String jsonTerms = new Gson().toJson(terms);
        EditTermsResponse etr = service.editTerms(Action.DELETE_TERMS, apiKey, projectId, jsonTerms);
        ApiUtils.checkResponse(etr.response);
        return etr.details;
    }

    /**
     * Export a translation for a language of a project.
     *
     * @param projectId id of the project
     * @param language language code
     * @param fte which type to export
     * @param filters which filter to apply
     * @return file object of the exported file
     */
    public File export(String projectId, String language, FileTypeEnum fte, FileTypeEnum[] filters){
        return export(projectId, language, fte, filters, null);
    }
    
    public File export(String projectId, String language, FileTypeEnum fte, FileTypeEnum[] filters, File exportFile){
        FileExport fe = service.export(Action.EXPORT, apiKey, projectId, language, fte.name().toLowerCase(), null);
        //System.out.println(fe.item);
        
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

    /**
     * Uploads a translation file. For the moment it only takes terms into account.
     *
     * @param projectId id of the project
     * @param translationFile terms file to upload
     * @return UploadDetails
     */
    public UploadDetails uploadTerms(String projectId, File translationFile){
        TypedFile typedFile = new TypedFile("application/xml", translationFile);
        UploadResponse ur = service.upload("upload", apiKey, projectId, "terms", typedFile, null, "0");
        ApiUtils.checkResponse(ur.response);
        return ur.details;
    }
    
    public UploadDetails uploadLanguage(String projectId, File translationFile, String language, boolean overwrite){
        TypedFile typedFile = new TypedFile("application/xml", translationFile);
        String _overwrite = overwrite ? "1" : "0";
        UploadResponse ur = service.upload("upload", apiKey, projectId, "definitions", typedFile, language, _overwrite);
        ApiUtils.checkResponse(ur.response);
        return ur.details;
    }
    
    public List<Term> viewTerms(String projectId){
        ViewTermsResponse response = service.viewProjectTerms(Action.VIEW_TERMS, apiKey, projectId, null);
        ApiUtils.checkResponse(response.response);
        return response.list;
    }

    @Override
    public String toString() {
        return "POEditorClient{" +
                "endpoint='" + endpoint + '\'' +
                ", apiKey='" + apiKey + '\'' +
                '}';
    }
}
