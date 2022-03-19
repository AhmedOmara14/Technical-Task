package com.task.football_league.data.datasource


import com.task.football_league.domain.model.competitions.Competitions_Response
import com.task.football_league.data.remote.ApiService
import com.task.football_league.domain.model.competition_details.CompetitionDetails
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.model.team_players.TeamPlayers
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RemoteDataSource {
    override fun getCompetitions(Token: String): Single<Response<Competitions_Response?>?>? {
        return apiService.getCompetitions(Token)
    }

    override fun getTeamById(
        Token: String,
        id: String
    ): Single<Response<CompetitionTeam?>?>? {
        return apiService.getTeamById(Token, id)
    }

    override fun getCompetitionsById(
        token: String,
        id: String
    ): Single<Response<CompetitionDetails?>?>? {
        return apiService.getCompetitionsById(token, id)
    }

    override fun getTeamPlayers(token: String, id: String): Single<Response<TeamPlayers?>?>? {
        return apiService.getTeamPlayers(token, id)
    }

}