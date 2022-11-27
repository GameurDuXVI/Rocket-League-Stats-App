package be.arnaud.rocketleaguestats.api.leaderboard

data class LeaderBoardOwner(
    var id: String,
    var type: String,
    var metadata: LeaderBoardOwnerMetaData
) {
}