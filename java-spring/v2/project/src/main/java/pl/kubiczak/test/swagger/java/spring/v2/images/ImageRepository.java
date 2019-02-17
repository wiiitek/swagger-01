package pl.kubiczak.test.swagger.java.spring.v2.images;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<ImageEntity, UUID> {
}
