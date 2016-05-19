package pl.edu.agh.tai.partytura.model;


import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AttenderTest {

  private Attender attender;
  private Institution institution;

  @Before
  public void setUp() throws Exception {
    attender = new Attender("wikla");
    institution = new Institution("FilharmoniaKrakowska");
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

    // when
    attender.addPost(post, event);

    //then
    verify(event).addPost(post);
  }


}