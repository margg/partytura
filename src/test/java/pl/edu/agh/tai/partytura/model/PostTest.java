package pl.edu.agh.tai.partytura.model;

import org.junit.Test;

import java.sql.Timestamp;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;

public class PostTest {

  @Test
  public void shouldEnableAddingComments() throws Exception {
    // given
    User user = mock(User.class);
    Comment comment = new Comment("Comment content", user, mock(Timestamp.class));
    Post post = new Post("Post content", user, mock(Timestamp.class));

    // when
    post.addComment(comment);

    // then
    assertThat(post.getComments()).containsExactly(comment);
  }
}