package com.vladblaj.socialplatform.socialplatformspringboot;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ChapterRepository
	extends ReactiveCrudRepository<Chapter, String> {

}