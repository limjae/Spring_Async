package springasync.practice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
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

    }

}