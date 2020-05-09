package pl.kubiczak.test.swagger.v2.kotlin.spring.flux.datetime

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.ZonedDateTime

@RestController
class DateTimeController {

  @RequestMapping(
    value = ["/now"],
    produces = ["application/json"],
    method = [RequestMethod.GET]
  )
  fun now(): Mono<ResponseEntity<NowOutput>> {
    val now = ZonedDateTime.now()
    val output = NowOutput(now)
    val response = ResponseEntity.ok(output)
    return Mono.just(response)
  }
}
