package peaksoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.entity.Lesson;
import peaksoft.entity.Task;
import peaksoft.repository.LessonRepository;
import peaksoft.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, LessonRepository lessonRepository) {
        this.taskRepository = taskRepository;
        this.lessonRepository = lessonRepository;
    }

    public List<Task> getAllTaskLessonId(Long id) {
        return taskRepository.findTasksByLessons_LessonId(id);
    }


    public void addTask(Long id, Task task) {
        Lesson lesson = lessonRepository.findById(id).get();
        lesson.addTasks(task);
        task.setLessons(lesson);
        taskRepository.save(task);
    }


    public Task getById(Long id) {
        return taskRepository.findById(id).get();
    }

    public void updateTask(Long id, Task task) {
        Task task1 = taskRepository.findById(id).get();
        task1.setTaskId(task.getTaskId());
        task1.setTaskName(task.getTaskName());
        task1.setDeadline(task.getDeadline());
        task1.setTaskText(task.getTaskText());
        taskRepository.save(task1);
    }

    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).get();
        task.setLessons(null);
        taskRepository.save(task);
        taskRepository.delete(task);
    }
}
