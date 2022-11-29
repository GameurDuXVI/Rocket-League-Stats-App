package be.arnaud.rocketleaguestats.api

import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.leaderboard.LeaderBoardData

data class LeaderBoard(
    var data: LeaderBoardData
) {

    enum class Board(
        val typeName: String,
        val resourceId: Int
    ) {
        NONE("", -1),
        GOALS("Goals", R.string.stats_goals),
        WINS("Wins", R.string.stats_wins),
        MVP("MVPs", R.string.stats_mvp),
        SAVES("Saves", R.string.stats_saves),
        ASSISTS("Assists", R.string.stats_assists),
        SHOTS("Shots", R.string.stats_shots),
        GOAL_SHOT_RATIO("GoalShotRatio", R.string.stats_goal_shot_ratio),
        SEASON_REWARD_LEVEL("SeasonRewardLevel", R.string.stats_season_reward_level),
        SEASON_REWARD_WINS("SeasonRewardWins", R.string.stats_season_reward_wins),
        TRN_SCORE("Score", R.string.stats_trn_score),
        TRN_RATING("TRNRating", R.string.stats_trn_rating)
    }

    enum class Type(
        val typeName: String
    ) {
        STATS("stats"),
        PLAYLIST("playlist")
    }
}