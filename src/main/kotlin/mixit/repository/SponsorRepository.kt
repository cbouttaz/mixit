package mixit.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mixit.data.dto.SponsorDataDto
import mixit.model.Session
import mixit.support.getEntityInformation
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.support.ReactiveMongoRepositoryFactory
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

class SponsorRepository(val db: ReactiveMongoTemplate, f: ReactiveMongoRepositoryFactory) :
        SimpleReactiveMongoRepository<Session, String>(f.getEntityInformation(Session::class), db) {

    fun initData() {
        deleteAll().block()

        val years = listOf(12, 13, 14, 15, 16)
        years.forEach { year -> saveSponsorsByYear(year) }
    }

    /**
     * Loads data from the json session files
     */
    private fun saveSponsorsByYear(year: Int) {
        val file = ClassPathResource("data/sponsor/sponsor_mixit$year.json")

        val objectMapper: ObjectMapper = Jackson2ObjectMapperBuilder.json().build()
        var sponsors: List<SponsorDataDto> = objectMapper.readValue(file.file)

        sponsors
                .map { sponsor -> sponsor.toSponsor() }
                .forEach { sponsor -> save(sponsor).block() }
    }
}
