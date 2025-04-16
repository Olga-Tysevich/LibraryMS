package by.lms.libraryms.conf.jobs;

import by.lms.libraryms.domain.BookLending;
import by.lms.libraryms.repo.BookLendingRepo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
@Configuration
public class BookLendingDelayBatchConfig {

    @Bean
    public RepositoryItemReader<BookLending> bookLendingReader(BookLendingRepo repo) {
        RepositoryItemReader<BookLending> reader = new RepositoryItemReader<>();
        reader.setRepository(repo);
        reader.setMethodName("findByRequiredReturnDateIsNotNullAndActualReturnDateIsNull");
        reader.setArguments(Collections.emptyList());
        reader.setSort(Collections.singletonMap("requiredReturnDate", Sort.Direction.ASC));
        return reader;
    }

    @Bean
    public ItemProcessor<BookLending, BookLending> delayProcessor() {
        return bookLending -> {
            long delayDays = ChronoUnit.DAYS.between(bookLending.getRequiredReturnDate(), LocalDate.now());
            bookLending.setPeriodOfDelay(delayDays > 0 ? Instant.ofEpochSecond(delayDays * 86400) : Instant.EPOCH);
            return bookLending;
        };
    }

    @Bean
    public ItemWriter<BookLending> bookLendingWriter(BookLendingRepo repo) {
        return repo::saveAll;
    }

    @Bean
    public Step delayUpdateStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                RepositoryItemReader<BookLending> reader,
                                ItemProcessor<BookLending, BookLending> processor,
                                ItemWriter<BookLending> writer) {

        return new StepBuilder("delayUpdateStep", jobRepository)
                .<BookLending, BookLending>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job updateDelayJob(JobRepository jobRepository, Step delayUpdateStep) {
        return new JobBuilder("updateDelayJob", jobRepository)
                .start(delayUpdateStep)
                .build();
    }
}
