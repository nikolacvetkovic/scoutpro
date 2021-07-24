package xyz.riocode.scoutpro.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PlayerDashboardDTO {

    private String id;
    private String playerName;
    private boolean myPlayer;

    private String tmCurrentValue;
    private String tmValueLastChanged;
    private String tmValueLastCheck;

    private String age;
    private String nationalTeam;
    private String clubTeam;
    private String contractUntil;

    private String pesDbPlayerName;
    private String pesDbTeamName;
    private String foot;
    private String weekCondition;
    private String position;
    private Set<String> otherStrongPositions;
    private Set<String> otherWeakPositions;
    private int offensiveAwareness;
    private int ballControl;
    private int dribbling;
    private int tightPossession;
    private int lowPass;
    private int loftedPass;
    private int finishing;
    private int heading;
    private int placeKicking;
    private int curl;
    private int speed;
    private int acceleration;
    private int kickingPower;
    private int jump;
    private int physicalContact;
    private int balance;
    private int stamina;
    private int defensiveAwareness;
    private int ballWinning;
    private int aggression;
    private int gkAwareness;
    private int gkCatching;
    private int gkClearing;
    private int gkReflexes;
    private int gkReach;
    private int weakFootUsage;
    private int weakFootAccuracy;
    private int form;
    private int injuryResistance;
    private int overallRating;
    private String playingStyle;
    private Set<String> playerSkills;
    private Set<String> comPlayingStyles;
    private String pesDbLastCheck;

    private String psmlTeam;
    private String psmlValue;
    private String psmlLastTransferDate;
    private String psmlLastTransferFromTeam;
    private String psmlLastTransferToTeam;
    private String psmlLastTransferFee;
    private String psmlLastCheck;

    private String totalStartedApps;
    private String totalMins;
    private String totalGoals;
    private String totalAssists;
    private String averageShotsPerGame;
    private String averagePassSuccess;
    private String averageAerialsWon;
    private String totalManOfTheMatch;
    private String averageRating;
    private String statisticsLastCheck;
}
