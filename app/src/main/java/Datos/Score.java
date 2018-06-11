package Datos;

/**
 * Created by ramir on 6/10/2018.
 */

public class Score {
    String id;
    int score;

    public Score(String id, int score) {
        this.id = id;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
