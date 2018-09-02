package util.model;


/**
 * Model to table players on database
 */

public class Player {

    private String Name;
    private int Id;

    public Player(){}


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
