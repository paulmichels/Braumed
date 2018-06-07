package workout.leagueofworkout.entity;

public class ParticipantIdentityEntity {
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
}
