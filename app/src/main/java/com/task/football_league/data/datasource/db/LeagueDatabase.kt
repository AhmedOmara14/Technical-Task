package com.task.football_league.data.datasource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.football_league.domain.model.competitions_team.CompetitionTeam
import com.task.football_league.domain.model.competitions_team.TypeConverterCompetation
import com.task.football_league.domain.model.competitions_team.TypeConverterTeam


@Database(
    entities = [CompetitionTeam::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverterCompetation::class,TypeConverterTeam::class)
abstract class LeagueDatabase: RoomDatabase() {
    abstract val noteDao: LeagueDao
    companion object {
        const val DATABASE_NAME = "League_db"
    }
}