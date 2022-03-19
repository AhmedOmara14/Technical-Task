package com.task.football_league.data.remote

import com.task.football_league.domain.model.competition_details.CompetitionDetails
import com.task.football_league.domain.model.competitions.Competitions_Response
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.model.team_players.TeamPlayers
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("competitions")
    fun getCompetitions(@Header("X-Auth-Token") Token: String): Single<Response<Competitions_Response?>?>?

    @GET("competitions/{id}/teams")
    fun getTeamById(
        @Header("X-Auth-Token") Token: String,
        @Path("id") id: String
    ): Single<Response<CompetitionTeam?>?>?

    @GET("competitions/{id}")
    fun getCompetitionsById(
        @Header("X-Auth-Token") Token: String,
        @Path("id") id: String
    ): Single<Response<CompetitionDetails?>?>?

    @GET("teams/{id}")
    fun getTeamPlayers(
        @Header("X-Auth-Token") Token: String,
        @Path("id") id: String
    ): Single<Response<TeamPlayers?>?>?

}