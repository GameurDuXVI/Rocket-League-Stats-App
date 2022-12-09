package be.arnaud.rocketleaguestats.api

import be.arnaud.rocketleaguestats.R

enum class Rank(val id: Int, val drawableId: Int) {
    NONE(0, R.drawable.rank0),
    BRONZE_1(1, R.drawable.rank1),
    BRONZE_2(2, R.drawable.rank2),
    BRONZE_3(3, R.drawable.rank3),
    SILVER_1(4, R.drawable.rank4),
    SILVER_2(5, R.drawable.rank5),
    SILVER_3(6, R.drawable.rank6),
    GOLD_1(7, R.drawable.rank7),
    GOLD_2(8, R.drawable.rank8),
    GOLD_3(9, R.drawable.rank9),
    PLATINUM_1(10, R.drawable.rank10),
    PLATINUM_2(11, R.drawable.rank11),
    PLATINUM_3(12, R.drawable.rank12),
    DIAMOND_1(13, R.drawable.rank13),
    DIAMOND_2(14, R.drawable.rank14),
    DIAMOND_3(15, R.drawable.rank15),
    CHAMPION_1(16, R.drawable.rank16),
    CHAMPION_2(17, R.drawable.rank17),
    CHAMPION_3(18, R.drawable.rank18),
    GRAND_CHAMPION_1(19, R.drawable.rank19),
    GRAND_CHAMPION_2(20, R.drawable.rank20),
    GRAND_CHAMPION_3(21, R.drawable.rank21),
    SUPERSONIC_LEGEND(22, R.drawable.rank22);

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