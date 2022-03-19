package com.task.football_league.domain.model.team_players

data class TeamPlayers(
    val activeCompetitions: List<ActiveCompetition>,
    val address: String,
    val area: AreaX,
    val clubColors: String,
    val crestUrl: String,
    val email: String,
    val founded: Int,
    val id: Int,
    val lastUpdated: String,
    val name: String,
    val phone: String,
    val shortName: String,
    val squad: List<Squad>,
    val tla: String,
    val venue: String,
    val website: String
)