package top.wetabq.dblibx.utils

import java.io.*
import java.util.*

object FileOtherUtils {

    @JvmStatic
    @Throws(IOException::class)
    fun readProperties(file: File): Properties {
        val properties = Properties()
        properties.load(BufferedReader(FileReader(file)))
        return properties
    }

}