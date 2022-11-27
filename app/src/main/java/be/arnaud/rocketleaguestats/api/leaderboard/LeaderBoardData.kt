package be.arnaud.rocketleaguestats.api.leaderboard

data class LeaderBoardData(
    var id: String,
    var items: List<LeaderBoardItem>
) {
}