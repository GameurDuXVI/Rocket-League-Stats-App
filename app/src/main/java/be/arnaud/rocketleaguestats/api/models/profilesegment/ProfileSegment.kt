package be.arnaud.rocketleaguestats.api.models.profilesegment

data class ProfileSegment(
    val type: String,
    val attributes: ProfileSegmentAttribute?,
    val stats: ProfileSegmentStats
) {

}