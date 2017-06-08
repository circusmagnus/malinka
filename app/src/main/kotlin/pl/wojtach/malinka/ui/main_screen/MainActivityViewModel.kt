package pl.wojtach.malinka.ui.main_screen

import android.databinding.ObservableBoolean
import android.support.v4.widget.SwipeRefreshLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.wojtach.malinka.statemachine.Action
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.PHASE
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by Lukasz on 14.01.2017.
 */
class MainActivityViewModel(val stateMachine: StateMachine<State>) : SwipeRefreshLayout.OnRefreshListener {

    val TAG = MainActivityViewModel::class.java.simpleName
    var isRefreshing = ObservableBoolean(false)

    init {
        render(stateMachine.getState())
        stateMachine.getPublisher()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { render(it) }
    }

    fun render(state: State) {
        isRefreshing.set(state.sensorState.phase == PHASE.IN_PROGRESS)
    }

//    fun refreshSensorList() {
//        isRefreshing.set(true)
//        repository
//                .getInfoFromSensors()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    listAdapter.sensors.clear()
//                    listAdapter.sensors.addAll(it)
//                    listAdapter.notifyDataSetChanged()
//                    isRefreshing.set(false)
//                }, { error ->
//                    Log.d(TAG, "can`t load data: " + error.message)
//                    isRefreshing.set(false)
//                })
//    }
//
override fun onRefresh() {
    stateMachine.dispatch(NewSensorsNeededAction())
}
}

class NewSensorsNeededAction : Action<State> {
    override fun transformState(oldState: State): State = oldState.copy(
            sensorState = oldState.sensorState.copy(phase = PHASE.IN_PROGRESS))

}
