package com.vladblaj.socialplatform.socialplatformspringboot.images;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private String id;
    private String imageId;
    private String comment;
}