package pl.edu.agh.tai.partytura.model;


import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

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
      fail("Should not throw exception when unfollowing followed institution.");
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
      fail("Should throw exception when unfollowing not followed institution.");
    } catch (UnfollowingNotFollowedInstitutionException e) {
      assertThat(attender.getFollowedInstitutions()).isEmpty();
    }
  }
}