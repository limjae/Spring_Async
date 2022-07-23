# Spring Async Method Practice.

## [Spring.io - Creating Asynchronous Methods](https://spring.io/guides/gs/async-method/)
### Spring의 Asynchronous Method에 대한 학습
약간의 번역을 해두었으나 자유롭게 번역하였기에 원문도 함께 기록했습니다.</br>
한글의 경우 참고만 해주시거나 자유롭게 피드백해주시면 감사합니다!</br></br>

### 개요
You will build a lookup service that queries GitHub user information and retrieves data through GitHub’s API.</br>
One approach to scaling services is to run expensive jobs in the background and wait for the results by using Java’s CompletableFuture interface.</br>
Java’s CompletableFuture is an evolution from the regular Future.</br>
It makes it easy to pipeline multiple asynchronous operations and merge them into a single asynchronous computation.</br>

Github API를 사용하여 Github User 정보를 조회하고 데이터를 검색하는 lookup service를 구현하고자 합니다.</br>
이 서비스를 확장하기 위한 접근 방법으로 Java의 CompletableFuture 인터페이스를 사용해 백그라운드에서 해당 작업을 처리하고 기다리는 것입니다.</br>
CompletableFuture는 기존 Future에서 발전한 클래스입니다.</br>
CompletableFuture는 여러 비동기 연산을 쉽게 파이프라인하고 단일 비동기 연산에 병합할 수 있습니다.</br></br>


참고</br>
[Guide To CompletableFuture - Baeldung](https://www.baeldung.com/java-completablefuture) - 추가 학습 예정</br>
[Class CompletableFuture\<T\> - Oracle](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)</br>
[Class SimpleAsyncTaskExecutor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/task/SimpleAsyncTaskExecutor.html)</br></br>

### 사용한 Dependencies
- Spring Web
- Lombok</br></br>

## 요약
### 1. /data/User.java
- GitHub Api를 통해 검색된 사용자 정보를 저장하기 위해 정의한 클래스입니다.

### 2. /service/GitHubLookupService.java
- GitHub Api를 호출하고 User를 반환하는 method를 CompletableFuture와 @Async를 통해 구현합니다.

### 3. /AsyncPracticeApplication.java
- @Async를 사용하기 위해 필요한 것들을 설정합니다.

### 4. /AppRunner.java
- 1~3. 을 통해 구현한 것들을 테스트 해보는 코드입니다.
- 2.에서 @Async를 사용할 때의 유의사항에 대한 Case를 함께 추가했습니다.



