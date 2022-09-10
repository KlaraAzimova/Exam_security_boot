package peaksoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.entity.Instructor;
import peaksoft.service.CompanyService;
import peaksoft.service.CourseService;
import peaksoft.service.InstructorService;

import java.util.List;

@Controller
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final InstructorService instructorService;


    @Autowired
    public CourseController(CourseService courseService, InstructorService instructorService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
    }

    @GetMapping("/{companyId}")
    private String getAllCourses(@PathVariable("companyId") Long companyId, Model model, @ModelAttribute("inst") Instructor instructor) {
        model.addAttribute("allCourses", courseService.getCourseByCompanyId(companyId));
        model.addAttribute("companyId", companyId);
        return "/course/mainCourse";
    }

    @GetMapping("/new/{companyId}")
    private String newCourse(@PathVariable Long companyId, Model model) {
        model.addAttribute("newCourse", new Course());
        model.addAttribute("companyId", companyId);
        return "course/newCourse";
    }

    @PostMapping("/save/{companyId}")
    private String saveCourse(@PathVariable Long companyId,
                              @ModelAttribute("newCourse") Course course) {
        courseService.addCourse(companyId, course);
        return "redirect:/api/courses/" + companyId;
    }

    @GetMapping("/update/{courseId}")
    public String updateCourse(@PathVariable("courseId") Long courseId, Model model) {
        Course course = courseService.getById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("compId", course.getCompany().getCompanyId());
        return "course/updateCourse";
    }

    @PostMapping("/update/{companyId}")
    public String saveUpdateCourse(@PathVariable("companyId") Long companyId,
                                   @ModelAttribute("course") Course course) {
        courseService.updateCourse(companyId, course);
        return "redirect:/api/courses/" + companyId;
    }


    @RequestMapping("/{companyId}/delete/{courseId}")
    private String deleteCourse(@PathVariable Long companyId,
                                @PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return "redirect:/api/courses/" + companyId;
    }

    @PostMapping("/{companyId}/{courseId}/saveAssign")
    private String saveAssign(@PathVariable("courseId") Long courseId,
                              @ModelAttribute("inst") Instructor instructor,
                              @PathVariable("companyId") Long compId) {
        System.out.println(instructor);
        instructorService.assignInstructorToCourse(instructor.getInstructorId(), courseId);
        return "redirect:/api/courses/" + compId;
    }
}

