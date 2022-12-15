package be.arnaud.rocketleaguestats.api.models.profile

import be.arnaud.rocketleaguestats.api.models.profilesegment.ProfileSegmentAttribute

data class ProfileSegment(
    val type: String,
    val attributes: ProfileSegmentAttribute?,
    val stats: ProfileSegmentStats
) {

}