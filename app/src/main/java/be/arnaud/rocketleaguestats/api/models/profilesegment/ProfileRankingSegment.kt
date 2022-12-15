package be.arnaud.rocketleaguestats.api.models.profilesegment

data class ProfileRankingSegment(
    val type: String,
    val attributes: ProfileSegmentAttribute?,
    val stats: ProfileSegmentRankingStats
) {

}