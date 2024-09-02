package com.compose.experiment.kitsu.data.remote.dto

data class TrendingAnimeListDto(
    val data: List<AnimeDataDto>
)

data class AnimeDataDto(
    val attributes: AttributesDto,
    val id: String,
    val links: LinksDto,
    val relationships: Relationships,
    val type: String
)

data class AttributesDto(
    val updatedAt: String? = null,
    val createdAt: String? = null,
    val slug: String? = null,
    val synopsis: String? = null,
    val description: String? = null,
    val coverImageTopOffset: Int? = null,
    val titles: TitlesDto? = null,
    val canonicalTitle: String? = null,
    val abbreviatedTitles: List<String>? =null,
    val averageRating: String? = null,
    val ratingFrequencies: Map<String,String>? = null,
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
    val chapterCount: Int? = null,
    val tba: String? = null,
    val volumeCount: Int? = null,
    val serialization: String? = null,
    val mangaType: String? = null,
)

data class TitlesDto(
    val en: String? = null,
    val en_jp: String? = null,
    val ja_jp: String? = null
)

data class PosterImageDto(
    val large: String? = null,
    val medium: String? = null,
    val meta: MetaDto? = null,
    val original: String? = null,
    val small: String? = null,
    val tiny: String? = null
)

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
)

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