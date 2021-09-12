package com.example.nytimes;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.nytimes.model.Article;

import java.util.ArrayList;

public class UnitTest {

    private static final Article article = new Article("https://google.com", "Test Article", "This is a test article", "By Unknown Author", "20201-09-12", new ArrayList<>());

    @Test
    public void article_exists(){
        assertNotNull(article);
    }

    @Test
    public void article_has_url(){
        assertNotNull(article.url);
    }

    @Test
    public void article_has_title(){
        assertNotNull(article.title);
    }
}