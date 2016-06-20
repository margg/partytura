package pl.edu.agh.tai.partytura.model;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;

public class InstitutionTest {
  private Institution institution;

  @Before
  public void setUp() {
    institution = new Institution("ICE Congress Centre");
  }

  @Test
  public void shouldAddEventToCreatedEventsSet() {
    //given
    Event event = mock(Event.class);

    //when
    institution.addEvent(event);

    //then
    assertThat(institution.getCreatedEvents()).containsExactly(event);
  }

  @Test
  public void shouldAddGenreToGenresSet() {
    //given
    String genre = "classical music";

    //when
    institution.addGenre(genre);

    //then
    assertThat(institution.getGenres()).containsExactly(genre);
  }

}
