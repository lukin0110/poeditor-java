# Todo

- local gradle test project
- setup unit tests
- prepare for maven central: pom info, javadocs, apply form
- make apiary stub

- implement all api calls
    def update_project_language(self, project_id, language_code, data):
    def sync_terms(self, project_id, data):
    def update_terms(self, project_id, file_path=None, language_code=None,
                     overwrite=False, sync_terms=False, tags=None):
    def update_terms_definitions(self, project_id, file_path=None,
                                 language_code=None, overwrite=False,
                                 sync_terms=False, tags=None):
    def update_definitions(self, project_id, file_path=None,
                           language_code=None, overwrite=False):

