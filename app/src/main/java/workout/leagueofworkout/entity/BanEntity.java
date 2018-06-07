package workout.leagueofworkout.entity;

public class BanEntity {
    int pickTurn, championId;

    public BanEntity(int pickTurn, int championId) {

        this.pickTurn = pickTurn;
        this.championId = championId;
    }

    public int getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(int pickTurn) {
        this.pickTurn = pickTurn;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }
}
