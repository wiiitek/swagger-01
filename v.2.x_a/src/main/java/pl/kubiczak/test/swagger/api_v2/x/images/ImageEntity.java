package pl.kubiczak.test.swagger.api_v2.x.images;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import pl.kubiczak.test.swagger.api_v2.x.tasks.TaskEntity;

@Entity
@Table(name = "image")
public class ImageEntity {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
    name = "UUID",
    strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(updatable = false, nullable = false)
  private UUID id;

  @Column
  private String description;

  @Column
  private String url;

  @ManyToMany(fetch = FetchType.LAZY,
    cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE})
  @JoinTable(name = "task_image",
    joinColumns = {@JoinColumn(name = "image_id")},
    inverseJoinColumns = {@JoinColumn(name = "task_id")})
  private Set<TaskEntity> tasks = new HashSet<>();

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Set<TaskEntity> getTasks() {
    return tasks;
  }

  public void setTasks(Set<TaskEntity> tasks) {
    this.tasks = tasks;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImageEntity that = (ImageEntity) o;
    return Objects.equals(id, that.id) &&
      Objects.equals(description, that.description) &&
      Objects.equals(url, that.url) &&
      Objects.equals(tasks, that.tasks);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, url, tasks);
  }

  @Override
  public String toString() {
    return "ImageEntity{" +
      "id=" + id +
      ", description='" + description + '\'' +
      ", url='" + url + '\'' +
      ", tasks=" + tasks +
      '}';
  }
}
