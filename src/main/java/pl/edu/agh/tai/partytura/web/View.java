package pl.edu.agh.tai.partytura.web;

public class View {

  public interface User {
  }

  public interface Attender extends User, EventBase {
  }

  public interface Institution extends User {
  }

  public interface Comment {
  }

  public interface EventBase { }

  public interface Event extends EventBase { }
}
