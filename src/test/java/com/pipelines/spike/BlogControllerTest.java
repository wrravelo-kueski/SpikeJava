package com.pipelines.spike;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.Assert.assertTrue;
import com.pipelines.spike.models.Blog;
import com.pipelines.spike.repositories.BlogRepository;

public class BlogControllerTest extends AbstractTest {
  @Autowired
  private BlogRepository blogRepository;

  @Override
  @Before
  public void setUp() {
    super.setUp();

    addBlogs();
  }

  @Test
  public void index() throws Exception {
    String uri = "/blog";
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    String content = mvcResult.getResponse().getContentAsString();
    Blog[] blogList = super.mapFromJson(content, Blog[].class);
    assertTrue(blogList.length == 1);
  }

  private void addBlogs() {
    blogRepository.save(new Blog("title", "content"));
  }
}
