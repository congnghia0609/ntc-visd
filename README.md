# ntc-visd
ntc-visd is a tool Vietnamese Sentence Detector using Apache OpenNLP  

## Quick start
```bash
# Build
mvn package
or
mvn -Dmaven.test.skip=true package

# Start
./runservice start development

# Clean
mvn clean install
```

## Call API Sentence Detector
```bash
# Request
curl -X POST -i 'http://localhost:8787/sd/v1/post' \
  -H "Content-Type: application/json" \
  --data '{
    "s": "Treap là cây nhị phân có thể lưu trữ nội dung của một mảng theo cách mà chúng ta có thể tách một mảng thành hai mảng và hợp nhất hai mảng thành một mảng một cách hiệu quả. Mỗi nút trong treap có hai giá trị: trọng số (weight) và giá trị (value). Trọng số của mỗi nút thì nhỏ hơn hoặc bằng trọng số của các nút con và vị trí của nút trong mảng thì nằm sau tất cả các nút nằm trong nhánh con bên trái và đứng trước tất cả các nút trong nhánh con bên phải."
  }'

# Response
{"err":0,"msg":"Sentence detector successful","data":{"ls":["Treap là cây nhị phân có thể lưu trữ nội dung của một mảng theo cách mà chúng ta có thể tách một mảng thành hai mảng và hợp nhất hai mảng thành một mảng một cách hiệu quả.","Mỗi nút trong treap có hai giá trị: trọng số (weight) và giá trị (value).","Trọng số của mỗi nút thì nhỏ hơn hoặc bằng trọng số của các nút con và vị trí của nút trong mảng thì nằm sau tất cả các nút nằm trong nhánh con bên trái và đứng trước tất cả các nút trong nhánh con bên phải."]}}
```


## License
This code is under the [Apache License v2](https://www.apache.org/licenses/LICENSE-2.0).  
