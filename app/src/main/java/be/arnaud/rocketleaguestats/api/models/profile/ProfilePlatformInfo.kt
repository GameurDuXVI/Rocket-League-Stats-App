package be.arnaud.rocketleaguestats.api.models.profile

data class ProfilePlatformInfo(
    val platformSlug: String,
    val platformUserHandle: String,
    val platformUserIdentifier: String,
    val avatarUrl: String?
) {

}