package pl.kubiczak.test.swagger.java.spring.v2.tasks;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;


public interface TaskRepository extends CrudRepository<TaskEntity, UUID> {
}
