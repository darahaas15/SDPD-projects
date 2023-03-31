package androidsamples.java.tictactoe;

public class User {
    private String email;
    private double gamesDrawn;
    private double gamesLost;
    private double gamesWon;

    public User() {

    }

    public User(String email, double gamesDrawn, double gamesLost, double gamesWon) {
        this.email = email;
        this.gamesDrawn = gamesDrawn;
        this.gamesLost = gamesLost;
        this.gamesWon = gamesWon;
    }

    public double getGamesWon() {
        return gamesWon;
    }

    public double getGamesLost() {
        return gamesLost;
    }

    public double getGamesDrawn() {
        return gamesDrawn;
    }

    public String getEmail() {
        return email;
    }
}
