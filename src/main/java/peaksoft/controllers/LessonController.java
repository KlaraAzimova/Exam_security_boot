package peaksoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Lesson;
import peaksoft.service.LessonService;

@Controller
@RequestMapping("lessons")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/all/{courseId}")
    private String getAllLessons(@PathVariable("courseId") Long courseId, Model model) {
        model.addAttribute("allLessons", lessonService.getAllLesson());
        model.addAttribute("courseId", courseId);
        return "lesson/mainLesson";
    }

    @GetMapping("{courseId}/new")
    private String newLesson(@PathVariable("courseId") Long id, Model model) {
        model.addAttribute("newLesson", new Lesson());
        model.addAttribute("courseId", id);
        return "lesson/newLesson";
    }

    @PostMapping("{courseId}/save")
    private String saveLesson(@PathVariable("courseId") Long id, @ModelAttribute("newLesson") Lesson lesson) {
        lessonService.addLesson(id, lesson);
        return "redirect:/lessons/all/ " + id;
    }

    @GetMapping("/find/{lessonId}")
    private String getLessonById(@PathVariable("lessonId") Long id, Model model) {
        model.addAttribute("lesson", lessonService.getById(id));
        return "lesson/mainLesson";
    }

    @GetMapping("/update/{lessonId}")
    private String updateLesson(@PathVariable("lessonId") Long lessonId, Model model) {
        Lesson lesson = lessonService.getById(lessonId);
        model.addAttribute("lesson", lesson);
        model.addAttribute("courseId", lesson.getCourses().getCourseId());
        return "lesson/updateLesson";
    }

    @PostMapping("/{courseId}/{lessonId}/update")
    private String saveUpdateLesson(@PathVariable("courseId") Long courseId,
                                    @PathVariable("lessonId") Long lessonId,
                                    @ModelAttribute("lesson") Lesson lesson) {
        lessonService.updateLesson(lessonId, lesson);
        return "redirect:/lessons/all/ " + courseId;
    }

    @RequestMapping("/{courseId}/{lessonId}/delete")
    private String deleteLesson(@PathVariable("courseId") Long id, @PathVariable("lessonId") Long lessonId) {
        lessonService.deleteLesson(lessonId);
        return "redirect:/lessons/all/ " + id;
    }

}
