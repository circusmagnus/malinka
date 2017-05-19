package pl.wojtach.malinka.ui.login_screen

import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import pl.wojtach.malinka.LoginErrorAction
import pl.wojtach.malinka.LoginSuccesAction
import pl.wojtach.malinka.StartLoginAction
import pl.wojtach.malinka.data.sensors.SensorRepository
import pl.wojtach.malinka.state.State
import pl.wojtach.malinka.state.StateMachine
import java.util.concurrent.TimeUnit

/**
 * Created by lukaszwojtach on 05.05.2017.
 */


class LoginActionDispatcher(val stateMachine: StateMachine<State>, val loggableEntity: SensorRepository) {

    val eventsReceiver: PublishSubject<LoginData> = PublishSubject.create<LoginData>()


    init {
        eventsReceiver
                .throttleFirst(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .subscribe { runLoginActions(it) }
    }

    fun login(userField: TextView, passwordField: TextView) {
        eventsReceiver.onNext(LoginData(userField.text.toString(), passwordField.text.toString()))
    }

    private fun runLoginActions(data: LoginData) {
        toLoadingState(data)
        loggableEntity.getInfoFromSensors()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ toLoggedInState() }, { toLoginErrorState() })
    }

    private fun toLoadingState(data: LoginData) {
        stateMachine.dispatch(StartLoginAction(data.user, data.password))
    }

    private fun toLoggedInState() {
        stateMachine.dispatch(LoginSuccesAction())
    }

    private fun toLoginErrorState() = stateMachine.dispatch(LoginErrorAction())
}

data class LoginData(val user: String, val password: String)