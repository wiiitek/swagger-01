package pl.kubiczak.test.swagger.v2.java.spring.tasks;

import com.google.common.base.Preconditions;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import pl.kubiczak.test.swagger.v2.java.spring.generated.model.ImageOutput;
import pl.kubiczak.test.swagger.v2.java.spring.generated.model.TaskInput;
import pl.kubiczak.test.swagger.v2.java.spring.generated.model.TaskOutput;
import pl.kubiczak.test.swagger.v2.java.spring.generated.tasks.TasksApi ;
import pl.kubiczak.test.swagger.v2.java.spring.images.ImageEntity;
import pl.kubiczak.test.swagger.v2.java.spring.images.ImageRepository;

import static java.time.ZoneOffset.UTC;


@RestController
@Transactional
public class TasksApiController implements TasksApi {

  private final TaskRepository taskRepository;

  private final ImageRepository imageRepository;

  @Autowired
  public TasksApiController(TaskRepository taskRepository, ImageRepository imageRepository) {
    this.taskRepository
      = Preconditions.checkNotNull(taskRepository, "Tasks repository is missing");
    this.imageRepository
      = Preconditions.checkNotNull(imageRepository, "Images repository is missing");
  }

  @Override
  public ResponseEntity<TaskOutput> createTask(@Valid @RequestBody TaskInput body) {
    TaskEntity toCreate = new TaskEntity();
    toCreate.setDescription(body.getDescription());
    toCreate.setCreatedAt(OffsetDateTime.now(UTC));
    toCreate.setImages(imageEntities(body));

    TaskEntity saved = taskRepository.save(toCreate);
    TaskOutput output = convert(saved);
    return ResponseEntity.ok(output);
  }

  @Override
  public ResponseEntity<List<TaskOutput>> readTasks() {
    Spliterator<TaskEntity> spliterator = taskRepository.findAll().spliterator();
    List<TaskOutput> outputList = StreamSupport.stream(spliterator, false)
      .map(this::convert)
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
        toUpdate.setImages(imageEntities(body));
        return toUpdate;
      })
      .map(taskRepository::save)
      .map(this::convert)
      .map(ResponseEntity::ok)
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

  private Set<ImageEntity> imageEntities(TaskInput body) {
    if (body.getImages() != null) {
      Set<ImageEntity> images = new HashSet<>();
      body.getImages()
        .forEach((uuid) -> {
          Optional<ImageEntity> optional = imageRepository.findById(uuid);
          optional.ifPresent(images::add);
        });
      return images;
    } else {
      return Collections.emptySet();
    }
  }

  private TaskOutput convert(TaskEntity entity) {
    return new TaskOutput()
      .description(entity.getDescription())
      .id(entity.getId())
      .createdAt(entity.getCreatedAt())
      .images(imagesOutput(entity));
  }

  private List<ImageOutput> imagesOutput(TaskEntity entity) {
    return entity.getImages().stream()
      .map(imageEntity -> new ImageOutput()
        .id(imageEntity.getId())
        .url(imageEntity.getUrl())
        .description(imageEntity.getDescription())
      )
      .collect(Collectors.toList());
  }
}
