package workout.leagueofworkout.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Objects;

public class SummonerEntity implements Parcelable, Serializable {

    long summonerLevel, revisionDate, id, accountId;
    int profileIconId;
    String name;

    public SummonerEntity(long summonerLevel, long revisionDate, long id, long accountId, int profileIconId, String name) {
        this.summonerLevel = summonerLevel;
        this.revisionDate = revisionDate;
        this.id = id;
        this.accountId = accountId;
        this.profileIconId = profileIconId;
        this.name = name;
    }

    protected SummonerEntity(Parcel in) {
        summonerLevel = in.readLong();
        revisionDate = in.readLong();
        id = in.readLong();
        accountId = in.readLong();
        profileIconId = in.readInt();
        name = in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SummonerEntity that = (SummonerEntity) o;
        return summonerLevel == that.summonerLevel &&
                revisionDate == that.revisionDate &&
                id == that.id &&
                accountId == that.accountId &&
                profileIconId == that.profileIconId &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(summonerLevel, revisionDate, id, accountId, profileIconId, name);
    }

    public static final Creator<SummonerEntity> CREATOR = new Creator<SummonerEntity>() {
        @Override
        public SummonerEntity createFromParcel(Parcel in) {
            return new SummonerEntity(in);
        }

        @Override
        public SummonerEntity[] newArray(int size) {
            return new SummonerEntity[size];
        }
    };

    public long getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(long summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(summonerLevel);
        dest.writeLong(revisionDate);
        dest.writeLong(id);
        dest.writeLong(accountId);
        dest.writeInt(profileIconId);
        dest.writeString(name);
    }
}
