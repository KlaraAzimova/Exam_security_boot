package peaksoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.entity.Student;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.StudentRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final CourseRepository courseRepository;
    @Autowired
    public StudentService(StudentRepository studentRepository, CompanyRepository companyRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    public void addStudents(Long id, Student student) {
        Company company = companyRepository.findById(id).get();
        company.addStudent(student);
        student.setCompany(company);
        studentRepository.save(student);

    }

    public Student getById(Long id) {
        return studentRepository.findById(id).get();
    }

    public List<Student> getStudentByCourseId(Long id) {
        return studentRepository.findStudentsByCourse_CourseId(id);
    }

    public List<Student> getStudentByCompanyId(Long id) {
        return studentRepository.findStudentsByCompany_CompanyId(id);
    }

    public void updateStudent(Long id, Student student) {
        Student student1 = studentRepository.findById(id).get();
        student1.setStudentId(student.getStudentId());
        student1.setFirstName(student.getFirstName());
        student1.setLastName(student.getLastName());
        student1.setPhoneNumber(student.getPhoneNumber());
        student1.setEmail(student.getEmail());
        student1.setStudyFormat(student.getStudyFormat());
        studentRepository.save(student1);

    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).get();
        student.setCourse(null);
        studentRepository.deleteById(id);
    }

    public void assignStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();
        student.setCourse(course);
        course.addStudents(student);
        studentRepository.save(student);
        courseRepository.save(course);
    }
}
