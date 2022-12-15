package be.arnaud.rocketleaguestats.api.models.profile

import be.arnaud.rocketleaguestats.api.models.profilesegment.ProfileSegmentStat

data class ProfileSegmentStats(
    val wins: ProfileSegmentStat,
    val goals: ProfileSegmentStat,
    val mVPs: ProfileSegmentStat,
    val saves: ProfileSegmentStat,
    val assists: ProfileSegmentStat,
    val shots: ProfileSegmentStat,
    val goalShotRatio: ProfileSegmentStat,
    val score: ProfileSegmentStat,
    val seasonRewardLevel: ProfileSegmentStat,
    val seasonRewardWins: ProfileSegmentStat,
    val tRNRating: ProfileSegmentStat
) {

}