package pl.wojtach.malinka.ui.main_screen

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by Lukasz on 07.01.2017.
 */

class SensorViewModel(stateMachine: StateMachine<State>, val id: String) {


    val isActiveObservable = ObservableBoolean(false)
    val errorOccuredObservable = ObservableBoolean(false)

    val name = ObservableField<String>()
    val value = ObservableField<String>()
    val date = ObservableField<String>()

    init {
        render(stateMachine.getState())
        stateMachine.getPublisher().observeOn(AndroidSchedulers.mainThread()).subscribe { render(it) }
    }

    private fun render(state: State) {
        val sourceSensor = state.sensorState.sensors.first { it.mac == id }
        isActiveObservable.set(sourceSensor.isActive)
        //errorOccuredObservable.set(sourceSensor.error)
        name.set(sourceSensor.name)
        value.set(sourceSensor.lastValue)
        date.set(sourceSensor.lastDate)
    }

//
//    fun setNewStatus() {
//        val newSensorStatus = sensor.copy(isActive = isActiveObservable.get())
//        repository
//                .setSensorStatus(newSensorStatus)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        { success ->
//                            sensor.apply { isActive = newSensorStatus.isActive }
//                            isActiveObservable.set(sensor.isActive)
//                            errorOccuredObservable.set(false)
//                        },
//                        { error ->
//                            Log.d(TAG, error.message)
//                            isActiveObservable.set(sensor.isActive)
//                            errorOccuredObservable.set(true)
//                        })
//    }
}
