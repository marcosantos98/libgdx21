package marco.ffloat.world;

public class LevelSystem {

    public int currentLevel = 1;

    public LevelInfo getInfoForLevel() {
        return new LevelInfo(this.currentLevel * 20, this.currentLevel * 50);
    }
}
