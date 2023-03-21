import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class Task implements Comparable<Task> {

    private final int id;
    private static int count = 0;
    private final String taskName;
    private final String description;
    private final TaskType taskType;
    private final LocalDateTime localDateAndTime;

    public Task(String taskName, String description, TaskType taskType, LocalDateTime localDateAndTime) {
        this.id = count++;
        this.taskName = taskName;
        this.description = description;
        this.taskType = taskType;
        this.localDateAndTime = localDateAndTime;
    }


    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public LocalDateTime getLocalDateAndTime() {
        return localDateAndTime;
    }

    public abstract boolean taskDate(LocalDate localDate);
    public abstract Repeatability getRepeatability();

    @Override
    public int compareTo(Task otherTask) {
        if (otherTask == null) {
            return 1;
        }
        return this.localDateAndTime.toLocalTime().compareTo(otherTask.localDateAndTime.toLocalTime());
    }
}
