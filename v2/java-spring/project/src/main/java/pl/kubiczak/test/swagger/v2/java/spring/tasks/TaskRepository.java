package pl.kubiczak.test.swagger.v2.java.spring.tasks;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;


public interface TaskRepository extends CrudRepository<TaskEntity, UUID> {
}
