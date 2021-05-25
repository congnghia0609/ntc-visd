# ntc-visd
ntc-visd is a library Vietnamese Sentence Detector using Apache OpenNLP  

## Maven
```Xml
<dependency>
    <groupId>com.streetcodevn</groupId>
    <artifactId>ntc-visd</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Build local
```bash
# Build
mvn package
or
mvn -Dmaven.test.skip=true package

# Clean
mvn clean install
```

## Quick start
```java
ViSD visd = new ViSD();
String s = "Đây là câu 1. Đây là câu 2. Đây là câu 3.";

// Sentence Detector
List<String> sents = visd.visd2List(s);
System.out.println(sents);

// or

String[] sents = visd.visd(s);
System.out.println(sents);
```


## License
This code is under the [Apache License v2](https://www.apache.org/licenses/LICENSE-2.0).  
