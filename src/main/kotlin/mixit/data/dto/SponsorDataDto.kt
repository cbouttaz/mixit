package mixit.data.dto

import mixit.model.*

data class SponsorDataDto(
        val idMember: Int,
        val login: String,
        val firstname: String?,
        val lastname: String?,
        val logo: String?,
        val hash: String?,
        val shortDescription: String?,
        var level: List<LevelDataDto>? = emptyList()
){
    fun toSponsor(): Sponsor {
        return Sponsor(
                login,
                firstname,
                lastname,
                logo,
                hash,
                shortDescription,
                level
        )
    }
}
