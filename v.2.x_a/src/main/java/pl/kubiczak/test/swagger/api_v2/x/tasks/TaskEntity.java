package pl.kubiczak.test.swagger.api_v2.x.tasks;

import java.time.OffsetDateTime;
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
import pl.kubiczak.test.swagger.api_v2.x.images.ImageEntity;

@Entity
@Table(name = "task")
public class TaskEntity {

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
  private OffsetDateTime createdAt;

  @ManyToMany(fetch = FetchType.LAZY,
    cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE})
  @JoinTable(name = "task_image",
    joinColumns = {@JoinColumn(name = "task_id")},
    inverseJoinColumns = {@JoinColumn(name = "image_id")})
  private Set<ImageEntity> images = new HashSet<>();

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

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Set<ImageEntity> getImages() {
    return images;
  }

  public void setImages(Set<ImageEntity> images) {
    this.images = images;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaskEntity that = (TaskEntity) o;
    return Objects.equals(id, that.id) &&
      Objects.equals(description, that.description) &&
      Objects.equals(createdAt, that.createdAt) &&
      Objects.equals(images, that.images);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, createdAt, images);
  }

  @Override
  public String toString() {
    return "TaskEntity{" +
      "id=" + id +
      ", description='" + description + '\'' +
      ", createdAt=" + createdAt +
      ", images=" + images +
      '}';
  }
}
