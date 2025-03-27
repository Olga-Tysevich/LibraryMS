package by.lms.libraryms.conf.jobs;

import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.repo.UserRepo;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.Collections;

@Configuration
public class UserCleanupBatchConfig {
    @Value("spring.data.mongodb.delete.delay")
    private int deleteDelay;

    @Bean
    public RepositoryItemReader<User> reader(UserRepo userRepo) {
        RepositoryItemReader<User> reader = new RepositoryItemReader<>();
        reader.setRepository(userRepo) ;
        reader.setMethodName("findByIsConfirmedFalseAndCreatedAtBefore");
        reader.setArguments(Collections.singletonList(Instant.now().minusSeconds(deleteDelay)));
        reader.setSort(Collections.singletonMap("createdAt", Sort.Direction.ASC));
        return reader;
    }
}
