package com.task.football_league.data.repository

import android.content.ContentValues
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.task.football_league.common.HelperClass
import com.task.football_league.data.datasource.RemoteDataSource
import com.task.football_league.domain.model.competitions.Competitions_Response
import com.task.football_league.di.PreferenceModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.net.ConnectException
import com.task.football_league.common.Resource
import com.task.football_league.data.datasource.db.LeagueDatabase
import com.task.football_league.domain.model.competition_details.CompetitionDetails
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.model.team_players.TeamPlayers
import com.task.football_league.domain.repository.MyRepository
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val preferenceModule: PreferenceModule,
    private val db: LeagueDatabase
) : MyRepository {
    override fun getCompetitions(token: String): LiveData<Resource<Competitions_Response?>?> {
        val competitionLiveData = MutableLiveData<Resource<Competitions_Response?>?>()
        competitionLiveData.value = (Resource.Loading())
        remoteDataSource.getCompetitions(token)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : DisposableSingleObserver<Response<Competitions_Response?>?>() {
                override fun onSuccess(response: Response<Competitions_Response?>) {
                    competitionLiveData.value = (Resource.Success(response.body()))
                }

                override fun onError(e: Throwable) {
                    competitionLiveData.value = (Resource.Error(getErrorType(e)))
                }
            }
            )
        return competitionLiveData
    }

    override fun getTeamById(
        Token: String,
        id: String
    ): LiveData<Resource<CompetitionTeam?>?> {
        val teamsLiveData = MutableLiveData<Resource<CompetitionTeam?>?>()
        teamsLiveData.value = (Resource.Loading())
        remoteDataSource.getTeamById(Token, id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : DisposableSingleObserver<Response<CompetitionTeam?>?>() {
                override fun onSuccess(response: Response<CompetitionTeam?>) {
                    teamsLiveData.value = (Resource.Success(response.body()))

                }

                override fun onError(e: Throwable) {
                    teamsLiveData.value = (Resource.Error(getErrorType(e)))
                }
            }
            )
        return teamsLiveData
    }

    override fun getCompetitionsById(
        token: String,
        id: String
    ): LiveData<Resource<CompetitionDetails?>?> {
        val competitionsDetailsLiveData = MutableLiveData<Resource<CompetitionDetails?>?>()
        competitionsDetailsLiveData.value = (Resource.Loading())
        remoteDataSource.getCompetitionsById(token, id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : DisposableSingleObserver<Response<CompetitionDetails?>?>() {
                override fun onSuccess(response: Response<CompetitionDetails?>) {
                    competitionsDetailsLiveData.value = (Resource.Success(response.body()))

                }

                override fun onError(e: Throwable) {
                    competitionsDetailsLiveData.value = (Resource.Error(getErrorType(e)))
                }
            }
            )
        return competitionsDetailsLiveData
    }

    override fun getTeamPlayers(token: String, id: String): LiveData<Resource<TeamPlayers?>?> {
        val teamPlayerLiveData = MutableLiveData<Resource<TeamPlayers?>?>()
        teamPlayerLiveData.value = (Resource.Loading())
        remoteDataSource.getTeamPlayers(token, id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : DisposableSingleObserver<Response<TeamPlayers?>?>() {
                override fun onSuccess(response: Response<TeamPlayers?>) {
                    teamPlayerLiveData.value = (Resource.Success(response.body()))

                }

                override fun onError(e: Throwable) {
                    teamPlayerLiveData.value = (Resource.Error(getErrorType(e)))
                }
            }
            )
        return teamPlayerLiveData
    }

    override fun getFavCompetitions(): MutableLiveData<List<CompetitionTeam>> {
        val competitionTeamLiveData = MutableLiveData<List<CompetitionTeam>>()
        AsyncTask.execute {
            kotlin.run {
                competitionTeamLiveData.postValue(db.noteDao.getCompetition())
            }
        }
        return competitionTeamLiveData
    }

    override fun insertFavCompetitions(competitionTeam: CompetitionTeam) {
        return db.noteDao.insertCompetition(competitionTeam)
    }


    private fun getErrorType(throwable: Throwable): String {
        return if (throwable is ConnectException) {
            if (preferenceModule.language == "ar") {
                "من فضلك تأكد من اتصالك بالإنترنت"
            } else {
                "Please Check your Internet Connection"
            }
        } else if (throwable is TimeoutException) {
            if (preferenceModule.language == "ar") {
                "تعذر الاتصال بالسيرفر"
            } else {
                "Unable to contact the server"
            }
        } else {
            if (preferenceModule.language == "ar") {
                try {
                    Log.d(ContentValues.TAG, "getErrorType: " + throwable.cause!!.message)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                "حدث مشكلة ما"
            } else {
                "Something went wrong"
            }
        }
    }

}