package pl.edu.agh.tai.partytura.model;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by wikla on 19.05.16.
 */
public class InstitutionTest {
  private Institution institution;
  private EventFactory eventFactory;

  @Before
  public void setUp() {
    eventFactory = mock(EventFactory.class);
    institution = new Institution("ICE Congress Centre", eventFactory);
  }

  @Test
  public void shouldCreateEventAndAddItToCreatedEventsSet() {
    //given
    Event event = mock(Event.class);
    when(eventFactory.createEvent("meh")).thenReturn(event);

    //when
    institution.createEvent("meh");

    //then
    assertThat(institution.getCreatedEvents()).containsExactly(event);
  }
}
