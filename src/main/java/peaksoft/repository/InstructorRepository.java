package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Instructor;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    List<Instructor> findInstructorsByCompany_CompanyId(Long id);

    @Query("select i from Instructor i where (select c from Course c where c.courseId = :id) member of i.courses")
    List<Instructor> findInstructorsByCourseId(Long id);
}