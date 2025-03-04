import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int id = 0;
    private HashMap<Integer, Task> simpleTasks = new HashMap<>();
    private HashMap<Integer, Epic> epicTasks = new HashMap<>();
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();

    public Manager() {

    }

    public int generateId() {
        id++;
        return id;
    }

    public void createTask(Task task) {
        task.setStatus("new");
        simpleTasks.put(task.getId(), task);
    }


    public void createEpicTask(Epic epic) {
        epic.setStatus("new");
        epicTasks.put(epic.getId(), epic);
    }

    public void createSubTask(Subtask subtask) {
        subtask.setStatus("new");
        subTasks.put(subtask.getId(), subtask);

    }

    public void updateTask(Task task) {
        simpleTasks.put(task.getId(), task);
    }

    public void updateSubTask(Subtask subtask) {
        updateEpicStatus(subtask);
        subTasks.put(subtask.getId(), subtask);

    }
    public void deleteTask(Task task) {
        simpleTasks.remove(task.getId(), task); // удаление задачи по идентификатору
    }

    public void deleteSubTask(Subtask subtask) { // удаление подзадачи по идентификатору
        subTasks.remove(subtask.getId(), subtask);
    }

    public void deleteEpicTask(Epic epic) { //при удалении эпика нужно чтобы удалялись все субтаски
        epicTasks.remove(epic.getId(), epic);
        for (Subtask sub : subTasks.values()) {
            if (epic.getId() == sub.getEpicId()) {
                subTasks.remove(sub.getId(), sub);
            }
        }
    }

    public void clearTask() {
        simpleTasks.clear(); // Удаление всех задач.
    }

    public void clearEpicTask(Epic epic) {
        epicTasks.clear(); // Удаление всех эпиков.
        subTasks.clear();
    }

    public Task getTask(int id) {
        return simpleTasks.get(id); //Получение задачи по идентификатору
    }

    public Epic getEpicTask(int id) {
        return epicTasks.get(id); //Получение эпика по идентификатору
    }

    public Subtask getSubTask(int id) {
        return subTasks.get(id); //Получение субтаски по идентификатору
    }

    public ArrayList<Task> getAllTask() {
        ArrayList<Task> tasklist = new ArrayList<>(simpleTasks.values()); //Получение списка всех задач
        return tasklist;
    }

    public ArrayList<Task> getAllEpics() {
        ArrayList<Task> epiclist = new ArrayList<>(epicTasks.values()); //Получение списка всех эпиков
        return epiclist;
    }

    public ArrayList<Task> getAllSubtasks() {
        ArrayList<Task> subtasklist = new ArrayList<>(subTasks.values()); //Получение списка всех подзадач
        return subtasklist;
    }

    private void updateEpicStatus(Subtask subtask) {
        Epic epic = epicTasks.get(subtask.getEpicId());
        int allDone = 0;

        String statusInProgress = "inProgress";
        String statusDone = "done";
        HashMap<Integer, Subtask> subTaskMap = epic.getSubTaskMap();
        for (Subtask sub : subTaskMap.values()) {
            String newstatus = sub.getStatus();
            if (statusInProgress.equals(newstatus)) { //((epic.getSubTaskMap() != null) && (statusInProgress.equals(newstatus) || statusDone.equals(newstatus)))
                epic.setStatus("inProgress");
                break;
            }
            if ((epic.getSubTaskMap() != null) && statusDone.equals(newstatus)) {
                allDone++;
            }
        }
        if (allDone == subTaskMap.size()) {
            epic.setStatus("done");
        } else if (allDone > 0 && allDone != subTaskMap.size()) {
            epic.setStatus("inProgress");
        }
        epicTasks.put(epic.getId(), epic);
    }

}
