package pl.edu.agh.tai.partytura.model;


import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.tai.partytura.model.exceptions.UnfollowingNotFollowedInstitutionException;
import pl.edu.agh.tai.partytura.model.factories.CommentFactory;
import pl.edu.agh.tai.partytura.model.factories.PostFactory;

import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;
import static org.mockito.Mockito.*;

public class AttenderTest {

  private Attender attender;
  private Institution institution;
  private PostFactory postFactory;
  private CommentFactory commentFactory;

  @Before
  public void setUp() throws Exception {
    postFactory = mock(PostFactory.class);
    commentFactory = mock(CommentFactory.class);
    institution = mock(Institution.class);
    attender = new Attender("wikla", 1, postFactory, commentFactory);
  }

  @Test
  public void shouldBeAbleToFollowInstitution() throws Exception {
    // given

    // when
    attender.follow(institution);

    // then
    Truth.assertThat(attender.getFollowedInstitutions()).isNotEmpty();
    Truth.assertThat(attender.getFollowedInstitutions()).containsExactly(institution);
  }

  @Test
  public void shouldNotAddDuplicatedInstitutionsToFollowedList() {
    // given

    // when
    attender.follow(institution);
    attender.follow(institution);

    //then
    Truth.assertThat(attender.getFollowedInstitutions()).containsExactly(institution);
  }

  @Test
  public void shouldBeAbleToRemoveInstitutionFromFollowedInstitutions() {
    // given
    attender.follow(institution);
    assertThat(attender.getFollowedInstitutions()).containsExactly(institution);

    // when
    try {
      attender.unfollow(institution);

      //then
    } catch (UnfollowingNotFollowedInstitutionException e) {
      assert_().fail("Should not throw exception when unfollowing followed institution.");
    }
    assertThat(attender.getFollowedInstitutions()).isEmpty();
  }

  @Test
  public void shouldThrowExceptionWhenUnfollowingNotFollowedInstitution() {
    // given

    // when
    try {
      attender.unfollow(institution);

      //then
      assert_().fail("Should throw exception when unfollowing not followed institution.");
    } catch (UnfollowingNotFollowedInstitutionException e) {
      Truth.assertThat(attender.getFollowedInstitutions()).isEmpty();
    }
  }

  @Test
  public void shouldBeAbleToJoinEvent() throws Exception {
    // given
    Event event = mock(Event.class);

    // when
    attender.joinEvent(event);

    //then
    Truth.assertThat(attender.getJoinedEvents()).containsExactly(event);
  }

  @Test
  public void shouldBeAbleToAddPostToEvent() throws Exception {
    // given
    Event event = mock(Event.class);
    Post post = mock(Post.class);
    String content = "Post content";
    when(postFactory.createPost(content, attender)).thenReturn(post);

    // when
    attender.addPost(content, event);

    //then
    verify(event).addPost(post);
  }

  @Test
  public void shouldBeAbleToAddCommentToPost() throws Exception {
    // given
    Post post = mock(Post.class);
    String commentContent = "Comment";
    Comment comment = new Comment(commentContent, attender, LocalDateTime.of(2016, 6, 10, 10, 0));
    when(commentFactory.createComment(commentContent, attender)).thenReturn(comment);

    // when
    attender.addComment(commentContent, post);

    //then
    verify(post).addComment(comment);
  }


}