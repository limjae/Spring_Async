package springasync.practice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import springasync.practice.data.User;

import java.util.concurrent.CompletableFuture;

//GitHubLookupService 클래스는 스프링의 RestTemplate을 이용하여 REST point "api.github.com/users/" 를 호출하여 값을 받아 User 객체에 값을 저장합니다.
//Spring Boot에서는 MessageConverter를 기본값으로 사용하는 RestTemplateBuilder를 자동으로 제공합니다.
//The GitHubLookupService class uses Spring’s RestTemplate to invoke a remote REST point (api.github.com/users/) and then convert the answer into a User object.
//Spring Boot automatically provides a RestTemplateBuilder that customizes the defaults with any auto-configuration bits (that is, MessageConverter).

//@Service annotation은 Spring의 컴포넌트 스캔의 대상이 되도록 지정합니다.
//The class is marked with the @Service annotation, making it a candidate for Spring’s component scanning to detect and add to the application context.
@Service
// private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class); 를 대체(번외)
@Slf4j
@RequiredArgsConstructor
public class GitHubLookupService {

    private final RestTemplateBuilder restTemplateBuilder;

    //@Async 어노테이션을 사용하여 스프링에 해당 메소드는 독립적인 스레드에서 동작해야 함을 표시합니다.
    //The findUser method is flagged with Spring’s @Async annotation, indicating that it should run on a separate thread.
    @Async
    //User 대신 CompletableFuture<User>를 리턴 타입으로 사용하는데 이는 비동기 처리를 위한 요구사항입니다.
    //CompletableFuture.completedFuture(results)를 호출하여 Github API 요청의 결과를 담은 CompletableFuture의 인스턴스를 리턴합니다.
    //The method’s return type is CompletableFuture<User> instead of User, a requirement for any asynchronous service.
    //This code uses the completedFuture method to return a CompletableFuture instance that is already completed with result of the GitHub query.
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        log.info("Looking up " + user);
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        // Artificial delay of 1s for demonstration purposes
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }

    public CompletableFuture<User> selfInvokeFindUser(String user) throws InterruptedException {
        return findUser(user);
    }

    public void method1(){
        System.out.println("GitHubLookupService.method1");
    }



}
// 주의사항!!
// self-invoke하거나 local instance로 GitHubLookupService class로 생성하면 비동기로 처리가 안됩니다. ComponentScan의 대상이 되어야합니다. method가 public이여야 합니다.
// Creating a local instance of the GitHubLookupService class does NOT allow the findUser method to run asynchronously.
// It must be created inside a @Configuration class or picked up by @ComponentScan.
// 원인 : https://dzone.com/articles/effective-advice-on-spring-async-part-1
