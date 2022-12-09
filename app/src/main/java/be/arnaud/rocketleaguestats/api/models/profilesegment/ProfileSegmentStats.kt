package be.arnaud.rocketleaguestats.api.models.profilesegment

data class ProfileSegmentStats(
    val tier: ProfileSegmentStat,
    val division: ProfileSegmentStat,
    val matchesPlayed: ProfileSegmentStat,
    val winStreak: ProfileSegmentStat,
    val rating: ProfileSegmentStat
) {

}