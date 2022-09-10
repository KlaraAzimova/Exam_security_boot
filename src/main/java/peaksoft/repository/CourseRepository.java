package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  List<Course> findCoursesByCompanyCompanyId(Long company_id);

  @Query("select c from Course c where (select i from Instructor i where i.instructorId = :id) member of c.instructors")
  List<Course> findCoursesByInstructorId(Long id);
}