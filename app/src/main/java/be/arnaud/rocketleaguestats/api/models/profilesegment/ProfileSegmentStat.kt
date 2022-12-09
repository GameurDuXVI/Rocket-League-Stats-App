package be.arnaud.rocketleaguestats.api.models.profilesegment

data class ProfileSegmentStat(
    val rank: Long?,
    val percentile: Double?,
    val displayName: String,
    val displayCategory: String,
    val category: String,
    val metadata: ProfileSegmentStatMetaData,
    val value: Int
) {

}