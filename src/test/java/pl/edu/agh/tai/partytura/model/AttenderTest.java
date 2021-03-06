package pl.edu.agh.tai.partytura.model;


import com.google.common.truth.Truth;
import org.junit.Before;
import org.junit.Test;
import pl.edu.agh.tai.partytura.model.exceptions.UnfollowingNotFollowedInstitutionException;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;
import static org.mockito.Mockito.mock;

public class AttenderTest {

  private Attender attender;
  private Institution institution;

  @Before
  public void setUp() throws Exception {
    institution = mock(Institution.class);
    attender = new Attender("wikla");
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
}