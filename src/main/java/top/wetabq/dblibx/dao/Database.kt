package top.wetabq.dblibx.dao

import com.alibaba.druid.pool.DruidDataSource
import com.alibaba.druid.pool.DruidDataSourceFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import top.wetabq.dblibx.DbLibX
import top.wetabq.dblibx.utils.FileOtherUtils
import java.io.File
import java.sql.Connection

object Database {

    private lateinit var source: DruidDataSource

    fun connect(): Boolean {
        source = DruidDataSourceFactory.createDataSource(FileOtherUtils.readProperties(File("${DbLibX.INSTANCE.dataFolder.absoluteFile}/database.properties"))) as DruidDataSource
        return isConnected()
    }

    suspend fun getConnection(): Connection {
        return withContext(Dispatchers.IO) {
            source.connection
        }
    }

    fun isConnected(): Boolean {
        return source.isEnable
    }

    fun disconnect() {
        source.close()
    }

}