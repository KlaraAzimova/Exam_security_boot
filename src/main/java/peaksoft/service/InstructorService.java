package peaksoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.entity.Company;
import peaksoft.entity.Course;
import peaksoft.entity.Instructor;
import peaksoft.repository.CompanyRepository;
import peaksoft.repository.CourseRepository;
import peaksoft.repository.InstructorRepository;

import java.util.List;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository, CourseRepository courseRepository, CompanyRepository companyRepository) {
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.companyRepository = companyRepository;
    }

    public void addInstructor(Long id, Instructor instructor) {
        Company company = companyRepository.findById(id).get();
        company.addInstructor(instructor);
        instructor.setCompany(company);
        instructorRepository.save(instructor);
    }

    public Instructor getById(Long id) {
        return instructorRepository.findById(id).get();
    }

    public List<Instructor> getInstructorByCompanyId(Long id) {
        return instructorRepository.findInstructorsByCompany_CompanyId(id);
    }

    public void updateInstructor(Long companyId, Instructor instructor) {
        addInstructor(companyId, instructor);
    }

    public void deleteInstructor(Long id) {
        instructorRepository.deleteById(id);
    }


    public void assignInstructorToCourse(Long instrId, Long courseId) {
        Instructor instructor = instructorRepository.findById(instrId).get();
        Course course = courseRepository.findById(courseId).get();
        instructor.addCourse(course);
        course.addInstructors(instructor);
        instructorRepository.save(instructor);
    }

    public List<Course> findCoursesByInstructorId(Long instructorId) {
       return courseRepository.findCoursesByInstructorId(instructorId);
    }
}
