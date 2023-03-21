import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {

    private static final DailyPlanner DAILY_PLANNER = new DailyPlanner();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("d.MM.yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH.mm");


    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            removeTask(scanner);
                            break;
                        case 3:
                            getTaskPrint(scanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }

    }

    private static void printMenu() {
        System.out.println(
                """
                        1. Добавить задачу.
                        2. Удалить задачу.
                        3. Получить задачу на указанный день.
                        0. Выход.
                        """
        );
    }

    private static void inputTask(Scanner scanner) {
        String taskName = readString("Введите название задачи: ", scanner);
        String description = readString("Введите описание задачи: ", scanner);
        TaskType taskType = readType(scanner);
        LocalDateTime taskDate = readDateTime(scanner);
        Repeatability repeatability = readRepeatability(scanner);
        Task task = switch (repeatability) {

            case ONE_TIME -> new OneTime(taskName, description, taskType, taskDate);
            case DAILY -> new Daily(taskName, description, taskType, taskDate);
            case WEEKLY -> new Weekly(taskName, description, taskType, taskDate);
            case MONTHLY -> new Monthly(taskName, description, taskType, taskDate);
            case ANNUAL -> new Annual(taskName, description, taskType, taskDate);
        };
        DAILY_PLANNER.inputTasks(task);
    }

    private static TaskType readType(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Выберите тип задачи: ");
                for (TaskType value : TaskType.values()) {
                    System.out.println(value.ordinal() + ". " + tasksType(value));
                }
                System.out.println("Введите тип: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return TaskType.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Введен не верный номер типа задачи");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найдена");
            }
        }
    }

    private static LocalDateTime readDateTime(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        LocalTime localTime = readTime(scanner);
        return localDate.atTime(localTime);
    }

    private static Repeatability readRepeatability(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Выберите тип повторяемости задачи: ");
                for (Repeatability value : Repeatability.values()) {
                    System.out.println(value.ordinal() + ". " + repeatabilityType(value));
                }
                System.out.println("Введите тип повторяемости: ");
                String ordinalLine = scanner.nextLine();
                int ordinal = Integer.parseInt(ordinalLine);
                return Repeatability.values()[ordinal];
            } catch (NumberFormatException e) {
                System.out.println("Введен не верный номер типа задачи");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Тип задачи не найдена");
            }
        }
    }


    private static String readString(String message, Scanner scanner) {
        while (true) {
            System.out.println(message);
            String readString = scanner.nextLine();
            if (readString == null || readString.isBlank()) {
                System.out.println("Не верный тип");
            } else {
                return readString;
            }
        }
    }

    private static void removeTask(Scanner scanner) {
        System.out.println("Все задачи: ");
        for (Task allTask : DAILY_PLANNER.getAllTasks()) {
            System.out.printf("%d. %s %s %s %n", allTask.getId(),
                    allTask.getTaskName(),
                    tasksType(allTask.getTaskType()),
                    repeatabilityType(allTask.getRepeatability()));
        }
        while (true) {
            try {
                System.out.print("Введите задачи для удаления: ");
                String idCorrect = scanner.nextLine();
                int id = Integer.parseInt(idCorrect);
                DAILY_PLANNER.removeTask(id);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введен не верный id");
            } catch (TaskNotFoundException e) {
                System.out.println("Задача не найдена");
            }
        }
    }

    private static void getTaskPrint(Scanner scanner) {
        LocalDate localDate = readDate(scanner);
        Collection<Task> taskForDay = DAILY_PLANNER.getTasks(localDate);
        System.out.println("Задача на " + localDate.format(DATE_FORMAT));
        for (Task task : taskForDay) {
            System.out.printf("%s %s %s %s %n", task.getTaskName(),
                    task.getDescription(),
                    tasksType(task.getTaskType()),
                    task.getLocalDateAndTime().format(TIME_FORMAT));
        }
    }


    private static LocalDate readDate(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите дату задачи в формате: dd.MM.yyyy ");
                String days = scanner.nextLine();
                return LocalDate.parse(days, DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Введена дата в неверном формате");
            }
        }
    }

    private static LocalTime readTime(Scanner scanner) {
        while (true) {
            try {
                System.out.print("Введите время задачи в формате: HH.mm ");
                String days = scanner.nextLine();
                return LocalTime.parse(days, TIME_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Введено время в неверном формате");
            }
        }
    }

    private static String tasksType(TaskType taskType) {
        return switch (taskType) {
            case WORKING -> "Рабочая задача";
            case PERSONAL -> "Персональная задача";
        };
    }

    private static String repeatabilityType(Repeatability repeatability) {
        return switch (repeatability) {

            case ONE_TIME -> "Единовременная";
            case DAILY -> "Ежедневная";
            case WEEKLY -> "Еженедельная";
            case MONTHLY -> "Ежемесячная";
            case ANNUAL -> "Ежегодная";
        };
    }
}