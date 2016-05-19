package pl.edu.agh.tai.partytura.model;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InstitutionTest {
  private Institution institution;
  private EventFactory eventFactory;
  private PostFactory postFactory;

  @Before
  public void setUp() {
    eventFactory = mock(EventFactory.class);
    postFactory = mock(PostFactory.class);
    institution = new Institution("ICE Congress Centre", eventFactory, postFactory);
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
