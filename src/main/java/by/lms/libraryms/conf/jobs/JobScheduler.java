package by.lms.libraryms.conf.jobs;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final JobLauncher jobLauncher;
    private final Job updateDelayJob;
    private final Job userCleanupJob;

    @Scheduled(cron = "0 0 2 * * *")
    public void runBookLendingJob() {
        runJob(updateDelayJob, "bookLending");
    }

    @Scheduled(cron = "0 0 3 * * MON")
    public void runUserCleanupJob() {
        runJob(userCleanupJob, "userCleanup");
    }

    //TODO лог
    private void runJob(Job job, String name) {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("run.id", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, params);
            System.out.println("✅ " + name + " job executed successfully.");
        } catch (Exception e) {
            System.err.println("❌ Failed to run " + name + " job: " + e.getMessage());
        }
    }
}