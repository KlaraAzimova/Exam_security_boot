package peaksoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Task;
import peaksoft.service.TaskService;

@Controller
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/all/{lessonId}")
    private String getAllTasks(@PathVariable("lessonId") Long lessonId, Model model) {
        model.addAttribute("allTasks", taskService.getAllTaskLessonId(lessonId));
        model.addAttribute("lessonId", lessonId);
        return "task/mainTask";
    }

    @GetMapping("{lessonId}/new")
    private String newTask(@PathVariable("lessonId") Long id, Model model) {
        model.addAttribute("newTask", new Task());
        model.addAttribute("lessonId", id);
        return "task/newTask";
    }

    @PostMapping("{lessonId}/save")
    private String saveTask(@PathVariable("lessonId") Long id, @ModelAttribute("newTask") Task task) {
        taskService.addTask(id, task);
        return "redirect:/tasks/all/ " + id;
    }

    @GetMapping("/find/{taskId}")
    private String getLessonById(@PathVariable("taskId") Long id, Model model) {
        model.addAttribute("task", taskService.getById(id));
        return "task/mainTask";
    }

    @GetMapping("/update/{taskId}")
    private String updateTask(@PathVariable("taskId") Long taskId, Model model) {
        Task task = taskService.getById(taskId);
        model.addAttribute("task", task);
        model.addAttribute("lessonId", task.getLessons().getLessonId());
        return "task/updateTask";
    }

    @PostMapping("/{lessonId}/{taskId}/update")
    private String saveUpdateTask(@PathVariable("lessonId") Long lessonId,
                                  @PathVariable("taskId") Long taskId,
                                  @ModelAttribute("task") Task task) {
        taskService.updateTask(taskId, task);
        return "redirect:/tasks/all/ " + lessonId;

    }

    @RequestMapping("/{lessonId}/{taskId}/delete")
    private String deleteTask(@PathVariable("lessonId") Long id, @PathVariable("taskId") Long taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/tasks/all/ " + id;
    }
}
