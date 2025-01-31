package ro.bogdannegoita.myplannerkt.api.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ro.bogdannegoita.myplannerkt.domain.MyPlanner
import java.util.*

@RestController
@RequestMapping("/images")
class ImageController(myPlanner: MyPlanner) : BaseController(myPlanner) {

    @GetMapping("/{id}")
    fun getImage(@PathVariable id: UUID): ResponseEntity<ByteArray> {
        val photo = myPlanner.getImage(id)
        return ResponseEntity.ok()
            .header("Content-Type", photo.contentType)
            .header("Content-Disposition", "attachment; filename=${photo.name}")
            .body(photo.content)
    }
}
