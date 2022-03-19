package com.task.football_league.data.datasource

import com.task.football_league.domain.model.competition_details.CompetitionDetails
import com.task.football_league.domain.model.competitions.Competitions_Response
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.model.team_players.TeamPlayers
import io.reactivex.Single
import retrofit2.Response

interface RemoteDataSource {
    fun getCompetitions(Token: String): Single<Response<Competitions_Response?>?>?
    fun getTeamById(Token: String, id: String): Single<Response<CompetitionTeam?>?>?
    fun getCompetitionsById(token: String, id: String): Single<Response<CompetitionDetails?>?>?
    fun getTeamPlayers(token: String, id: String): Single<Response<TeamPlayers?>?>?

}