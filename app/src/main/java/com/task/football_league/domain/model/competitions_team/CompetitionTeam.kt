package com.task.football_league.domain.model.competitions_team

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "League")
class CompetitionTeam() : Parcelable{
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id")
    var id: Int = 0
    var competition: Competition?=null
    var count: String?=null
    var teams: List<Team>?=null



    constructor(parcel: Parcel) : this() {
        count = parcel.readString()
    }
    constructor(competition: Competition?, count: String) : this() {
        this.competition=competition
        this.count=count
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CompetitionTeam> {
        override fun createFromParcel(parcel: Parcel): CompetitionTeam {
            return CompetitionTeam(parcel)
        }

        override fun newArray(size: Int): Array<CompetitionTeam?> {
            return arrayOfNulls(size)
        }
    }
}