import java.time.LocalDate;
import java.time.LocalDateTime;

public class Annual extends Task {
    public Annual(String taskName, String description, TaskType taskType, LocalDateTime localDateAndTime) {
        super(taskName, description, taskType, localDateAndTime);
    }

    @Override
    public boolean taskDate(LocalDate localDate) {
        LocalDate taskDate = this.getLocalDateAndTime().toLocalDate();
        return localDate.equals(taskDate) || (localDate.isAfter(taskDate) &&
                localDate.getDayOfMonth() == taskDate.getDayOfMonth() &&
                localDate.getMonth() == taskDate.getMonth());
    }

    @Override
    public Repeatability getRepeatability() {
        return Repeatability.ANNUAL;
    }
}
