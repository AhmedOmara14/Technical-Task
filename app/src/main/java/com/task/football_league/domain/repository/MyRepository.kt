package com.task.football_league.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.football_league.common.Resource
import com.task.football_league.domain.model.competition_details.CompetitionDetails
import com.task.football_league.domain.model.competitions.Competitions_Response
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.model.team_players.TeamPlayers
import io.reactivex.Single
import retrofit2.Response


interface MyRepository {
    fun getCompetitions(Token: String): LiveData<Resource<Competitions_Response?>?>?
    fun getTeamById(Token: String, id: String): LiveData<Resource<CompetitionTeam?>?>?
    fun getCompetitionsById(token: String, id: String): LiveData<Resource<CompetitionDetails?>?>?
    fun getTeamPlayers(token: String, id: String): LiveData<Resource<TeamPlayers?>?>?

    fun getFavCompetitions(): MutableLiveData<List<CompetitionTeam>>
    fun insertFavCompetitions(competitionTeam: CompetitionTeam)
}