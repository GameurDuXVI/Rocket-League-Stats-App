package be.arnaud.rocketleaguestats.api.models.profile

data class ProfileData(
    val platformInfo: ProfilePlatformInfo,
    val userInfo: ProfileUserInfo,
    val segments: List<ProfileSegment>
) {

}