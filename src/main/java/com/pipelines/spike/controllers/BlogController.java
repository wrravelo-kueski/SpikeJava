package com.pipelines.spike.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pipelines.spike.mocks.BlogMockedData;
import com.pipelines.spike.models.Blog;
import com.pipelines.spike.repositories.BlogRepository;

@RestController
public class BlogController {

  @Autowired
  BlogRepository blogRepository;

  BlogMockedData blogMockedData = BlogMockedData.getInstance();
  
  @GetMapping("/blog")
  public List<Blog> index() {
    return blogRepository.findAll();
  }

  @GetMapping("/blog/{id}")
  public Blog show(@PathVariable String id) throws NotFoundException {
      int blogId = Integer.parseInt(id);
      return blogRepository.findById(blogId).orElseThrow();
  }

  @PostMapping("/blog/search")
  public List<Blog> search(@RequestBody Map<String, String> body) {
    String searchTerm = body.get("text");
    return blogRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
  }

  @PostMapping("/blog")
  public Blog create(@RequestBody Map<String, String> body) {
    String title = body.get("title");
    String content = body.get("content");
    return blogRepository.save(
      new Blog(title, content)
    );
  }

  @PutMapping("/blog/{id}")
  public Blog update(@PathVariable String id, @RequestBody Map<String, String> body) throws NotFoundException {
    int blogId = Integer.parseInt(id);
    // getting blog
    Blog blog = blogRepository.findById(blogId).orElseThrow();
    blog.setTitle(body.get("title"));
    blog.setContent(body.get("content"));
    return blogRepository.save(blog);
  }

  @DeleteMapping("blog/{id}")
  public boolean delete(@PathVariable String id) {
    int blogId = Integer.parseInt(id);
    blogRepository.deleteById(blogId);
    return true;
  }
}