package Family.Map.Client.Data;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class UserSettings {
    private boolean lifeLines;

    private boolean treeLines;

    private boolean spouseLines;

    private boolean fatherSide;
    private boolean motherSide;

    private boolean mEvents;
    private boolean fEvents;

    private  boolean loggedIn;

    public UserSettings(){
        setLifeLines(true);
        setTreeLines(true);
        setSpouseLines(true);
        setFatherSide(true);
        setMotherSide(true);
        setMaleEvents(true);
        setFemaleEvents(true);
        setLoggedIn(false);
    }


    public boolean isLifeLines() { return lifeLines; }
    public void setLifeLines(boolean lifeLines) { this.lifeLines = lifeLines; }

    public boolean isTreeLines() { return treeLines; }
    public void setTreeLines(boolean treeLines) { this.treeLines = treeLines; }

    public boolean isSpouseLines() { return spouseLines; }
    public void setSpouseLines(boolean spouseLines) { this.spouseLines = spouseLines; }

    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    public boolean isFatherSide() { return fatherSide; }
    public void setFatherSide(boolean fatherSide) { this.fatherSide = fatherSide; }

    public boolean isMotherSide() { return motherSide; }
    public void setMotherSide(boolean motherSide) { this.motherSide = motherSide; }

    public boolean isMaleEvents() { return mEvents; }
    public void setMaleEvents(boolean maleEvents) { this.mEvents = maleEvents; }

    public boolean isFemaleEvents() { return fEvents; }

    public void setFemaleEvents(boolean femaleEvents) { this.fEvents = femaleEvents; }
}
