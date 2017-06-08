package pl.wojtach.malinka.ui.main_screen

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by lukaszwojtach on 07.06.2017.
 */
class SensorListViewModel(val stateMachine: StateMachine<State>, context: Context) {

    var adapter = SensorAdapter(emptyList(), SensorClicksDispatcher.getInstance(stateMachine))
    val layoutManager = LinearLayoutManager(context)

    init {
        render(stateMachine.getState())
        stateMachine.getPublisher().observeOn(AndroidSchedulers.mainThread()).subscribe { render(it) }
    }

    private fun render(state: State) {
        state.sensorState.sensors
                .map { SensorViewModel(stateMachine = stateMachine, id = it.mac) }
                .toList()
                .let { adapter.sensors = it }
    }
}