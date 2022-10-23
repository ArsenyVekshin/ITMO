package obj;

public enum State {
    DEFAULT("NONE"),
    LIGHT("освещен(-а)"),
    DARK("в темноте"),
    OPEN("открыт(-а)"),
    CLOSED("закрыт(-а)"),
    ON("включен(-а)"),
    OFF("отключен(-а)"),
    FALLS("упал(-а)"),
    SCALLEDTEETHS("скалит зубы"),
    LEAN("прислонился к стене"),
    CONFUSED("в замешательстве"),
    SCARED("напуган(-а)");


    private String replic;
    State(final String replic){
        this.replic = replic;
    }
    public String getReplic(){return replic;}
}
