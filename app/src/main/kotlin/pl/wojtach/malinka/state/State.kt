package pl.wojtach.malinka.state

/**
 * Created by lukaszwojtach on 16.04.2017.
 */
data class State(
        val errorState: ErrorState?,
        val loginState: LoginState
        //val mainScreenState: MainScreenState,
        //val enviromentState: EnviromentState
) {
}