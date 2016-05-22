package pl.edu.agh.tai.partytura.model;

import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.tai.partytura.model.factories.CommentFactory;
import pl.edu.agh.tai.partytura.model.factories.EventFactory;
import pl.edu.agh.tai.partytura.model.factories.PostFactory;

import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

public class InstitutionTest {
  private Institution institution;
  private EventFactory eventFactory;
  private PostFactory postFactory;
  private CommentFactory commentFactory;

  @Before
  public void setUp() {
    eventFactory = mock(EventFactory.class);
    postFactory = mock(PostFactory.class);
    commentFactory = mock(CommentFactory.class);
    institution = new Institution("ICE Congress Centre", eventFactory, postFactory, commentFactory);
  }

  @Test
  public void shouldCreateEventAndAddItToCreatedEventsSet() {
    //given
    Event event = mock(Event.class);
    String eventName = "meh";
    String eventHashtag = "eventHashtag";
    LocalDateTime dateTime = LocalDateTime.of(2016, 7, 10, 18, 0);
    EventLocation location = new EventLocation("ICE");
    when(eventFactory.createEvent(eventName, eventHashtag, dateTime, location)).thenReturn(event);

    //when
    institution.createEvent(eventName, eventHashtag, dateTime, location);

    //then
    assertThat(institution.getCreatedEvents()).containsExactly(event);
  }

  @Test
  public void shouldBeAbleToAddPostToEvent() throws Exception {
    // given
    Event event = mock(Event.class);
    Post post = mock(Post.class);
    String content = "Post content";
    when(postFactory.createPost(content, institution)).thenReturn(post);

    // when
    institution.addPost(content, event);

    //then
    verify(event).addPost(post);
  }

  @Test
  public void shouldBeAbleToAddCommentToPost() throws Exception {
    // given
    Post post = mock(Post.class);
    String commentContent = "Comment";
    Comment comment = new Comment(commentContent, institution, LocalDateTime.of(2016, 6, 10, 10, 0));
    when(commentFactory.createComment(commentContent, institution)).thenReturn(comment);

    // when
    institution.addComment(commentContent, post);

    //then
    verify(post).addComment(comment);
  }

}
