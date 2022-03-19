package com.task.football_league.data.datasource.db
import androidx.room.*
import com.task.football_league.domain.model.competitions_team.CompetitionTeam

@Dao
interface LeagueDao {
    @Query("SELECT * FROM League")
    fun getCompetition(): List<CompetitionTeam>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompetition(Competition: CompetitionTeam)

}