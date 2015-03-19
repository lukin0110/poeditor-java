Upload
======

Create and publish the archive: http://gradle.org/docs/current/userguide/artifact_management.html

```
gradle uploadArchives
```
This will build, sign and upload the archive.

Upload to Maven central
-----------------------

- Gradle upload: http://central.sonatype.org/pages/gradle.html
- OSSRH Guide: http://central.sonatype.org/pages/ossrh-guide.html
- Releasing: http://central.sonatype.org/pages/releasing-the-deployment.html
- Requirements: http://central.sonatype.org/pages/requirements.html
- Signing: http://central.sonatype.org/pages/working-with-pgp-signatures.html

New gradle publishing plugin
----------------------------

https://gradle.org/docs/current/userguide/publishing_maven.html

Publish to local repo:
```
gradle publishToMavenLocal
```

Publish:
```
gradle publish
```

Github tags
-----------
Create tag
```
git tag -a 0.3.1 -m '0.3.1'
```

Push tag:
```
git push origin --tags
```
