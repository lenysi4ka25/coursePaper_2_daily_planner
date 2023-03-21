import java.time.LocalDate;
import java.util.*;

public class DailyPlanner {

    private final Map<Integer, Task> tasks = new HashMap<>();


    public void inputTasks(Task task) {
        this.tasks.put(task.getId(), task);
    }

    public Collection<Task> getTasks(LocalDate date) {
        TreeSet<Task> arraysTaskDate = new TreeSet<>();
        for (Task task : tasks.values()) {
            if (task.taskDate(date)) {
                arraysTaskDate.add(task);
            }
        }
        return arraysTaskDate;
    }

    public void removeTask(int id) throws TaskNotFoundException {
        if (tasks.containsKey(id)) {
            this.tasks.remove(id);
        } else {
            throw new TaskNotFoundException();
        }

    }

    public Collection<Task> getAllTasks() {
        return this.tasks.values();
    }

}
