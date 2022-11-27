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
        GOALS("Goals", R.string.goals),
        WINS("Wins", R.string.wins),
        MVP("MVPs", R.string.mvp),
        SAVES("Saves", R.string.saves),
        ASSISTS("Assists", R.string.assists),
        SHOTS("Shots", R.string.shots),
        GOAL_SHOT_RATIO("GoalShotRatio", R.string.goal_shot_ratio),
        SCORE("Score", R.string.trn_score),
        SEASON_REWARD_LEVEL("SeasonRewardLevel", R.string.season_reward_level),
        SEASON_REWARD_WINS("SeasonRewardWins", R.string.season_reward_wins),
        TRN_RATING("TRNRating", R.string.trn_rating)
    }

    enum class Type(
        val typeName: String
    ) {
        STATS("stats"),
        PLAYLIST("playlist")
    }
}