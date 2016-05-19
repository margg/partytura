package pl.edu.agh.tai.partytura.model;


import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.tai.partytura.model.exceptions.UnfollowingNotFollowedInstitutionException;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AttenderTest {

  private Attender attender;
  private Institution institution;
  private PostFactory postFactory;

  @Before
  public void setUp() throws Exception {
    postFactory = mock(PostFactory.class);
    attender = new Attender("wikla", postFactory);
    institution = mock(Institution.class);
  }

  @Test
  public void shouldBeAbleToFollowInstitution() throws Exception {
    // given

    // when
    attender.follow(institution);

    // then
    assertThat(attender.getFollowedInstitutions()).isNotEmpty();
    assertThat(attender.getFollowedInstitutions()).containsExactly(institution);
  }

  @Test
  public void shouldNotAddDuplicatedInstitutionsToFollowedList() {
    // given

    // when
    attender.follow(institution);
    attender.follow(institution);

    //then
    assertThat(attender.getFollowedInstitutions()).containsExactly(institution);
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
      assertThat(attender.getFollowedInstitutions()).isEmpty();
    }
  }

  @Test
  public void shouldBeAbleToJoinEvent() throws Exception {
    // given
    Event event = mock(Event.class);

    // when
    attender.joinEvent(event);

    //then
    assertThat(attender.getJoinedEvents()).containsExactly(event);
  }

  @Test
  public void shouldBeAbleToAddPostToEvent() throws Exception {
    // given
    Event event = mock(Event.class);
    Post post = mock(Post.class);
    String content = "Post content";
    when(postFactory.createPost(content)).thenReturn(post);

    // when
    attender.addPost(content, event);

    //then
    verify(event).addPost(post);
  }


}