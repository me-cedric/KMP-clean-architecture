package cedricmeyer.kmp.sqldelight

import app.cash.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        TODO("Not yet implemented")
    }
}