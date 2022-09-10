package peaksoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.entity.Student;
import peaksoft.service.CompanyService;
import peaksoft.service.CourseService;
import peaksoft.service.StudentService;

@Controller
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final CompanyService companyService;

    @Autowired
    public StudentController(StudentService studentService, CourseService courseService, CompanyService companyService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.companyService = companyService;
    }

    @GetMapping("/{companyId}")
    private String getAllStudents(@PathVariable Long companyId, Model model,
                                  @ModelAttribute("course") Course course) {
        model.addAttribute("allStudents", studentService.getStudentByCompanyId(companyId));
        model.addAttribute("companyId", companyId);
        model.addAttribute("courses", courseService.getCourseByCompanyId(companyId));
        return "student/mainStudent";
    }

    @GetMapping("/new/{companyId}")
    private String newStudent(@PathVariable Long companyId, Model model) {
        model.addAttribute("newStudent", new Student());
        model.addAttribute("companyId", companyId);
        return "student/newStudent";
    }

    @PostMapping("/save/{companyId}")
    private String saveStudent(@PathVariable Long companyId,
                               @ModelAttribute("newStudent") Student student) {
        studentService.addStudents(companyId, student);
        return "redirect:/api/students/ " + companyId;
    }

    @GetMapping("/find/{id}")
    private String getStudentById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return "student/mainStudent";
    }

    @GetMapping("/update/{studentId}")
    private String updateStudent(@PathVariable Long studentId, Model model) {
        Student student = studentService.getById(studentId);
        model.addAttribute("student", student);
        model.addAttribute("companyId", student.getCompany().getCompanyId());
        return "student/updateStudent";
    }

    @PostMapping("/update/{companyId}")
    public String saveUpdateStudent(@PathVariable Long companyId,
                                    @ModelAttribute("student") Student student) {
        studentService.updateStudent(companyId, student);
        return "redirect:/api/students/" + companyId;
    }

    @RequestMapping("/{companyId}/delete/{studentId}")
    private String deleteStudent(@PathVariable Long studentId,
                                 @PathVariable Long companyId) {
        studentService.deleteStudent(studentId);
        return "redirect:/api/students/" + companyId;
    }

    @PostMapping("/{companyId}/{studentId}/assign")
    private String assign(@PathVariable("studentId") Long id,
                          @PathVariable("companyId") Long companyId,
                          @ModelAttribute("course") Course course) {
        studentService.assignStudentToCourse(id, course.getCourseId());
        return "redirect:/api/students/" + companyId;
    }
}
