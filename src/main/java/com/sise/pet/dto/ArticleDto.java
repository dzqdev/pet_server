package com.sise.pet.dto;

import com.sise.pet.entity.Article;
import com.sise.pet.entity.Pet;
import lombok.Data;

@Data
public class ArticleDto extends Article {
    private Pet pet;
    private String typeName;
    private String petName;
}
