poeditor-java
=============
[![Build Status](https://travis-ci.org/lukin0110/poeditor-java.svg)](https://travis-ci.org/lukin0110/poeditor-java)

Client for the [POEditor API][1] in Java. Manage translation projects in Java.

To suggest changes, please submit an [Issue](https://github.com/lukin0110/poeditor-java/issues/new)
or [Pull Request](https://github.com/lukin0110/poeditor-java/compare/).

Download
--------

Download [the latest JAR][2] or grab via Maven:
```xml
<dependency>
  <groupId>be.lukin.poeditor</groupId>
  <artifactId>poeditor-client</artifactId>
  <version>0.3.0</version>
</dependency>
```
or gradle:
```groovy
compile 'be.lukin.poeditor:poeditor-java:0.3.0'
```

Usage
-----
```java
// Create a client with your api key
POEditorClient client = new POEditorClient("your api key");

// Fetch project details
Project project = client.client.getProject("your project Id");

// Download a translation for Android
File french = client.export("your project Id", "fr", FileTypeEnum.APPLE_STRINGS, null);

// Upload a language and do not overwrite existing translations, only a new translations
File dutch = new File("./android.xml");
UploadDetails details1 = client.uploadLanguage("your project Id", dutch, "nl", false);

// Upload terms
UploadDetails details2 = client.uploadTerms("your project Id", dutch);
```

License
=======

    Copyright 2015 Maarten Huijsmans

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 [1]: https://poeditor.com/api_reference/
 [2]: http://search.maven.org/remotecontent?filepath=be/lukin/poeditor/poeditor-client/0.3.0/poeditor-client-0.3.0.jar
