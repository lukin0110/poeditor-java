package be.lukin.poeditor;

import be.lukin.poeditor.models.FileExport;
import be.lukin.poeditor.models.Response;
import be.lukin.poeditor.models.UploadDetails;
import be.lukin.poeditor.response.*;
import retrofit.http.*;
import retrofit.mime.TypedFile;

public interface POEditorService {
    
    @FormUrlEncoded
    @POST("/")
    public ProjectsResponse getProjects(
            @Field("action") String action, 
            @Field("api_token") String token);

    @FormUrlEncoded
    @POST("/")
    public ProjectDetailResponse getProject(
            @Field("action") String action, 
            @Field("api_token") String token, 
            @Field("id") String projectId);

    @FormUrlEncoded
    @POST("/")
    public LanguagesResponse getProjectLanguages(
            @Field("action") String action,
            @Field("api_token") String token, 
            @Field("id") String projectId);
    
    @FormUrlEncoded
    @POST("/")
    public AvailableLanguagesResponse getAvailableLanguages(
            @Field("action") String action,
            @Field("api_token") String token);

    @FormUrlEncoded
    @POST("/")
    public FileExport export(
            @Field("action") String action, 
            @Field("api_token") String token, 
            @Field("id") String id, 
            @Field("language") String language, 
            @Field("type") String fileType, 
            @Field("filters") String[] filters);

    /**
     * https://poeditor.com/api_reference/#upload
     * Action: upload 
     * 
     * - updating - options (terms, terms_definitions, definitions)
     */
    @Multipart
    @POST("/")
    public UploadResponse upload(
            @Part("action") String action,
            @Part("api_token") String token,
            @Part("id") String projectId,
            @Part("updating") String updating,
            @Part("file") TypedFile file);

    @FormUrlEncoded
    @POST("/")
    public ContributorsResponse getContributors(
            @Field("action") String action,
            @Field("api_token") String token,
            @Field("id") String projectId);
  
    @FormUrlEncoded
    @POST("/")
    public Response addProjectMember(
            @Field("action") String action,
            @Field("api_token") String token,
            @Field("id") String projectId,
            @Field("name") String name,
            @Field("email") String email,
            @Field("language") String language,
            @Field("admin") int admin
    );
    
    @FormUrlEncoded
    @POST("/")
    public AddTermsResponse addTerms(
            @Field("action") String action,
            @Field("api_token") String token,
            @Field("id") String projectId,
            @Field("data")String terms
    );

    /**
     * https://poeditor.com/api_reference/#add_language_to_project
     * Action: add_language
     */
    @FormUrlEncoded
    @POST("/")
    public ResponseWrapper editProjectLanguage(
            @Field("action") String action,
            @Field("api_token") String token,
            @Field("id") String projectId,
            @Field("language") String language);

    /**
     * https://poeditor.com/api_reference/#clear_reference_language
     * Action: clear_reference_language
     */
    @FormUrlEncoded
    @POST("/")
    public ResponseWrapper clearProjectReferenceLanguage(
            @Field("action") String action,
            @Field("api_token") String token,
            @Field("id") String projectId);
}
