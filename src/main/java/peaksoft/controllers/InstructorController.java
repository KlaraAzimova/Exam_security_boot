package peaksoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Instructor;
import peaksoft.service.CourseService;
import peaksoft.service.InstructorService;

@Controller
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("/{companyId}")
    private String getAllInstructors(@PathVariable Long companyId, Model model) {
        model.addAttribute("allInstructors", instructorService.getInstructorByCompanyId(companyId));
        model.addAttribute("companyId", companyId);
        return "instructor/mainInstructor";
    }

    @GetMapping("/findCoursesByInstructorId/{instructorId}")
    private String getAllCourses(@PathVariable Long instructorId, Model model) {
        Instructor instructor = instructorService.getById(instructorId);
        model.addAttribute("allCourses", instructorService.findCoursesByInstructorId(instructorId));
        model.addAttribute("companyId", instructor.getCompany());
        return "/course/mainCourse";
    }

    @GetMapping("/new/{companyId}")
    private String newInstructor(@PathVariable Long companyId, Model model) {
        model.addAttribute("newInstructor", new Instructor());
        model.addAttribute("companyId", companyId);
        return "instructor/newInstructor";
    }

    @PostMapping("/save/{companyId}")
    private String saveInstructor(@PathVariable Long companyId,
                                  @ModelAttribute("newInstructor") Instructor instructor) {
        instructorService.addInstructor(companyId, instructor);
        return "redirect:/api/instructors/" + companyId;
    }

    @GetMapping("/update/{instructorId}")
    private String updateInstructor(@PathVariable("instructorId") Long instructorId, Model model) {
        Instructor instructor = instructorService.getById(instructorId);
        model.addAttribute("instructor", instructor);
        model.addAttribute("companyId", instructor.getCompany().getCompanyId());
        return "instructor/updateInstructor";
    }

    @PostMapping("/update/{companyId}")
    private String saveUpdate(@PathVariable Long companyId,
                              @ModelAttribute("instructor") Instructor instructor) {
        instructorService.updateInstructor(companyId, instructor);
        return "redirect:/api/instructors/" + companyId;
    }

    @RequestMapping("/{companyId}/delete/{instructorId}")
    private String deleteInstructor(@PathVariable Long companyId,
                                    @PathVariable Long instructorId) {
        instructorService.deleteInstructor(instructorId);
        return "redirect:/api/instructors/" + companyId;
    }

}
