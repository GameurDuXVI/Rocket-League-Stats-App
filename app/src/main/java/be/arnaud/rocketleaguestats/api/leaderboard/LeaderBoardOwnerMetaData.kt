package be.arnaud.rocketleaguestats.api.leaderboard

data class LeaderBoardOwnerMetaData(
    var platformId: Int,
    var platformSlug: String,
    var platformUserHandle: String,
    var platformUserIdentifier: String,
    var countryCode: String?,
    var avatarUrl: String,
    var isPremium: Boolean,
    var twitch: String?,
    var twitter: String?,
    var additionalIcon: LeaderBoardOwnerMetaDataAdditionalIcon?
) {
}