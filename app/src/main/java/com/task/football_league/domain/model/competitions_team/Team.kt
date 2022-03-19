package com.task.football_league.domain.model.competitions_team

import android.os.Parcel
import android.os.Parcelable

class Team() : Parcelable {
    val address: String?=null
    val area: AreaX?=null
    val clubColors: String?=null
    val crestUrl: String?=null
    val email: String?=null
    val founded: Int?=null
    val id: Int?=null
    val lastUpdated: String?=null
    val name: String?=null
    val phone: String?=null
    val shortName: String?=null
    val tla: String?=null
    val venue: String?=null
    val website: String?=null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Team> {
        override fun createFromParcel(parcel: Parcel): Team {
            return Team(parcel)
        }

        override fun newArray(size: Int): Array<Team?> {
            return arrayOfNulls(size)
        }
    }
}