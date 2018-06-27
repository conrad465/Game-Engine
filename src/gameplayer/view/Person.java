package gameplayer.view;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Person Class to specify a Person object with information about the name
 * and Score of a record holder.
 * @author Ryan
 */

public class Person {
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty score;
 
    public Person(String name, Double score) {
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleDoubleProperty(score);
    }
 
    public String getName() {
        return name.get();
    }
    public void setName(String playerName) {
    		name.set(playerName);
    }

    public Double getScore() {
        return score.get();
    }
    public void setScore(double playerScore) {
        score.set(playerScore);
    }
}
