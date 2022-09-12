package Family.Map.Client.Data;

import Model.Event;

public class LinkedEvents {
    private Event firstLoc;
    private Event secondLoc;

    public LinkedEvents(Event first, Event second){
        setFirst(first);
        setSecond(second);
    }

    public Event getFirst() { return firstLoc; }
    public void setFirst(Event first) { this.firstLoc = first; }
    public Event getSecond() { return secondLoc; }
    public void setSecond(Event second) { this.secondLoc = second; }
}
