package peaksoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Instructor;
import peaksoft.service.CourseService;
import peaksoft.service.InstructorService;

@Controller
@RequestMapping("/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    private final CourseService courseService;

    @Autowired
    public InstructorController(InstructorService instructorService, CourseService courseService) {
        this.instructorService = instructorService;
        this.courseService = courseService;
    }

    @GetMapping("/all/{companyId}")
    private String getAllInstructors(@PathVariable("companyId") Long id, Model model) {
        model.addAttribute("allInstructors", instructorService.getInstructorByCompanyId(id));
        model.addAttribute("companyId", id);
        model.addAttribute("instructors", courseService.getCourseByCompanyId(id));
        return "instructor/mainInstructor";
    }


    @GetMapping("{companyId}/new")
    private String newInstructor(@PathVariable("companyId") Long id, Model model) {
        model.addAttribute("newInstructor", new Instructor());
        model.addAttribute("companyId", id);
        return "instructor/newInstructor";
    }

    @PostMapping("{companyId}/save")
    private String saveInstructor(@PathVariable("companyId") Long id,
                                  @ModelAttribute("newInstructor") Instructor instructor) {
        instructorService.addInstructor(id, instructor);
        return "redirect:/instructors/all/ " + id;
    }

    @GetMapping("/find/{id}")
    private String getInstructorById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", instructorService.getById(id));
        return "instructor/mainInstructor";
    }

    @GetMapping("/update/{instructorId}")
    private String updateInstructor(@PathVariable("instructorId") Long instructorId, Model model) {
        Instructor instructor = instructorService.getById(instructorId);
        model.addAttribute("instructor", instructor);
        model.addAttribute("companyId", instructor.getCompany().getCompanyId());
        return "instructor/updateInstructor";
    }

    @PostMapping("/{companyId}/{instructorId}/update")
    private String saveUpdate(@PathVariable("companyId") Long companyId,
                              @PathVariable("instructorId") Long id,
                              @ModelAttribute("instructor") Instructor instructor) {
        instructorService.updateInstructor(id, instructor);
        return "redirect:/instructors/all/" + companyId;
    }

    @RequestMapping("/{id}/{instructorId}/delete")
    private String deleteInstructor(@PathVariable("id") Long id, @PathVariable("instructorId") Long instructorId) {
        instructorService.deleteInstructor(instructorId);
        return "redirect:/instructors/all/ " + id;
    }

}
