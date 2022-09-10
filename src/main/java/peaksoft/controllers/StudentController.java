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
@RequestMapping("/students")
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

    @GetMapping("/all/{companyId}")
    private String getAllStudents(@PathVariable("companyId") Long companyId, Model model, @ModelAttribute("course") Course course) {
        model.addAttribute("allStudents", studentService.getAllStudents());
        model.addAttribute("companyId", companyId);
        Company company = companyService.getById(companyId);
        model.addAttribute("courses", courseService.getCourseByCompanyId(companyId));
        return "student/mainStudent";
    }

    @GetMapping("/{companyId}/new")
    private String newStudent(@PathVariable("companyId") Long id, Model model) {
        model.addAttribute("newStudent", new Student());
        model.addAttribute("companyId", id);
        return "student/newStudent";
    }

    @PostMapping("{companyId}/save")
    private String saveStudent(@PathVariable("companyId") Long companyId, @ModelAttribute("newStudent") Student student) {
        studentService.addStudents(companyId, student);
        return "redirect:/students/allStudents/ " + companyId;
    }

    @GetMapping("/find/{id}")
    private String getStudentById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", studentService.getById(id));
        return "student/mainStudent";
    }

    @GetMapping("/update/{studentId}")
    private String updateStudent(@PathVariable("studentId") Long id, Model model) {
        Student student = studentService.getById(id);
        model.addAttribute("student", student);
        model.addAttribute("companyId", student.getCompany().getCompanyId());
        return "student/updateStudent";
    }

    @PostMapping("/{companyId}/{studentId}/update")
    public String saveUpdateStudent(@PathVariable("companyId") Long id,
                                    @PathVariable("studentId") Long studentId,
                                    @ModelAttribute("student") Student student) {
        studentService.updateStudent(studentId, student);
        return "redirect:/students/all/ " + id;
    }

    @RequestMapping("/{studentId}/{companyId}/delete")
    private String deleteStudent(@PathVariable("studentId") Long id, @PathVariable("companyId") Long companyId) {
        studentService.deleteStudent(id);
        return "redirect:/students/all/ " + companyId;
    }

    @PostMapping("/{companyId}/{studentId}/assign")
    private String assign(@PathVariable("studentId") Long id,
                          @PathVariable("companyId") Long companyId,
                          @ModelAttribute("course") Course course) {
        studentService.assignStudentToCourse(id, course.getCourseId());
        return "redirect:/students/all/ " + companyId;
    }

}
