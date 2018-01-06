package application.views.user;


public final class UserScoreboard {

    private final String username;
    private final Long userID;
    private final Double score;

    public UserScoreboard(String username, Long userID, Double score) {
        this.username = username;
        this.userID = userID;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public Long getUserID() {
        return userID;
    }

    public Double getScore() {
        return score;
    }
}
