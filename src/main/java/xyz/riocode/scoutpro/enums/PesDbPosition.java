package xyz.riocode.scoutpro.enums;

public enum PesDbPosition {

    GOALKEEPER("GK"),
    CENTRAL_DEFENDER("CB"),
    RIGHT_BACK("RB"),
    LEFT_BACK("LB"),
    DEFENSIVE_MIDFIELDER("DMF"),
    CENTRE_MIDFIELDER("CMF"),
    RIGHT_MIDFIELDER("RMF"),
    LEFT_MIDFIELDER("LMF"),
    ATTACKING_MIDFIELDER("AMF"),
    LEFT_WING_FORWARD("LWF"),
    RIGHT_WING_FORWARD("RWF"),
    SECOND_STRIKER("SS"),
    CENTRE_FORWARD("CF");


    private String position;

    PesDbPosition(String position){
        this.position = position;
    }
}
