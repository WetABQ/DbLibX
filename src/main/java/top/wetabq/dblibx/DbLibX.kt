package top.wetabq.dblibx

import cn.nukkit.plugin.PluginBase
import top.wetabq.dblibx.dao.Database

class DbLibX : PluginBase() {


    override fun onEnable() {
        INSTANCE = this
        saveResource("database.properties")
        Database.connect()
        logger.info("DbLibX Enabled! - by WetABQ")
    }

    override fun onDisable() {
        logger.info("DbLibX Disabled!")
    }

    companion object {

        lateinit var INSTANCE: DbLibX

    }


}