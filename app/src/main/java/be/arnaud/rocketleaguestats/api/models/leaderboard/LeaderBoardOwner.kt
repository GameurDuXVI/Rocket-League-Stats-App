package be.arnaud.rocketleaguestats.api.models.leaderboard

data class LeaderBoardOwner(
    var id: String,
    var type: String,
    var metadata: LeaderBoardOwnerMetaData
) {
}