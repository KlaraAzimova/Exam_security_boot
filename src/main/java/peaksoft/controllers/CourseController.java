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
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final CompanyService companyService;
    private final InstructorService instructorService;


    @Autowired
    public CourseController(CourseService courseService, CompanyService companyService,
                            InstructorService instructorService) {
        this.courseService = courseService;
        this.companyService = companyService;
        this.instructorService = instructorService;
    }

    @GetMapping("/all/{companyId}")
    private String getAllCourses(@PathVariable("companyId") Long companyId, Model model, @ModelAttribute("inst") Instructor instructor) {
        model.addAttribute("allCourses", courseService.getCourseByCompanyId(companyId));
        model.addAttribute("companyId", companyId);
        Company company = companyService.getById(companyId);
        List<Instructor> instructors = company.getInstructors();
        model.addAttribute("instructors", instructorService.getInstructorByCompanyId(companyId));
        return "/course/mainCourse";
    }

    @GetMapping("{companyId}/new")
    private String newCourse(@PathVariable("companyId") Long id, Model model) {
        model.addAttribute("newCourse", new Course());
        model.addAttribute("companyId", id);
        return "course/newCourse";
    }

    @PostMapping("/{companyId}/save")
    private String saveCourse(@PathVariable("companyId") Long companyId, @ModelAttribute("newCourse") Course course) {
        courseService.addCourse(companyId, course);
        return "redirect:/courses/allCourses/ " + companyId;
    }

    @GetMapping("/find/{courseId}")
    private String getCourseById(@PathVariable("courseId") Long courseId, Model model) {
        model.addAttribute("course", courseService.getById(courseId));
        return "/course/mainCourse";
    }

    @GetMapping("/update/{courseId}")
    public String updateCourse(@PathVariable("courseId") Long courseId, Model model) {
        Course course = courseService.getById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("compId", course.getCompany().getCompanyId());
        return "course/updateCourse";
    }

    @PostMapping("/{companyId}/{courseId}/update")
    public String saveUpdateCourse(@PathVariable("companyId") Long companyId,
                                   @PathVariable("courseId") Long courseId,
                                   @ModelAttribute("course") Course course) {
        courseService.updateCourse(courseId, course);
        return "redirect:/courses/allCourses/ " + companyId;
    }


    @RequestMapping("/{id}/{courseId}/delete")
    private String deleteCourse(@PathVariable("id") Long id,
                                @PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
        return "redirect:/courses/allCourses/ " + courseId;
    }

    @PostMapping("/{companyId}/{courseId}/saveAssign")
    private String saveAssign(@PathVariable("courseId") Long courseId,
                              @ModelAttribute("inst") Instructor instructor,
                              @PathVariable("companyId") Long compId) {
        System.out.println(instructor);
        instructorService.assignInstructorToCourse(instructor.getInstructorId(), courseId);
        return "redirect:/courses/allCourses/" + compId;
    }
}

