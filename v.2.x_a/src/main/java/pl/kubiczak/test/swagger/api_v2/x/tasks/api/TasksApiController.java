package pl.kubiczak.test.swagger.api_v2.x.tasks.api;

import com.google.common.base.Preconditions;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kubiczak.test.swagger.api_v2.x.tasks.entities.TaskEntity;
import pl.kubiczak.test.swagger.api_v2.x.tasks.repository.TaskRepository;
import pl.kubiczak.test.swagger.v2.x.a.generated.tasks.api.TasksApi;
import pl.kubiczak.test.swagger.v2.x.a.generated.tasks.model.TaskInput;
import pl.kubiczak.test.swagger.v2.x.a.generated.tasks.model.TaskOutput;

@RestController
@Transactional
public class TasksApiController implements TasksApi {

  private final TaskRepository taskRepository;

  @Autowired
  public TasksApiController(TaskRepository taskRepository) {
    this.taskRepository
      = Preconditions.checkNotNull(taskRepository, "Tasks repository is missing");
  }

  @Override
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

  @Override
  public ResponseEntity<List<TaskOutput>> readTasks() {
    Spliterator<TaskEntity> spliterator = taskRepository.findAll().spliterator();
    List<TaskOutput> outputList = StreamSupport.stream(spliterator, false)
      .map(entity -> new TaskOutput()
        .description(entity.getDescription())
        .id(entity.getId())
        .createdAt(entity.getCreatedAt())
      )
      .collect(Collectors.toList());
    return ResponseEntity.ok(outputList);
  }

  @Override
  public ResponseEntity<TaskOutput> updateTask(
    @PathVariable("uuid") UUID uuid,
    @Valid @RequestBody TaskInput body) {
    Optional<TaskEntity> optional = taskRepository.findById(uuid);
    return optional
      .map((toUpdate) -> {
        toUpdate.setDescription(body.getDescription());
        return toUpdate;
      })
      .map(taskRepository::save)
      .map(saved -> new TaskOutput()
        .description(saved.getDescription())
        .id(saved.getId())
        .createdAt(saved.getCreatedAt())
      )
      .map(savedTaskOutput -> ResponseEntity.ok(savedTaskOutput))
      .orElse(ResponseEntity.notFound().build());
  }

  @Override
  public ResponseEntity<TaskOutput> deleteTask(@PathVariable("uuid") UUID uuid) {
    Optional<TaskEntity> optional = taskRepository.findById(uuid);
    return optional
      .map((toDelete) -> {
        taskRepository.deleteById(uuid);
        return toDelete;
      })
      .map(deletedEntity -> new TaskOutput()
        .description(deletedEntity.getDescription())
        .id(deletedEntity.getId())
        .createdAt(deletedEntity.getCreatedAt())
      )
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }
}
