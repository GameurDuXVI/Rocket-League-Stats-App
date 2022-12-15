package be.arnaud.rocketleaguestats.api.models.profile

data class ProfileUserInfo(
    val isPremium: Boolean,
    val isVerified: Boolean,
    val isInfluencer: Boolean,
    val isPartner: Boolean,
    val countryCode: String?,
    val customAvatarUrl: String?,
    val customHeroUrl: String?,
    val socialAccounts: List<String>,
    val pageviews: Int,
    val isSuspicious: Boolean?
) {

}