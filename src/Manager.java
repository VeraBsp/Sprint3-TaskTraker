import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int id = 0;
    private HashMap<Integer, Task> simpleTasks = new HashMap<>();
    private HashMap<Integer, Epic> epicTasks = new HashMap<>();
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();

    public static final String NEW_STATUS = "NEW";
    public static final String DONE_STATUS = "DONE";
    public static final String IN_PROGRESS_STATUS = "IN_PROGRESS";

    public Manager() {

    }

    public void createTask(Task task) {
        id++;
        task.setId(id);
        task.setStatus(NEW_STATUS);
        simpleTasks.put(task.getId(), task);
    }

    public void createEpicTask(Epic epic) {
        id++;
        epic.setId(id);
        epic.setStatus(NEW_STATUS);
        epicTasks.put(epic.getId(), epic);
    }

    public void createSubTask(Subtask subtask, int epicID) {
        id++;
        subtask.setId(id);
        subtask.setStatus(NEW_STATUS);
        subtask.setEpicId(epicID);
        subTasks.put(subtask.getId(), subtask);
        Epic epicFromEpicTasksMap = epicTasks.get(epicID);
        epicFromEpicTasksMap.putSubtask(subtask);
    }

    public void updateTask(Task task) {
        simpleTasks.put(task.getId(), task);
    }

    public void updateSubTask(Subtask subtask) {
        updateEpicStatus(subtask);
        subTasks.put(subtask.getId(), subtask);
    }

    public void deleteTask(Task task) {
        simpleTasks.remove(task.getId(), task);
    }

    public void deleteSubTask(Subtask subtask) {
        subTasks.remove(subtask.getId(), subtask);
    }

    public void deleteEpicTask(Epic epic) {
        epicTasks.remove(epic.getId(), epic);
        for (Subtask sub : subTasks.values()) {
            if (epic.getId() == sub.getEpicId()) {
                subTasks.remove(sub.getId(), sub);
            }
        }
    }

    public void clearTask() {
        simpleTasks.clear();
    }

    public void clearEpicTask(Epic epic) {
        epicTasks.clear();
        subTasks.clear();
    }

    public Task getTask(int id) {
        return simpleTasks.get(id);
    }

    public Epic getEpicTask(int id) {
        return epicTasks.get(id);
    }

    public Subtask getSubTask(int id) {
        return subTasks.get(id);
    }

    public ArrayList<Task> getAllTask() {
        ArrayList<Task> tasklist = new ArrayList<>(simpleTasks.values());
        return tasklist;
    }

    public ArrayList<Task> getAllEpics() {
        ArrayList<Task> epiclist = new ArrayList<>(epicTasks.values());
        return epiclist;
    }

    public ArrayList<Task> getAllSubtasks() {
        ArrayList<Task> subtasklist = new ArrayList<>(subTasks.values());
        return subtasklist;
    }

    private void updateEpicStatus(Subtask subtask) {
        Epic epic = epicTasks.get(subtask.getEpicId());
        int allDone = 0;

        String statusInProgress = IN_PROGRESS_STATUS;
        String statusDone = DONE_STATUS;
        HashMap<Integer, Subtask> subTaskMap = epic.getSubTaskMap();
        for (Subtask sub : subTaskMap.values()) {
            String epicStatus = sub.getStatus();
            if (statusInProgress.equals(epicStatus)) {
                epic.setStatus(IN_PROGRESS_STATUS);
                break;
            }
            if ((epic.getSubTaskMap() != null) && statusDone.equals(epicStatus)) {
                allDone++;
            }
        }
        if (allDone == subTaskMap.size()) {
            epic.setStatus(DONE_STATUS);
        } else if (allDone > 0 && allDone != subTaskMap.size()) {
            epic.setStatus(IN_PROGRESS_STATUS);
        }
        epicTasks.put(epic.getId(), epic);
    }
}
