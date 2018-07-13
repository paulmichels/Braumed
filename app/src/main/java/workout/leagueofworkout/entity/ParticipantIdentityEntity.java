package workout.leagueofworkout.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ParticipantIdentityEntity implements Serializable, Parcelable {
    long accountId, summonerId, currentAccountId;
    int participantId, profileIcon;
    String matchHistoryUri, summonerName, currentPlatformId;

    public ParticipantIdentityEntity(long accountId, long summonerId, long currentAccountId, int participantId, int profileIcon, String matchHistoryUri, String summonerName, String currentPlatformId) {
        this.accountId = accountId;
        this.summonerId = summonerId;
        this.currentAccountId = currentAccountId;
        this.participantId = participantId;
        this.profileIcon = profileIcon;
        this.matchHistoryUri = matchHistoryUri;
        this.summonerName = summonerName;
        this.currentPlatformId = currentPlatformId;
    }

    protected ParticipantIdentityEntity(Parcel in) {
        accountId = in.readLong();
        summonerId = in.readLong();
        currentAccountId = in.readLong();
        participantId = in.readInt();
        profileIcon = in.readInt();
        matchHistoryUri = in.readString();
        summonerName = in.readString();
        currentPlatformId = in.readString();
    }

    public static final Creator<ParticipantIdentityEntity> CREATOR = new Creator<ParticipantIdentityEntity>() {
        @Override
        public ParticipantIdentityEntity createFromParcel(Parcel in) {
            return new ParticipantIdentityEntity(in);
        }

        @Override
        public ParticipantIdentityEntity[] newArray(int size) {
            return new ParticipantIdentityEntity[size];
        }
    };

    public long getAccountId() {
        return accountId;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public long getCurrentAccountId() {
        return currentAccountId;
    }

    public int getParticipantId() {
        return participantId;
    }

    public int getProfileIcon() {
        return profileIcon;
    }

    public String getMatchHistoryUri() {
        return matchHistoryUri;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public String getCurrentPlatformId() {
        return currentPlatformId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(accountId);
        dest.writeLong(summonerId);
        dest.writeLong(currentAccountId);
        dest.writeInt(participantId);
        dest.writeInt(profileIcon);
        dest.writeString(matchHistoryUri);
        dest.writeString(summonerName);
        dest.writeString(currentPlatformId);
    }
}
