package com.compose.experiment.kitsu.data.remote.dto

import com.compose.experiment.kitsu.domain.model.AnimeData
import com.compose.experiment.kitsu.domain.model.Attributes
import com.compose.experiment.kitsu.domain.model.CoverImage
import com.compose.experiment.kitsu.domain.model.PosterImage
import com.compose.experiment.kitsu.domain.model.Titles

data class TrendingAnimeListResponseDto(
    val data: List<AnimeDataDto>
)

data class AnimeResponseDto(
    val data: AnimeDataDto
)

data class AnimeDataDto(
    val attributes: AttributesDto,
    val id: String,
    val links: LinksDto,
    val relationships: Relationships,
    val type: String
) {
    fun toModel(): AnimeData = AnimeData(id = id, attributes = attributes.toModel())
}

data class AttributesDto(
    val updatedAt: String? = null,
    val createdAt: String? = null,
    val slug: String? = null,
    val synopsis: String? = null,
    val description: String? = null,
    val coverImageTopOffset: Int? = null,
    val titles: TitlesDto? = null,
    val canonicalTitle: String? = null,
    val abbreviatedTitles: List<String>? = null,
    val averageRating: String? = null,
    val ratingFrequencies: Map<String, String>? = null,
    val userCount: Int? = null,
    val favoritesCount: Int? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val popularityRank: Int? = null,
    val ratingRank: Int? = null,
    val ageRating: String? = null,
    val ageRatingGuide: String? = null,
    val subtype: String? = null,
    val status: String? = null,
    val posterImage: PosterImageDto? = null,
    val coverImage: CoverImageDto? = null,
    val episodeCount: Int? = null,
    val episodeLength: Int? = null,
    val youtubeVideoId: String? = null,
    val showType: String? = null,
    val nsfw: Boolean? = null
) {
    fun toModel(): Attributes =
        Attributes(
            createdAt = createdAt,
            updatedAt = updatedAt,
            slug = slug,
            synopsis = synopsis,
            titles = titles?.toModel(),
            canonicalTitle = canonicalTitle,
            abbreviatedTitles = abbreviatedTitles,
            ageRating = ageRatingGuide,
            coverImage = coverImage?.toModel(),
            subtype = subtype,
            posterImage = posterImage?.toModel(),
            episodeCount = episodeCount,
            episodeLength = episodeLength,
            showType = showType,
            ageRatingGuide = ageRatingGuide,
            favoritesCount = favoritesCount,
            popularityRank = popularityRank,
            status = status,
            endDate = endDate,
            startDate = startDate,
            userCount = userCount,
            averageRating = averageRating,
            ratingRank = ratingRank
        )
}

data class TitlesDto(
    val en: String? = null,
    val en_jp: String? = null,
    val ja_jp: String? = null
) {
    fun toModel(): Titles = Titles(en = en)
}

data class PosterImageDto(
    val large: String? = null,
    val medium: String? = null,
    val meta: MetaDto? = null,
    val original: String? = null,
    val small: String? = null,
    val tiny: String? = null
) {
    fun toModel(): PosterImage =
        PosterImage(tiny = tiny, small = small, medium = medium, large = large, original = original)
}

data class MetaDto(
    val dimensions: DimensionsDto? = null
)

data class DimensionsDto(
    val large: SizeDto? = null,
    val medium: SizeDto? = null,
    val small: SizeDto? = null,
    val tiny: SizeDto? = null
)

data class SizeDto(
    val width: Int? = null,
    val height: Int? = null
)

data class CoverImageDto(
    val large: String? = null,
    val meta: MetaDto? = null,
    val original: String? = null,
    val small: String? = null,
    val tiny: String? = null
) {
    fun toModel(): CoverImage =
        CoverImage(tiny = tiny, small = small, large = large, original = original)
}

data class Relationships(
    val castings: RelationDto? = null,
    val categories: RelationDto? = null,
    val chapters: RelationDto? = null,
    val genres: RelationDto? = null,
    val installments: RelationDto? = null,
    val mangaCharacters: RelationDto? = null,
    val mangaStaff: RelationDto? = null,
    val mappings: RelationDto? = null,
    val mediaRelationships: RelationDto? = null,
    val reviews: RelationDto? = null
)

data class RelationDto(
    val links: RelationLinksDto? = null
)

data class LinksDto(
    val self: String? = null
)

data class RelationLinksDto(
    val self: String? = null,
    val related: String? = null
)