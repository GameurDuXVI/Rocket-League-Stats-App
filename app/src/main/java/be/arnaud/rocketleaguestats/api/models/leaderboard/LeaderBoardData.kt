package be.arnaud.rocketleaguestats.api.models.leaderboard

data class LeaderBoardData(
    var id: String,
    var items: List<LeaderBoardItem>
) {
}