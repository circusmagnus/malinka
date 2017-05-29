package pl.wojtach.malinka.data

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.wojtach.malinka.LoginErrorAction
import pl.wojtach.malinka.LoginSuccesAction
import pl.wojtach.malinka.data.sensors.SensorRepository
import pl.wojtach.malinka.state.State
import pl.wojtach.malinka.state.StateMachine


/**
 * Created by Lukasz on 29.05.2017.
 */
class DataFetcher(val stateMachine: StateMachine<State>, val repository: SensorRepository) {

    fun fetchData() {
        repository.getInfoFromSensors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ toLoggedInState(it) }, { toLoginErrorState(it) })
    }

    private fun toLoggedInState() = stateMachine.dispatch(LoginSuccesAction())

    private fun toLoginErrorState() = stateMachine.dispatch(LoginErrorAction())
}