package be.arnaud.rocketleaguestats.api.leaderboard

data class LeaderBoardItem(
    var id: String,
    var owner: LeaderBoardOwner,
    var value: Double,
    var displayValue: String,
    var rank: Int,
    var iconUrl: String
) {
}