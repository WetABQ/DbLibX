package top.wetabq.dblibx.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.PreparedStatement
import java.sql.ResultSet

suspend inline fun <R> createAction(sql: String, vararg args: Any, noinline action: suspend (ps: PreparedStatement) -> R): DatabaseAction<R> {
    val dbAction = DatabaseAction(action)
    dbAction prepare sql
    args.forEach {
        if (it is Int) {
            dbAction.setArgs(it)
        } else if (it is Double) {
            dbAction.setArgs(it)
        } else if (it is Long) {
            dbAction.setArgs(it)
        } else {
            dbAction.setArgs(it as String)
        }
    }
    return dbAction
}

class DatabaseAction<R> (private var action: suspend (ps: PreparedStatement) -> R) {

    lateinit var prepareStatement: PreparedStatement
    var result: ResultSet? = null
    var r: R? = null
    private var prepareIndex = 1

    suspend inline infix fun prepare(sql: String): PreparedStatement { prepareStatement = Database.getConnection().prepareStatement(sql); return prepareStatement }

    suspend fun doAction() : R {
        return withContext(Dispatchers.IO) {
            println()
            val rr: R = action(prepareStatement)
            r = rr
            rr
        }
    }

    suspend inline fun close() {
        withContext(Dispatchers.IO) { prepareStatement.close(); result?.close() }
    }

    suspend inline fun closeReturn() : R {
        withContext(Dispatchers.IO) { prepareStatement.close(); result?.close() }
        return r!!
    }

    fun setAction(action: suspend (ps: PreparedStatement) -> R) {
        this.action = action
    }

    fun setArgs(vararg args: Int) {
        args.forEach { prepareStatement.setInt(prepareIndex, it) }
        prepareIndex++
    }

    fun setArgs(vararg args: String) {
        args.forEach { prepareStatement.setNString(prepareIndex, it) }
        prepareIndex++
    }

    fun setArgs(vararg args: Double) {
        args.forEach { prepareStatement.setDouble(prepareIndex, it) }
        prepareIndex++
    }

    fun setArgs(vararg args: Long) {
        args.forEach { prepareStatement.setLong(prepareIndex, it) }
        prepareIndex++
    }

}