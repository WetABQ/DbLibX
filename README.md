# DbLibX
NukkitX Database Dependency with Kotlin Coroutine.
- DbLibX base on the Kotlin Coroutine auto async control database.
- DbLibX base on the Alibaba Druid Connection Pool
- DbLibX has friendly interface for developer.
- Main interface：
  ```kotlin 
  createAction<R>(sql: kotlin.String, vararg args: kotlin.Any,
   noinline action: suspend (java.sql.PreparedStatement) -> R)
  ```
  - Example 1:
  ```kotlin
  val insertAction = 
    createAction<Int>( // Kotlin can infer class type, you can ignore the INT type
            "INSERT INTO wacban (player, violationId, violationDuration, 
            violationType, checkType, time, extra)
            VALUES (?, ?, ?, ? ,? ,?, ?)", // SQL statement
            player, // arg 1
            violationData.violationId, // arg 2
            violationData.violationDuration, // args...
            violationData.violationType.toString(),
            violationData.getCheckType().getName(),
            violationData.time,
            violationData.extra) {
                it.executeUpdate() // The action you want to execute
            }
    insertAction.doAction() // Return action status
  ```
  - Example 2:
  ```kotlin
  val cacheHelperDataAction = 
    createAction("select * from wachelper") { // Construct database action
        val rs = it.executeQuery() // execute query
        val result = ArrayList<String>()
        while (rs.next()) result.add(rs.getString("player")) // get data
        result // return data
    }
    cacheBanDataAction.doAction() // execute action and get return data
  ```
