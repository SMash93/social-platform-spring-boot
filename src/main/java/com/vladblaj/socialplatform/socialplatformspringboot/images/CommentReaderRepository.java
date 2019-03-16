package com.vladblaj.socialplatform.socialplatformspringboot.images;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CommentReaderRepository extends ReactiveCrudRepository<Comment, String> {
    Flux<Comment> findByImageId(String imageId);
}
