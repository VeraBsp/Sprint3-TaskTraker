
public class TaskTracker {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("забрать заказ", "заказ №125",manager.generateId());
        Task task2 = new Task("сложить вещи в шкаф", "вещи должны лежать на своих полках", manager.generateId());
        Epic epic1 = new Epic("купить дом", "дом должен быть удобный и красивый", manager.generateId());
        Subtask subtask1 = new Subtask("посмотреть объявления о продаже", "объявления 21212", manager.generateId(), epic1.getId());
        Subtask subtask2 = new Subtask("выбрать расположение", "расположение в деревне", manager.generateId(), epic1.getId());
        Epic epic2 = new Epic("ораганизовать праздник", "день рождения", manager.generateId());
        Subtask subtask3 = new Subtask("заказать торт", "торт большой, вкусный", manager.generateId(), epic2.getId());

        manager.createTask(task1);
        manager.createTask(task2);
        manager.createEpicTask(epic1);
        manager.createSubTask(subtask1);
        manager.createSubTask(subtask2);
        epic1.putSubtask(subtask1);
        epic1.putSubtask(subtask2);
        manager.createEpicTask(epic2);
        manager.createSubTask(subtask3);
        epic2.putSubtask(subtask3);

        System.out.println("task1= " + task1);
        System.out.println("task2= " + task2);
        System.out.println("epic1= " + epic1);
        System.out.println("subtask1= " + subtask1);
        System.out.println("subtask2= " + subtask2);
        System.out.println("epic2= " + epic2);
        System.out.println("subtask3= " + subtask3);
        System.out.println();

        task1.setStatus("inProgress"); // меняем статус задачи на inProgress
        manager.updateTask(task1);
        System.out.println(manager.getTask(task1.getId()));
        task2.setStatus("done");
        manager.updateTask(task2);
        System.out.println(manager.getTask(task2.getId()));
        subtask1.setStatus("done");
        manager.updateSubTask(subtask1);
        System.out.println(manager.getSubTask(subtask1.getId()));
        subtask2.setStatus("done");
        manager.updateSubTask(subtask2);
        System.out.println(manager.getSubTask(subtask2.getId()));
        subtask3.setStatus("inProgress");
        manager.updateSubTask(subtask3);
        System.out.println(manager.getSubTask(subtask3.getId()));
        System.out.println(manager.getEpicTask(epic1.getId()));
        System.out.println(manager.getEpicTask(epic2.getId()));
        System.out.println();

        System.out.println("Получение списка всех задач: ");
        System.out.println(manager.getAllTask());
        System.out.println();

        System.out.println("Получение списка всех эпиков: ");
        System.out.println(manager.getAllEpics());
        System.out.println();

        System.out.println("Получение списка всех подзадач: ");
        System.out.println(manager.getAllSubtasks());
        System.out.println();

        System.out.println("Удаление всех задач: ");
        manager.clearTask();
        System.out.println(manager.getAllTask());
        System.out.println();

        System.out.println("Удаление задачи task1: ");
        manager.deleteTask(task1);
        System.out.println(manager.getAllTask());
        System.out.println();

        System.out.println("Удаление подзадачи subtask2: ");
        manager.deleteSubTask(subtask2);
        System.out.println(manager.getAllSubtasks()); // вывод списка всех подзадач, без удаленной подзадачи
        System.out.println();

        System.out.println("Удаление эпика:");
        manager.deleteEpicTask(epic2);
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks()); // вывод списка подзадач удаленного эпика - должны удалиться все подзадачи

        System.out.println("Удаление всех эпиков: ");
        manager.clearEpicTask(epic1);
        System.out.println(manager.getAllEpics());
        System.out.println();
        System.out.println("Получение списка всех подзадач (подзадачи удаляются после удаления эпиков): ");
        System.out.println(manager.getAllSubtasks());

    }
}
