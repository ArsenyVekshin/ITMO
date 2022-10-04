package People;

import Places.Place;

public class Human {
    private String name = "default";
    private String status = "none";
    protected String place ;

    public Human(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String where(){
        if (place == null) return "none";
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
