package workout.leagueofworkout.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BanEntity implements Serializable, Parcelable {
    int pickTurn, championId;

    public BanEntity(int pickTurn, int championId) {

        this.pickTurn = pickTurn;
        this.championId = championId;
    }

    protected BanEntity(Parcel in) {
        pickTurn = in.readInt();
        championId = in.readInt();
    }

    public static final Creator<BanEntity> CREATOR = new Creator<BanEntity>() {
        @Override
        public BanEntity createFromParcel(Parcel in) {
            return new BanEntity(in);
        }

        @Override
        public BanEntity[] newArray(int size) {
            return new BanEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pickTurn);
        dest.writeInt(championId);
    }
}
