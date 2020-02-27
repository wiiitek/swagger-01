package pl.kubiczak.test.swagger.v2.java.spring.images;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<ImageEntity, UUID> {
}
