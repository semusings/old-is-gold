package pathsala.backend.student.data;

import org.springframework.data.repository.CrudRepository;

interface DataLayerStudentRepository extends CrudRepository<StudentEntity, String> {

}