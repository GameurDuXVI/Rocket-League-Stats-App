package be.arnaud.rocketleaguestats.api

enum class Rank(val id: Int) {
    NONE(0),
    BRONZE_1(1),
    BRONZE_2(2),
    BRONZE_3(3),
    SILVER_1(4),
    SILVER_2(5),
    SILVER_3(6),
    GOLD_1(7),
    GOLD_2(8),
    GOLD_3(9),
    PLATINUM_1(10),
    PLATINUM_2(11),
    PLATINUM_3(12),
    DIAMOND_1(13),
    DIAMOND_2(14),
    DIAMOND_3(15),
    CHAMPION_1(16),
    CHAMPION_2(17),
    CHAMPION_3(18),
    GRAND_CHAMPION_1(19),
    GRAND_CHAMPION_2(20),
    GRAND_CHAMPION_3(21),
    SUPERSONIC_LEGEND(22);

    companion object {
        fun fromId(id: Int?): Rank {
            for (rank in values()) {
                if (rank.id == id) {
                    return rank
                }
            }
            return NONE
        }
    }
}