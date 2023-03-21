import java.time.LocalDate;
import java.time.LocalDateTime;

public class Monthly extends Task {
    public Monthly(String taskName, String description, TaskType taskType, LocalDateTime localDateAndTime) {
        super(taskName, description, taskType, localDateAndTime);
    }

    @Override
    public boolean taskDate(LocalDate localDate) {
        LocalDate taskDate = this.getLocalDateAndTime().toLocalDate();
        return taskDate.equals(localDate) || (localDate.isAfter(taskDate) &&
                localDate.getDayOfMonth() == taskDate.getDayOfMonth());
    }

    @Override
    public Repeatability getRepeatability() {
        return Repeatability.MONTHLY;
    }
}
