import java.time.LocalDate;
import java.time.LocalDateTime;

public class Daily extends Task {
    public Daily(String taskName, String description, TaskType taskType, LocalDateTime localDateAndTime) {
        super(taskName, description, taskType, localDateAndTime);
    }

    @Override
    public boolean taskDate(LocalDate localDate) {
        LocalDate taskDate = this.getLocalDateAndTime().toLocalDate();
        return localDate.equals(taskDate) || localDate.isAfter(taskDate);
    }

    @Override
    public Repeatability getRepeatability() {
        return Repeatability.DAILY;
    }
}
