package be.arnaud.rocketleaguestats.api.models.profile

import be.arnaud.rocketleaguestats.api.models.profilesegment.ProfileSegment

data class ProfileData(
    val platformInfo: ProfilePlatformInfo,
    val userInfo: ProfileUserInfo,
    val segments: List<ProfileSegment>
) {

}