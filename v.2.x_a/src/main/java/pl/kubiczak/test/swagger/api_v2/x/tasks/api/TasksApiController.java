package pl.kubiczak.test.swagger.api_v2.x.tasks.api;

import com.google.common.base.Preconditions;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import pl.kubiczak.test.swagger.api_v2.x.tasks.entities.TaskEntity;
import pl.kubiczak.test.swagger.api_v2.x.tasks.repository.TaskRepository;
import pl.kubiczak.test.swagger.v2.x.a.generated.tasks.api.TasksApi;
import pl.kubiczak.test.swagger.v2.x.a.generated.tasks.model.TaskInput;
import pl.kubiczak.test.swagger.v2.x.a.generated.tasks.model.TaskOutput;

@Controller
public class TasksApiController implements TasksApi {

  private final TaskRepository taskRepository;

  @Autowired
  public TasksApiController(TaskRepository taskRepository) {
    this.taskRepository
      = Preconditions.checkNotNull(taskRepository, "Tasks repository is missing");
  }

  public ResponseEntity<TaskOutput> createTask(@Valid @RequestBody TaskInput body) {
    TaskEntity toCreate = new TaskEntity();
    toCreate.setDescription(body.getDescription());
    toCreate.setCreatedAt(OffsetDateTime.now());

    TaskEntity saved = taskRepository.save(toCreate);
    TaskOutput output = new TaskOutput()
      .description(saved.getDescription())
      .id(saved.getId())
      .createdAt(saved.getCreatedAt());

    return ResponseEntity.ok(output);
  }
}
