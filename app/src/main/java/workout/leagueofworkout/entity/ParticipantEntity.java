package workout.leagueofworkout.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ParticipantEntity implements Serializable, Parcelable{
    private long physicalDamageDealt, magicDamageDealt, totalDamageDealt, magicDamageDealtToChampions, damageDealtToObjectives, visionScore, damageSelfMitigated, magicalDamageTaken, trueDamageTaken, damageDealtToTurrets, physicalDamageDealtToChampions, trueDamageDealt, trueDamageDealtToChampions, totalHeal, totalDamageDealtToChampions, totalDamageTaken, timeCCingOthers, physicalDamageTaken;
    private int teamId, championId, spell1Id, spell2Id, neutralMinionsKilledTeamJungle, totalPlayerScore, deaths, neutralMinionsKilledEnemyJungle, largestCriticalStrike, visionWardsBoughtInGame, largestKillingSpree, item1, quadraKills, totalTimeCrowdControlDealt, longestTimeSpentLiving, wardsKilled, item2, item3, item0, wardsPlaced, item4, item5, item6, turretKills, tripleKills, champLevel, goldEarned, kills, doubleKills, assists, unrealKills, neutralMinionsKilled, objectivePlayerScore, combatPlayerScore, goldSpent, participantId, pentaKills, totalMinionsKilled, largestMultiKill, sightWardsBoughtInGame, totalUnitsHealed, inhibitorKills, totalScoreRank, killingSprees;
    private boolean win, firstTowerAssist, firstTowerKill, firstBloodAssist, firstInhibitorKill, firstInhibitorAssist, firstBloodKill;
    private String highestAchievedSeasonTier, role, lane;

    public ParticipantEntity(long physicalDamageDealt, long magicDamageDealt, long totalDamageDealt, long magicDamageDealtToChampions, long damageDealtToObjectives, long visionScore, long damageSelfMitigated, long magicalDamageTaken, long trueDamageTaken, long damageDealtToTurrets, long physicalDamageDealtToChampions, long trueDamageDealt, long trueDamageDealtToChampions, long totalHeal, long totalDamageDealtToChampions, long totalDamageTaken, long timeCCingOthers, long physicalDamageTaken, int teamId, int championId, int spell1Id, int spell2Id, int neutralMinionsKilledTeamJungle, int totalPlayerScore, int deaths, int neutralMinionsKilledEnemyJungle, int largestCriticalStrike, int visionWardsBoughtInGame, int largestKillingSpree, int item1, int quadraKills, int totalTimeCrowdControlDealt, int longestTimeSpentLiving, int wardsKilled, int item2, int item3, int item0, int wardsPlaced, int item4, int item5, int item6, int turretKills, int tripleKills, int champLevel, int goldEarned, int kills, int doubleKills, int assists, int unrealKills, int neutralMinionsKilled, int objectivePlayerScore, int combatPlayerScore, int goldSpent, int participantId, int pentaKills, int totalMinionsKilled, int largestMultiKill, int sightWardsBoughtInGame, int totalUnitsHealed, int inhibitorKills, int totalScoreRank, int killingSprees, boolean win, boolean firstTowerAssist, boolean firstTowerKill, boolean firstBloodAssist, boolean firstInhibitorKill, boolean firstInhibitorAssist, boolean firstBloodKill, String highestAchievedSeasonTier, String role, String lane) {
        this.physicalDamageDealt = physicalDamageDealt;
        this.magicDamageDealt = magicDamageDealt;
        this.totalDamageDealt = totalDamageDealt;
        this.magicDamageDealtToChampions = magicDamageDealtToChampions;
        this.damageDealtToObjectives = damageDealtToObjectives;
        this.visionScore = visionScore;
        this.damageSelfMitigated = damageSelfMitigated;
        this.magicalDamageTaken = magicalDamageTaken;
        this.trueDamageTaken = trueDamageTaken;
        this.damageDealtToTurrets = damageDealtToTurrets;
        this.physicalDamageDealtToChampions = physicalDamageDealtToChampions;
        this.trueDamageDealt = trueDamageDealt;
        this.trueDamageDealtToChampions = trueDamageDealtToChampions;
        this.totalHeal = totalHeal;
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
        this.totalDamageTaken = totalDamageTaken;
        this.timeCCingOthers = timeCCingOthers;
        this.physicalDamageTaken = physicalDamageTaken;
        this.teamId = teamId;
        this.championId = championId;
        this.spell1Id = spell1Id;
        this.spell2Id = spell2Id;
        this.neutralMinionsKilledTeamJungle = neutralMinionsKilledTeamJungle;
        this.totalPlayerScore = totalPlayerScore;
        this.deaths = deaths;
        this.neutralMinionsKilledEnemyJungle = neutralMinionsKilledEnemyJungle;
        this.largestCriticalStrike = largestCriticalStrike;
        this.visionWardsBoughtInGame = visionWardsBoughtInGame;
        this.largestKillingSpree = largestKillingSpree;
        this.item1 = item1;
        this.quadraKills = quadraKills;
        this.totalTimeCrowdControlDealt = totalTimeCrowdControlDealt;
        this.longestTimeSpentLiving = longestTimeSpentLiving;
        this.wardsKilled = wardsKilled;
        this.item2 = item2;
        this.item3 = item3;
        this.item0 = item0;
        this.wardsPlaced = wardsPlaced;
        this.item4 = item4;
        this.item5 = item5;
        this.item6 = item6;
        this.turretKills = turretKills;
        this.tripleKills = tripleKills;
        this.champLevel = champLevel;
        this.goldEarned = goldEarned;
        this.kills = kills;
        this.doubleKills = doubleKills;
        this.assists = assists;
        this.unrealKills = unrealKills;
        this.neutralMinionsKilled = neutralMinionsKilled;
        this.objectivePlayerScore = objectivePlayerScore;
        this.combatPlayerScore = combatPlayerScore;
        this.goldSpent = goldSpent;
        this.participantId = participantId;
        this.pentaKills = pentaKills;
        this.totalMinionsKilled = totalMinionsKilled;
        this.largestMultiKill = largestMultiKill;
        this.sightWardsBoughtInGame = sightWardsBoughtInGame;
        this.totalUnitsHealed = totalUnitsHealed;
        this.inhibitorKills = inhibitorKills;
        this.totalScoreRank = totalScoreRank;
        this.killingSprees = killingSprees;
        this.win = win;
        this.firstTowerAssist = firstTowerAssist;
        this.firstTowerKill = firstTowerKill;
        this.firstBloodAssist = firstBloodAssist;
        this.firstInhibitorKill = firstInhibitorKill;
        this.firstInhibitorAssist = firstInhibitorAssist;
        this.firstBloodKill = firstBloodKill;
        this.highestAchievedSeasonTier = highestAchievedSeasonTier;
        this.role = role;
        this.lane = lane;
    }

    protected ParticipantEntity(Parcel in) {
        physicalDamageDealt = in.readLong();
        magicDamageDealt = in.readLong();
        totalDamageDealt = in.readLong();
        magicDamageDealtToChampions = in.readLong();
        damageDealtToObjectives = in.readLong();
        visionScore = in.readLong();
        damageSelfMitigated = in.readLong();
        magicalDamageTaken = in.readLong();
        trueDamageTaken = in.readLong();
        damageDealtToTurrets = in.readLong();
        physicalDamageDealtToChampions = in.readLong();
        trueDamageDealt = in.readLong();
        trueDamageDealtToChampions = in.readLong();
        totalHeal = in.readLong();
        totalDamageDealtToChampions = in.readLong();
        totalDamageTaken = in.readLong();
        timeCCingOthers = in.readLong();
        physicalDamageTaken = in.readLong();
        teamId = in.readInt();
        championId = in.readInt();
        spell1Id = in.readInt();
        spell2Id = in.readInt();
        neutralMinionsKilledTeamJungle = in.readInt();
        totalPlayerScore = in.readInt();
        deaths = in.readInt();
        neutralMinionsKilledEnemyJungle = in.readInt();
        largestCriticalStrike = in.readInt();
        visionWardsBoughtInGame = in.readInt();
        largestKillingSpree = in.readInt();
        item1 = in.readInt();
        quadraKills = in.readInt();
        totalTimeCrowdControlDealt = in.readInt();
        longestTimeSpentLiving = in.readInt();
        wardsKilled = in.readInt();
        item2 = in.readInt();
        item3 = in.readInt();
        item0 = in.readInt();
        wardsPlaced = in.readInt();
        item4 = in.readInt();
        item5 = in.readInt();
        item6 = in.readInt();
        turretKills = in.readInt();
        tripleKills = in.readInt();
        champLevel = in.readInt();
        goldEarned = in.readInt();
        kills = in.readInt();
        doubleKills = in.readInt();
        assists = in.readInt();
        unrealKills = in.readInt();
        neutralMinionsKilled = in.readInt();
        objectivePlayerScore = in.readInt();
        combatPlayerScore = in.readInt();
        goldSpent = in.readInt();
        participantId = in.readInt();
        pentaKills = in.readInt();
        totalMinionsKilled = in.readInt();
        largestMultiKill = in.readInt();
        sightWardsBoughtInGame = in.readInt();
        totalUnitsHealed = in.readInt();
        inhibitorKills = in.readInt();
        totalScoreRank = in.readInt();
        killingSprees = in.readInt();
        win = in.readByte() != 0;
        firstTowerAssist = in.readByte() != 0;
        firstTowerKill = in.readByte() != 0;
        firstBloodAssist = in.readByte() != 0;
        firstInhibitorKill = in.readByte() != 0;
        firstInhibitorAssist = in.readByte() != 0;
        firstBloodKill = in.readByte() != 0;
        highestAchievedSeasonTier = in.readString();
        role = in.readString();
        lane = in.readString();
    }

    public static final Creator<ParticipantEntity> CREATOR = new Creator<ParticipantEntity>() {
        @Override
        public ParticipantEntity createFromParcel(Parcel in) {
            return new ParticipantEntity(in);
        }

        @Override
        public ParticipantEntity[] newArray(int size) {
            return new ParticipantEntity[size];
        }
    };

    public long getPhysicalDamageDealt() {
        return physicalDamageDealt;
    }

    public long getMagicDamageDealt() {
        return magicDamageDealt;
    }

    public long getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public long getMagicDamageDealtToChampions() {
        return magicDamageDealtToChampions;
    }

    public long getDamageDealtToObjectives() {
        return damageDealtToObjectives;
    }

    public long getVisionScore() {
        return visionScore;
    }

    public long getDamageSelfMitigated() {
        return damageSelfMitigated;
    }

    public long getMagicalDamageTaken() {
        return magicalDamageTaken;
    }

    public long getTrueDamageTaken() {
        return trueDamageTaken;
    }

    public long getDamageDealtToTurrets() {
        return damageDealtToTurrets;
    }

    public long getPhysicalDamageDealtToChampions() {
        return physicalDamageDealtToChampions;
    }

    public long getTrueDamageDealt() {
        return trueDamageDealt;
    }

    public long getTrueDamageDealtToChampions() {
        return trueDamageDealtToChampions;
    }

    public long getTotalHeal() {
        return totalHeal;
    }

    public long getTotalDamageDealtToChampions() {
        return totalDamageDealtToChampions;
    }

    public long getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public long getTimeCCingOthers() {
        return timeCCingOthers;
    }

    public long getPhysicalDamageTaken() {
        return physicalDamageTaken;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getChampionId() {
        return championId;
    }

    public int getSpell1Id() {
        return spell1Id;
    }

    public int getSpell2Id() {
        return spell2Id;
    }

    public int getNeutralMinionsKilledTeamJungle() {
        return neutralMinionsKilledTeamJungle;
    }

    public int getTotalPlayerScore() {
        return totalPlayerScore;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getNeutralMinionsKilledEnemyJungle() {
        return neutralMinionsKilledEnemyJungle;
    }

    public int getLargestCriticalStrike() {
        return largestCriticalStrike;
    }

    public int getVisionWardsBoughtInGame() {
        return visionWardsBoughtInGame;
    }

    public int getLargestKillingSpree() {
        return largestKillingSpree;
    }

    public int getItem1() {
        return item1;
    }

    public int getQuadraKills() {
        return quadraKills;
    }

    public int getTotalTimeCrowdControlDealt() {
        return totalTimeCrowdControlDealt;
    }

    public int getLongestTimeSpentLiving() {
        return longestTimeSpentLiving;
    }

    public int getWardsKilled() {
        return wardsKilled;
    }

    public int getItem2() {
        return item2;
    }

    public int getItem3() {
        return item3;
    }

    public int getItem0() {
        return item0;
    }

    public int getWardsPlaced() {
        return wardsPlaced;
    }

    public int getItem4() {
        return item4;
    }

    public int getItem5() {
        return item5;
    }

    public int getItem6() {
        return item6;
    }

    public int getTurretKills() {
        return turretKills;
    }

    public int getTripleKills() {
        return tripleKills;
    }

    public int getChampLevel() {
        return champLevel;
    }

    public int getGoldEarned() {
        return goldEarned;
    }

    public int getKills() {
        return kills;
    }

    public int getDoubleKills() {
        return doubleKills;
    }

    public int getAssists() {
        return assists;
    }

    public int getUnrealKills() {
        return unrealKills;
    }

    public int getNeutralMinionsKilled() {
        return neutralMinionsKilled;
    }

    public int getObjectivePlayerScore() {
        return objectivePlayerScore;
    }

    public int getCombatPlayerScore() {
        return combatPlayerScore;
    }

    public int getGoldSpent() {
        return goldSpent;
    }

    public int getParticipantId() {
        return participantId;
    }

    public int getPentaKills() {
        return pentaKills;
    }

    public int getTotalMinionsKilled() {
        return totalMinionsKilled;
    }

    public int getLargestMultiKill() {
        return largestMultiKill;
    }

    public int getSightWardsBoughtInGame() {
        return sightWardsBoughtInGame;
    }

    public int getTotalUnitsHealed() {
        return totalUnitsHealed;
    }

    public int getInhibitorKills() {
        return inhibitorKills;
    }

    public int getTotalScoreRank() {
        return totalScoreRank;
    }

    public int getKillingSprees() {
        return killingSprees;
    }

    public boolean isWin() {
        return win;
    }

    public boolean isFirstTowerAssist() {
        return firstTowerAssist;
    }

    public boolean isFirstTowerKill() {
        return firstTowerKill;
    }

    public boolean isFirstBloodAssist() {
        return firstBloodAssist;
    }

    public boolean isFirstInhibitorKill() {
        return firstInhibitorKill;
    }

    public boolean isFirstInhibitorAssist() {
        return firstInhibitorAssist;
    }

    public boolean isFirstBloodKill() {
        return firstBloodKill;
    }

    public String getHighestAchievedSeasonTier() {
        return highestAchievedSeasonTier;
    }

    public String getRole() {
        return role;
    }

    public String getLane() {
        return lane;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(physicalDamageDealt);
        dest.writeLong(magicDamageDealt);
        dest.writeLong(totalDamageDealt);
        dest.writeLong(magicDamageDealtToChampions);
        dest.writeLong(damageDealtToObjectives);
        dest.writeLong(visionScore);
        dest.writeLong(damageSelfMitigated);
        dest.writeLong(magicalDamageTaken);
        dest.writeLong(trueDamageTaken);
        dest.writeLong(damageDealtToTurrets);
        dest.writeLong(physicalDamageDealtToChampions);
        dest.writeLong(trueDamageDealt);
        dest.writeLong(trueDamageDealtToChampions);
        dest.writeLong(totalHeal);
        dest.writeLong(totalDamageDealtToChampions);
        dest.writeLong(totalDamageTaken);
        dest.writeLong(timeCCingOthers);
        dest.writeLong(physicalDamageTaken);
        dest.writeInt(teamId);
        dest.writeInt(championId);
        dest.writeInt(spell1Id);
        dest.writeInt(spell2Id);
        dest.writeInt(neutralMinionsKilledTeamJungle);
        dest.writeInt(totalPlayerScore);
        dest.writeInt(deaths);
        dest.writeInt(neutralMinionsKilledEnemyJungle);
        dest.writeInt(largestCriticalStrike);
        dest.writeInt(visionWardsBoughtInGame);
        dest.writeInt(largestKillingSpree);
        dest.writeInt(item1);
        dest.writeInt(quadraKills);
        dest.writeInt(totalTimeCrowdControlDealt);
        dest.writeInt(longestTimeSpentLiving);
        dest.writeInt(wardsKilled);
        dest.writeInt(item2);
        dest.writeInt(item3);
        dest.writeInt(item0);
        dest.writeInt(wardsPlaced);
        dest.writeInt(item4);
        dest.writeInt(item5);
        dest.writeInt(item6);
        dest.writeInt(turretKills);
        dest.writeInt(tripleKills);
        dest.writeInt(champLevel);
        dest.writeInt(goldEarned);
        dest.writeInt(kills);
        dest.writeInt(doubleKills);
        dest.writeInt(assists);
        dest.writeInt(unrealKills);
        dest.writeInt(neutralMinionsKilled);
        dest.writeInt(objectivePlayerScore);
        dest.writeInt(combatPlayerScore);
        dest.writeInt(goldSpent);
        dest.writeInt(participantId);
        dest.writeInt(pentaKills);
        dest.writeInt(totalMinionsKilled);
        dest.writeInt(largestMultiKill);
        dest.writeInt(sightWardsBoughtInGame);
        dest.writeInt(totalUnitsHealed);
        dest.writeInt(inhibitorKills);
        dest.writeInt(totalScoreRank);
        dest.writeInt(killingSprees);
        dest.writeByte((byte) (win ? 1 : 0));
        dest.writeByte((byte) (firstTowerAssist ? 1 : 0));
        dest.writeByte((byte) (firstTowerKill ? 1 : 0));
        dest.writeByte((byte) (firstBloodAssist ? 1 : 0));
        dest.writeByte((byte) (firstInhibitorKill ? 1 : 0));
        dest.writeByte((byte) (firstInhibitorAssist ? 1 : 0));
        dest.writeByte((byte) (firstBloodKill ? 1 : 0));
        dest.writeString(highestAchievedSeasonTier);
        dest.writeString(role);
        dest.writeString(lane);
    }
}
