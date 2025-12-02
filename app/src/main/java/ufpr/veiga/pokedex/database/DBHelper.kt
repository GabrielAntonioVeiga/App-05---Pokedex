package ufpr.veiga.pokedex.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Objects

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE, null, VERSION) {
    companion object{
        const val DATABASE = "pokedex.db"
        const val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = """
            CREATE TABLE users(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                email TEXT NOT NULL,
                password TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(sql)

        if (Objects.nonNull(db)) {
            populateUsers(db)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun populateUsers(db: SQLiteDatabase){
        val sql = "INSERT INTO users(email, password) VALUES(?, ?)"

        db.execSQL(sql, arrayOf("gabriel.veiga@gmail.com", "123456"))
        db.execSQL(sql, arrayOf("uriel.duarte@gmail.com", "123456"))
        db.execSQL(sql, arrayOf("vitor.espinoza@gmail.com", "123456"))
        db.execSQL(sql, arrayOf("rafael.silva@gmail.com", "123456"))
    }

    fun getValidUser(email: String, password: String): Boolean{
        val db = readableDatabase
        val sql = "SELECT * FROM users WHERE email = ? AND password = ?"
        val cursor = db.rawQuery(sql, arrayOf(email, password))
        val isValid = cursor.count > 0
        cursor.close()
        db.close()
        return isValid
    }


}