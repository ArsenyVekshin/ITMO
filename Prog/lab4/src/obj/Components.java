package obj;

public enum Components {
    TOWEL1("кухонное полотенце"),
    TOWEL2("мохнатое полотенце"),
    NAPKINS("салфетки"),
    TEETHS("зубы (вставная челюсть)"),
    PATCH("пластырь");

    private final String replic;
    Components(final String replic){
        this.replic = replic;
    }
    public String getReplic(){return replic;}

    @Override
    public String toString(){
        return getReplic();
    }
}
