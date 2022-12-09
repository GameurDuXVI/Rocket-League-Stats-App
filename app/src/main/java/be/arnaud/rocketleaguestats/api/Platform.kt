package be.arnaud.rocketleaguestats.api

import be.arnaud.rocketleaguestats.R

enum class Platform(
    val platformId: Int,
    val displayName: String,
    val typeName: String,
    val drawableResource: Int
) {
    ALL(-1, "", "all", -1),
    XBOX(1, "Xbox", "xbl", R.drawable.ic_xbox),
    PLAY_STATION(2, "PlayStation", "psn", R.drawable.ic_play_station),
    STEAM(3, "Steam", "steam", R.drawable.ic_steam),
    EPIC_GAMES(8, "Epic Games", "epic", R.drawable.ic_epic_games),
    SWITCH(20, "Switch", "switch", R.drawable.ic_switch);

    companion object {
        fun fromType(type: String?): Platform? {
            for (platform in values()) {
                if (platform.typeName == type) {
                    return platform
                }
            }
            return null
        }
    }
}