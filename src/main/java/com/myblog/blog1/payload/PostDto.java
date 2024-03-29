package com.myblog.blog1.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Previous Annotation used before was @getter & @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long id;

    private String title;
    private String description;
    private String content;
}
