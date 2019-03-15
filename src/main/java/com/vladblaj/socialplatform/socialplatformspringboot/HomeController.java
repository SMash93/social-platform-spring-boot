package com.vladblaj.socialplatform.socialplatformspringboot;

import com.vladblaj.socialplatform.socialplatformspringboot.comments.Comment;
import com.vladblaj.socialplatform.socialplatformspringboot.images.CommentReaderRepository;
import com.vladblaj.socialplatform.socialplatformspringboot.images.Image;
import com.vladblaj.socialplatform.socialplatformspringboot.images.ImageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class HomeController {

    private static final String BASE_PATH = "/images";
    private static final String FILENAME = "{filename:.+}";

    // tag::extra[]
    private final ImageService imageService;
    private final CommentReaderRepository repository;

    public HomeController(ImageService imageService,
                          CommentReaderRepository repository) {
        this.imageService = imageService;
        this.repository = repository;
    }
    // end::extra[]

    // tag::comments[]
    @GetMapping("/")
    public Mono<String> index(Model model) {
        model.addAttribute("images",
                imageService
                        .findAllImages()
                        .flatMap(image ->
                                Mono.just(image)
                                        .zipWith(repository.findByImageId(
                                                image.getId()).collectList()))
                        .map(imageAndComments -> new HashMap<String, Object>() {{
                            put("id", imageAndComments.getT1().getId());
                            put("name", imageAndComments.getT1().getName());
                            put("comments",
                                    imageAndComments.getT2());
                        }}));
        model.addAttribute("extra",
                "DevTools can also detect code changes too");
        return Mono.just("index");
    }
    // end::comments[]

    @Data
    @AllArgsConstructor
    static class CommandAndImage {
        Comment comment;
        Image image;
    }

    @GetMapping("/comments/{id}")
    @ResponseBody
    public Flux<Comment> comments(@PathVariable String id) {
        return repository.findByImageId(id);
    }

    @GetMapping(value = BASE_PATH + "/" + FILENAME + "/raw",
            produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public Mono<ResponseEntity<?>> oneRawImage(
            @PathVariable String filename) {
        // tag::try-catch[]
        return imageService.findOneImage(filename)
                .map(resource -> {
                    try {
                        return ResponseEntity.ok()
                                .contentLength(resource.contentLength())
                                .body(new InputStreamResource(
                                        resource.getInputStream()));
                    } catch (IOException e) {
                        return ResponseEntity.badRequest()
                                .body("Couldn't find " + filename +
                                        " => " + e.getMessage());
                    }
                });
        // end::try-catch[]
    }

    @PostMapping(value = BASE_PATH)
    public Mono<String> createFile(@RequestPart(name = "file")
                                           Flux<FilePart> files) {
        return imageService.createImage(files)
                .then(Mono.just("redirect:/"));
    }

    @DeleteMapping(BASE_PATH + "/" + FILENAME)
    public Mono<String> deleteFile(@PathVariable String filename) {
        return imageService.deleteImage(filename)
                .then(Mono.just("redirect:/"));
    }
}
