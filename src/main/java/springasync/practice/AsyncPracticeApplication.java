package springasync.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@SpringBootApplication
//@EnableAsync annotation은 @Async Method를 백그라운드에서 실행시키도록 변경합니다.
//taskExecutor라는 method를 통해 Executor를 설정 할 수 있습니다.
//Executor bean을 정의하지 않는 경우 SimpleAsyncTaskExecutor를 사용합니다.
//주의) 이때 SimpleAsyncTaskExecutor를 사용 할 경우 thread pool에서 스레드를 재활용하는 형식이 아님에 주의!!
//https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/task/SimpleAsyncTaskExecutor.html
//The @EnableAsync annotation switches on Spring’s ability to run @Async methods in a background thread pool.
//This class also customizes the Executor by defining a new bean. Here, the method is named taskExecutor,
//since this is the specific method name for which Spring searches.
//If you do not define an Executor bean, Spring creates a SimpleAsyncTaskExecutor and uses that.
@EnableAsync
public class AsyncPracticeApplication {

    public static void main(String[] args) {
        // close the application context to shut down the custom ExecutorService
        SpringApplication.run(AsyncPracticeApplication.class, args).close();
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //CorePoolSize의 경우 유지되는 최소 worker 수, MaxPoolSize의 경우 생성 될 수 있는 최대 worker 수
        //https://www.baeldung.com/java-threadpooltaskexecutor-core-vs-max-poolsize
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        //스레드 대기 큐
        executor.setQueueCapacity(500);
        //스레드명의 Prefix 설정
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }


}
