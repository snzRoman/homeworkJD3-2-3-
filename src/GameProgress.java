import java.io.*;

public class GameProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    private int health;
    private int weapon;
    private int lvl;
    private double distance;

    public GameProgress(int health, int weapon, int lvl, double distance) {
        this.health = health;
        this.weapon = weapon;
        this.lvl = lvl;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapon=" + weapon +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }

}
