package xyz.riocode.scoutpro.enums;

public enum PlayingStyle {

    GOAL_POACHER ("Goal Poacher"),
    DUMMY_RUNNER ("Dummy Runner"),
    FOX_IN_THE_BOX ("Fox in the Box"),
    TARGET_MAN ("Target Man"),
    CREATIVE_PLAYMAKER ("Creative Playmaker"),
    PROFILIC_WINGER ("Profilic Winger"),
    ROAMING_FLANK ("Roaming Flank"),
    CROSS_SPECIALIST ("Cross Specialist"),
    CLASSIC_NO_10 ("Classic No. 10"),
    HOLE_PLAYER ("Hole Player"),
    BOX_TO_BOX ("Box-to-Box"),
    THE_DESTROYER ("The Destroyer"),
    ORCHESTRATOR ("Orchestrator"),
    ANCHOR_MAN ("Anchor Man"),
    BUILD_UP ("Build Up"),
    OFFENSIVE_FULL_BACK ("Offensive Full-back"),
    FULL_BACK_FINISHER ("Full-back Finisher"),
    DEFENSIVE_FULL_BACK ("Defensive Full-back"),
    EXTRA_FRONTMAN ("Extra Frontman"),
    OFFENSIVE_GOALKEEPER ("Offensive Goalkeeper"),
    DEFENSIVE_GOALKEEPER ("Defensive Goalkeeper");

    private String style;

    PlayingStyle(String style){
        this.style = style;
    }
}
