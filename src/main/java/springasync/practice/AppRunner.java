package springasync.practice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import springasync.practice.data.User;
import springasync.practice.service.GitHubLookupService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
//CommandLineRunner 인터페이스를 구현하고 @Component annotaion을 사용하면 Compoent Scan 이후 구동시점에 run이 실행됩니다.
//https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/CommandLineRunner.html
public class AppRunner implements CommandLineRunner {
    private final GitHubLookupService gitHubLookupService;

    @Override
    public void run(String... args) throws Exception {

        // See Proxy
        GitHubLookupService gitHubLookupService2 = new GitHubLookupService(new RestTemplateBuilder());

        System.out.println("gitHubLookupService = " + gitHubLookupService.getClass());
        System.out.println("gitHubLookupService2 = " + gitHubLookupService2.getClass());

//        Method method1 = gitHubLookupService.getClass().getMethod("method1");
//        Method method2 = gitHubLookupService2.getClass().getMethod("method1");
//        System.out.println("method1 = " + method1);
//        System.out.println("method2 = " + method2);

        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        List<CompletableFuture<User>> pages = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            pages.add(gitHubLookupService.findUser("PivotalSoftware"));
            pages.add(gitHubLookupService.findUser("CloudFoundry"));
            pages.add(gitHubLookupService.findUser("Spring-Projects"));
            pages.add(gitHubLookupService.findUser("limjae"));
        }

        // Wait until they are all done
        CompletableFuture.allOf(pages.toArray(new CompletableFuture[0])).join();


        // Print results, including elapsed time
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
        for(CompletableFuture<User> page : pages)
        {
            log.info("--> " + page.get());
        }

        log.info("-------------------------------------------------------------");
        log.info("---------------------self invoke-----------------------------");
        long start2 = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        List<CompletableFuture<User>> pages2 = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            pages2.add(gitHubLookupService.selfInvokeFindUser("PivotalSoftware"));
            pages2.add(gitHubLookupService.selfInvokeFindUser("CloudFoundry"));
            pages2.add(gitHubLookupService.selfInvokeFindUser("Spring-Projects"));
            pages2.add(gitHubLookupService.selfInvokeFindUser("limjae"));
        }

        // Wait until they are all done
        CompletableFuture.allOf(pages2.toArray(new CompletableFuture[0])).join();


        // Print results, including elapsed time
        log.info("Elapsed time: " + (System.currentTimeMillis() - start2));
        for(CompletableFuture<User> page : pages2)
        {
            log.info("--> " + page.get());
        }

    }

}