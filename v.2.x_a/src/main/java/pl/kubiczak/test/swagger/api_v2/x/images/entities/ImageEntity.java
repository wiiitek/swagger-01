package pl.kubiczak.test.swagger.api_v2.x.images.entities;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
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
      Objects.equals(url, that.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description, url);
  }

  @Override
  public String toString() {
    return "ImageEntity{" +
      "id=" + id +
      ", description='" + description + '\'' +
      ", url='" + url + '\'' +
      '}';
  }
}
