package com.task.football_league.presentation.all_competitions

import android.os.AsyncTask
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.football_league.common.Resource
import com.task.football_league.domain.model.competitions.Competitions_Response
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.repository.MyRepository
import io.reactivex.Single
import retrofit2.Response


class AllCompetitionsViewModel @ViewModelInject constructor(myRepository: MyRepository) :
    ViewModel() {
    protected val repository: MyRepository

    init {
        repository = myRepository
    }

    fun getCompetitions(Token: String): LiveData<Resource<Competitions_Response?>?>? {
        return repository.getCompetitions(Token)
    }

    fun getTeamById(token: String, id: String): LiveData<Resource<CompetitionTeam?>?>? {

        return repository.getTeamById(token, id)
    }

    fun insert(competitionTeam: CompetitionTeam) {
        return repository.insertFavCompetitions(competitionTeam)
    }

    fun get(): MutableLiveData<List<CompetitionTeam>> {
        return repository.getFavCompetitions()
    }



}