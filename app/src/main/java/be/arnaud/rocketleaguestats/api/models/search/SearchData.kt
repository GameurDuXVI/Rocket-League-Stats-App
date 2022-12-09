package be.arnaud.rocketleaguestats.api.models.search

data class SearchData(
    val platformId: Int,
    val platformSlug: String,
    val platformUserIdentifier: String,
    val platformUserId: String?,
    val platformUserHandle: String,
    val avatarUrl: String?,
    val status: String?,
    val additionalParameters: String?
) {

}