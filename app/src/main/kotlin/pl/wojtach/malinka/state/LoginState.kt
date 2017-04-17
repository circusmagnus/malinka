package pl.wojtach.malinka.state

/**
 * Created by lukaszwojtach on 16.04.2017.
 */
data class LoginState(
        val phaseOfLogging: PHASE,
        val currentUser: String,
        val currentPassword: String
)