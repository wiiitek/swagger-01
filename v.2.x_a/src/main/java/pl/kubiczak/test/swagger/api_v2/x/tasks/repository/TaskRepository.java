package pl.kubiczak.test.swagger.api_v2.x.tasks.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import pl.kubiczak.test.swagger.api_v2.x.tasks.entities.TaskEntity;

public interface TaskRepository extends CrudRepository<TaskEntity, UUID> {
}
