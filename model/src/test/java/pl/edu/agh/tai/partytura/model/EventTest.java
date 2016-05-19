package pl.edu.agh.tai.partytura.model;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;

public class EventTest {

  @Test
  public void shouldAllowToAddPosts() throws Exception {
    // given
    Event event = new Event("Event");
    Post post = mock(Post.class);

    // when
    event.addPost(post);

    // then
    assertThat(event.getPosts()).containsExactly(post);
  }
}