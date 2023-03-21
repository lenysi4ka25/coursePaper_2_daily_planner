import java.time.LocalDate;
import java.time.LocalDateTime;

public class OneTime extends Task {


    public OneTime(String taskName, String description, TaskType taskType, LocalDateTime localDateAndTime) {
        super(taskName, description, taskType, localDateAndTime);
    }

    @Override
    public boolean taskDate(LocalDate localDate) {
        return localDate.equals(this.getLocalDateAndTime().toLocalDate());
    }

    @Override
    public Repeatability getRepeatability() {
        return Repeatability.ONE_TIME;
    }
}
