package by.lms.libraryms.conf.jobs;

import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.repo.UserRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Configuration
public class UserCleanupBatchConfig {

    @Value("${spring.data.mongodb.delete.delay}")
    private long deleteDelay;

    @Bean
    public RepositoryItemReader<User> userReader(UserRepo userRepo) {
        RepositoryItemReader<User> reader = new RepositoryItemReader<>();
        reader.setRepository(userRepo);
        reader.setMethodName("findByConfirmedFalseAndCreatedAtBefore");
        reader.setArguments(List.of(Instant.now().minusSeconds(deleteDelay)));
        reader.setSort(Map.of("createdAt", Sort.Direction.ASC));
        return reader;
    }

    @Bean
    public ItemProcessor<User, User> userProcessor() {
        return user -> user;
    }

    @Bean
    public ItemWriter<User> userWriter(UserRepo repo) {
        return repo::deleteAll;
    }

    @Bean
    public Step userCleanupStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                RepositoryItemReader<User> reader,
                                ItemProcessor<User, User> processor,
                                ItemWriter<User> writer) {
        return new StepBuilder("userCleanupStep", jobRepository)
                .<User, User>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job userCleanupJob(JobRepository jobRepository, Step userCleanupStep) {
        return new JobBuilder("userCleanupJob", jobRepository)
                .start(userCleanupStep)
                .build();
    }
}