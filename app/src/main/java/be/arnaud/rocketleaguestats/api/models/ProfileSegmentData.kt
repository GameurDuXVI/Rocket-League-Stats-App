package be.arnaud.rocketleaguestats.api.models

import be.arnaud.rocketleaguestats.api.models.profilesegment.ProfileRankingSegment

data class ProfileSegmentData(
    val data: List<ProfileRankingSegment>
) {

}