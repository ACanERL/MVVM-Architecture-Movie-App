package com.example.moviemood.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviemood.utils.MOVIE_TABLE

@Entity(tableName = MOVIE_TABLE)
data class MoviesEntity (
    @PrimaryKey
    var id:Int =0,
    var poster : String ="",
    var title : String ="",
    var rate : String ="",
    var lang : String ="",
    var year : String =""

)