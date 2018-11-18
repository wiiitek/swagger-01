package pl.kubiczak.test.swagger.api_v2.x.images.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import pl.kubiczak.test.swagger.api_v2.x.images.entities.ImageEntity;

public interface ImageRepository extends CrudRepository<ImageEntity, UUID> {
}
