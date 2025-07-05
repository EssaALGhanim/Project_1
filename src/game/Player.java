package game;

public class Player {

    private String name;
    private String ID;
    private int score;
    private String sign;

    public Player(String name, String sign){
        this.name = name;
        ID = Math.random() * 1000 + "";
        score = 0;
        this.sign = sign;
    }
    public String getSign(){
        return sign;
    }

    public String getName(){
        return name;
    }

    public String getID(){
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }




}
