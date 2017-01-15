package mixit.model

import mixit.data.dto.LevelDataDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Sponsor(
        val login: String? = null,
        val firstname: String? = null,
        val lastname: String? = null,
        val logo: String? = null,
        val hash: String? = null,
        val shortDescription: String? = null,
        var level: List<LevelDataDto>? = null,
        @Id val id: String? = null
)

