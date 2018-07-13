package workout.leagueofworkout.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameEntity implements Serializable, Parcelable {
    private String platformId, gameVersion, gameMode, gameType;
    private long gameId, gameCreation, gameDuration;
    private int queueId, mapId, seasonId;
    private List<TeamEntity> teamList = new ArrayList<>();
    private List<ParticipantEntity> participantList = new ArrayList<>();
    private List<ParticipantIdentityEntity> participantIdentityList = new ArrayList<>();

    public GameEntity(String platformId, String gameVersion, String gameMode, String gameType, long gameId, long gameCreation, long gameDuration, int queueId, int mapId, int seasonId, List<TeamEntity> teamList, List<ParticipantEntity> participantList, List<ParticipantIdentityEntity> participantIdentityList) {
        this.platformId = platformId;
        this.gameVersion = gameVersion;
        this.gameMode = gameMode;
        this.gameType = gameType;
        this.gameId = gameId;
        this.gameCreation = gameCreation;
        this.gameDuration = gameDuration;
        this.queueId = queueId;
        this.mapId = mapId;
        this.seasonId = seasonId;
        this.teamList = teamList;
        this.participantList = participantList;
        this.participantIdentityList = participantIdentityList;
    }

    protected GameEntity(Parcel in) {
        platformId = in.readString();
        gameVersion = in.readString();
        gameMode = in.readString();
        gameType = in.readString();
        gameId = in.readLong();
        gameCreation = in.readLong();
        gameDuration = in.readLong();
        queueId = in.readInt();
        mapId = in.readInt();
        seasonId = in.readInt();
    }

    public static final Creator<GameEntity> CREATOR = new Creator<GameEntity>() {
        @Override
        public GameEntity createFromParcel(Parcel in) {
            return new GameEntity(in);
        }

        @Override
        public GameEntity[] newArray(int size) {
            return new GameEntity[size];
        }
    };

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getGameCreation() {
        return gameCreation;
    }

    public void setGameCreation(long gameCreation) {
        this.gameCreation = gameCreation;
    }

    public long getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(long gameDuration) {
        this.gameDuration = gameDuration;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public List<TeamEntity> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<TeamEntity> teamList) {
        this.teamList = teamList;
    }

    public List<ParticipantEntity> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<ParticipantEntity> participantList) {
        this.participantList = participantList;
    }

    public List<ParticipantIdentityEntity> getParticipantIdentityList() {
        return participantIdentityList;
    }

    public void setParticipantIdentityList(List<ParticipantIdentityEntity> participantIdentityList) {
        this.participantIdentityList = participantIdentityList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(platformId);
        dest.writeString(gameVersion);
        dest.writeString(gameMode);
        dest.writeString(gameType);
        dest.writeLong(gameId);
        dest.writeLong(gameCreation);
        dest.writeLong(gameDuration);
        dest.writeInt(queueId);
        dest.writeInt(mapId);
        dest.writeInt(seasonId);
    }
}
