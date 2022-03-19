package com.task.football_league.domain.model.competitions

data class Competition(
    var area: Area?=null,
    var code: String?=null,
    var currentSeason: CurrentSeason?=null,
    var emblemUrl: Any?=null,
    var id: Int?=null,
    var lastUpdated: String?=null,
    var name: String?=null,
    var numberOfAvailableSeasons: Int?=null,
    var plan: String?=null,
    var isAvailable: Boolean?=null
)