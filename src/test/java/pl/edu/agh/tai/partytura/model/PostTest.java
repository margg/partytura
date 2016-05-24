package pl.edu.agh.tai.partytura.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;

public class PostTest {

  @Test
  public void shouldAllowToAddComments() throws Exception {
    // given
    User user = mock(User.class);
    Post post = new Post("Post content", user,  LocalDateTime.of(2016, 6, 10, 11, 0));
    Comment comment = mock(Comment.class);

    // when
    post.addComment(comment);

    // then
    assertThat(post.getComments()).containsExactly(comment);
  }
}