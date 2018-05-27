all:
	protoc content_provider.proto --java_out=./java --cpp_out=./cpp --python_out=./python
	protoc cursor.proto --java_out=./java --cpp_out=./cpp --python_out=./python
	protoc intent.proto --java_out=./java --cpp_out=./cpp --python_out=./python
	protoc app_info.proto --java_out=./java --cpp_out=./cpp --python_out=./python
clean:
	rm -rf java cpp python
	mkdir java
	mkdir cpp
	mkdir python
