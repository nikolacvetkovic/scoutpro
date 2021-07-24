package xyz.riocode.scoutpro.enums;

public enum PlayerSkill {

    SCISSORS_FEINT ("Scissors Feint"),
    DOUBLE_TOUCH ("Double Touch"),
    FLIP_FLAP ("Flip Flap"),
    MARSEILLE_TURN ("Marseille Turn"),
    SOMBRERO ("Sombrero"),
    CROSS_OVER_TURN ("Cross Over Turn"),
    CUT_BEHIND_TURN ("Cut Behind & Turn"),
    SCOTCH_MOVE ("Scotch Move"),
    STEP_ON_SKILL ("Step On Skill control"),
    HEADING ("Heading"),
    LONG_RANGE_DRIVE ("Long Range Drive"),
    CHIP_SHOT ("Chip shot control"),
    KNUCKLE_SHOT ("Knuckle Shot"),
    DIPPING_SHOT ("Dipping Shot"),
    RISING_SHOTS ("Rising Shots"),
    ACROBATIC_FINISHING ("Acrobatic Finishing"),
    HEEL_TRICK ("Heel Trick"),
    FIRST_TIME_SHOT ("First-time Shot"),
    ONE_TOUCH_PASS ("One-touch Pass"),
    WEIGHTED_PASS ("Weighted Pass"),
    PINPOINT_CROSSING ("Pinpoint Crossing"),
    OUTSIDE_CURLER ("Outside Curler"),
    RABONA ("Rabona"),
    NO_LOOK_PASS ("No Look Pass"),
    LOW_LOFTED_PASS ("Low Lofted Pass"),
    GK_LOW_PUNT ("GK Low Punt"),
    GK_HIGH_PUNT ("GK High Punt"),
    LONG_THROW ("Long Throw"),
    GK_LONG_THROW ("GK Long Throw"),
    PENALTY_SPECIALIST ("Penalty Specialist"),
    GK_PENALTY_SAVER ("GK Penalty Saver"),
    MALICIA ("Malicia"),
    MAN_MARKING ("Man Marking"),
    TRACK_BACK ("Track Back"),
    INTERCEPTION ("Interception"),
    ACROBATIC_CLEAR ("Acrobatic Clear"),
    CAPTAINCY ("Captaincy"),
    SUPER_SUB ("Super-sub"),
    FIGHTING_SPIRIT ("Fighting Spirit");

    private String skill;

    PlayerSkill(String skill){
        this.skill = skill;
    }
}
