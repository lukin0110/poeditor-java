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

    /**
     *
     * @param action upload
     * @param token api key
     * @param projectId id of the project
     * @param language - language code (Required only if type is terms_definitions or definitions)
     * @param fileType - which file format to export
     * @param filters - filter results by 'translated', 'untranslated', 'fuzzy', 'not_fuzzy', 'automatic', 'not_automatic', 'proofread', 'not_proofread' (only available when Proofreading is set to "Yes" in Project Settings); you can use either a string for a single filter or a json array for one or multiple filters (Optional)
     * @param tags - filter results by tags; you can use either a string for a single tag or a json array for one or multiple tags (Optional).
     * @return FileExport
     */
    @FormUrlEncoded
    @POST("/")
    public FileExport export(
            @Field("action") String action, 
            @Field("api_token") String token, 
            @Field("id") String projectId,
            @Field("language") String language, 
            @Field("type") String fileType, 
            @Field("filters") String[] filters, 
            @Field("tags") String tags);
    
    /**
     * https://poeditor.com/api_reference/#upload
     * 
     * @param action upload
     * @param token api key
     * @param projectId id of the project
     * @param updating - options (terms, terms_definitions, definitions)
     * @param file file to upload
     * @param language - language code (Required only if type is terms_definitions or definitions)
     * @param overwrite - set it to 1 if you want to overwrite definitions
     * @param tags - add tags to the project terms; available when updating "terms" or "terms_definitions"
     * @return UploadResponse
     */
    @Multipart
    @POST("/")
    public UploadResponse upload(
            @Part("action") String action,
            @Part("api_token") String token,
            @Part("id") String projectId,
            @Part("updating") String updating,
            @Part("file") TypedFile file, 
            @Part("language") String language, 
            @Part("overwrite") String overwrite, 
            @Part("tags") String tags);

    @FormUrlEncoded
    @POST("/")
    public ContributorsResponse getContributors(
            @Field("action") String action,
            @Field("api_token") String token,
            @Field("id") String projectId);

    /**
     * https://poeditor.com/api_reference/#Contributors
     *
     * - language - language code (Required if adding a contributor)
     * - admin - 0 / 1 (Default 0; 1 for adding as administrator)
     *
     * @param action value must be add_contributor
     * @param token api key
     * @param projectId id of the project
     * @param name name of the contributor/admin
     * @param email email of the contributor/admin
     * @param language language to contribute to
     * @param admin is it an admin or contributor
     * @return ResponseWrapper
     */
    @FormUrlEncoded
    @POST("/")
    public ResponseWrapper addProjectMember(
            @Field("action") String action,
            @Field("api_token") String token,
            @Field("id") String projectId,
            @Field("name") String name,
            @Field("email") String email,
            @Field("language") String language,
            @Field("admin") int admin
    );

    /**
     * *
     * @param action add_terms or delete_terms
     * @param token api key
     * @param projectId id of the project
     * @param terms json string with a list of terms
     * @return TermsResponse
     */
    @FormUrlEncoded
    @POST("/")
    public EditTermsResponse editTerms(
            @Field("action") String action,
            @Field("api_token") String token,
            @Field("id") String projectId,
            @Field("data")String terms
    );

    /**
     * https://poeditor.com/api_reference/#add_language_to_project
     * * 
     * @param action add_language
     * @param token api key
     * @param projectId id of the project
     * @param language language to edit
     * @return ResponseWrapper
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
     * * 
     * @param action clear_reference_language
     * @param token api key
     * @param projectId id of the project
     * @return ResponseWrapper
     */
    @FormUrlEncoded
    @POST("/")
    public ResponseWrapper clearProjectReferenceLanguage(
            @Field("action") String action,
            @Field("api_token") String token,
            @Field("id") String projectId);

    /**
     * https://poeditor.com/api_reference/#create_project
     *
     * @param action value must be create_project
     * @param token api key
     * @param name name of the project that you want to create
     * @return ResponseWrapper
     */
    @FormUrlEncoded
    @POST("/")
    public ResponseWrapper createProject(
            @Field("action") String action,
            @Field("api_token") String token,
            @Field("name") String name);
    
    /**
     * https://poeditor.com/api_reference/#view_project_terms
     *
     * @param action value must be view_terms
     * @param token api key
     * @param projectId id of the project
     * @param language language in which you want to show the terms
     * @return ViewTermsResponse
     */
    @FormUrlEncoded
    @POST("/")
    public ViewTermsResponse viewProjectTerms(
            @Field("action") String action,
            @Field("api_token") String token,
            @Field("id") String projectId, 
            @Field("language") String language);
    
    
}
