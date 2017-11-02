package pl.wojtach.malinka.statemachine

import io.reactivex.Observable

/**
 * Created by Lukasz on 05.07.2017.
 */
interface Consumer<STATE> {
    fun consume(stateObservable: Observable<STATE>)
}