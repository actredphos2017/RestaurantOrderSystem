package com.sakuno.restaurant

import com.sakuno.restaurant.utils.DatabaseEntrance

object Globals {
    const val databaseDriver = "com.mysql.cj.jdbc.Driver"
    const val databaseUrl = "jdbc:mysql://127.0.0.1:3306"
    const val databaseUserName = "root"
    const val databasePassword = "helloworld"
    const val databaseName = "RestaurantDatabase"
    var dbEntrance: DatabaseEntrance? = null
}