package be.arnaud.rocketleaguestats.api.models.profilesegment

data class ProfileSegmentRankingStats(
    val tier: ProfileSegmentStat,
    val division: ProfileSegmentStat,
    val matchesPlayed: ProfileSegmentStat,
    val winStreak: ProfileSegmentStat,
    val rating: ProfileSegmentStat
) {

}