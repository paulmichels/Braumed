package workout.leagueofworkout.entity;

import java.util.ArrayList;
import java.util.List;

public class TeamEntity {
    private int teamId, towerKills, inhibitorKills, baronKills, dragonKills, vilemawKills, riftHeraldKills, dominionVictoryScore;
    private String win;
    private boolean firstBlood, firstInhibitor, firstBaron, firstDragon, firstRiftHerald;
    private List<BanEntity> listBan = new ArrayList<>();

    public TeamEntity(int teamId, int towerKills, int inhibitorKills, int baronKills, int dragonKills, int vilemawKills, int riftHeraldKills, int dominionVictoryScore, String win, boolean firstBlood, boolean firstInhibitor, boolean firstBaron, boolean firstDragon, boolean firstRiftHerald, List<BanEntity> listBan) {
        this.teamId = teamId;
        this.towerKills = towerKills;
        this.inhibitorKills = inhibitorKills;
        this.baronKills = baronKills;
        this.dragonKills = dragonKills;
        this.vilemawKills = vilemawKills;
        this.riftHeraldKills = riftHeraldKills;
        this.dominionVictoryScore = dominionVictoryScore;
        this.win = win;
        this.firstBlood = firstBlood;
        this.firstInhibitor = firstInhibitor;
        this.firstBaron = firstBaron;
        this.firstDragon = firstDragon;
        this.firstRiftHerald = firstRiftHerald;
        this.listBan = listBan;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getTowerKills() {
        return towerKills;
    }

    public int getInhibitorKills() {
        return inhibitorKills;
    }

    public int getBaronKills() {
        return baronKills;
    }

    public int getDragonKills() {
        return dragonKills;
    }

    public int getVilemawKills() {
        return vilemawKills;
    }

    public int getRiftHeraldKills() {
        return riftHeraldKills;
    }

    public int getDominionVictoryScore() {
        return dominionVictoryScore;
    }

    public String getWin() {
        return win;
    }

    public boolean isFirstBlood() {
        return firstBlood;
    }

    public boolean isFirstInhibitor() {
        return firstInhibitor;
    }

    public boolean isFirstBaron() {
        return firstBaron;
    }

    public boolean isFirstDragon() {
        return firstDragon;
    }

    public boolean isFirstRiftHerald() {
        return firstRiftHerald;
    }

    public List<BanEntity> getListBan() {
        return listBan;
    }
}
