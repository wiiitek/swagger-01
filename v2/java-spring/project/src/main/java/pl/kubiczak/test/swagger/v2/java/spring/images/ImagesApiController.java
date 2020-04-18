package pl.kubiczak.test.swagger.v2.java.spring.images;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kubiczak.test.swagger.v2.java.spring.generated.images.ImagesApi;
import pl.kubiczak.test.swagger.v2.java.spring.generated.model.ImageInput;
import pl.kubiczak.test.swagger.v2.java.spring.generated.model.ImageOutput;


@RestController
@Transactional
public class ImagesApiController implements ImagesApi {

  private final ImageRepository imageRepository;

  @Autowired
  public ImagesApiController(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @Override
  public ResponseEntity<ImageOutput> createImage(@Valid @RequestBody ImageInput body) {
    ImageEntity toCreate = new ImageEntity();
    toCreate.setUrl(body.getUrl());
    toCreate.setDescription(body.getDescription());

    ImageEntity saved = imageRepository.save(toCreate);
    ImageOutput output = new ImageOutput()
      .description(saved.getDescription())
      .id(saved.getId())
      .url(saved.getUrl());
    return ResponseEntity.ok(output);
  }

  @Override
  public ResponseEntity<List<ImageOutput>> readImages() {
    Spliterator<ImageEntity> spliterator = imageRepository.findAll().spliterator();
    List<ImageOutput> outputList = StreamSupport.stream(spliterator, false)
      .map(entity -> new ImageOutput()
        .description(entity.getDescription())
        .id(entity.getId())
        .url(entity.getUrl())
      )
      .collect(Collectors.toList());
    return ResponseEntity.ok(outputList);
  }

}
