package workout.leagueofworkout.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TeamEntity implements Serializable, Parcelable {
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

    protected TeamEntity(Parcel in) {
        teamId = in.readInt();
        towerKills = in.readInt();
        inhibitorKills = in.readInt();
        baronKills = in.readInt();
        dragonKills = in.readInt();
        vilemawKills = in.readInt();
        riftHeraldKills = in.readInt();
        dominionVictoryScore = in.readInt();
        win = in.readString();
        firstBlood = in.readByte() != 0;
        firstInhibitor = in.readByte() != 0;
        firstBaron = in.readByte() != 0;
        firstDragon = in.readByte() != 0;
        firstRiftHerald = in.readByte() != 0;
        listBan = in.createTypedArrayList(BanEntity.CREATOR);
    }

    public static final Creator<TeamEntity> CREATOR = new Creator<TeamEntity>() {
        @Override
        public TeamEntity createFromParcel(Parcel in) {
            return new TeamEntity(in);
        }

        @Override
        public TeamEntity[] newArray(int size) {
            return new TeamEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(teamId);
        dest.writeInt(towerKills);
        dest.writeInt(inhibitorKills);
        dest.writeInt(baronKills);
        dest.writeInt(dragonKills);
        dest.writeInt(vilemawKills);
        dest.writeInt(riftHeraldKills);
        dest.writeInt(dominionVictoryScore);
        dest.writeString(win);
        dest.writeByte((byte) (firstBlood ? 1 : 0));
        dest.writeByte((byte) (firstInhibitor ? 1 : 0));
        dest.writeByte((byte) (firstBaron ? 1 : 0));
        dest.writeByte((byte) (firstDragon ? 1 : 0));
        dest.writeByte((byte) (firstRiftHerald ? 1 : 0));
        dest.writeTypedList(listBan);
    }
}
