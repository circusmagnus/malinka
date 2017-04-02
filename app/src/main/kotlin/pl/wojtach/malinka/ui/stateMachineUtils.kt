package pl.wojtach.malinka.ui

import android.content.Intent
import android.os.Bundle
import pl.wojtach.malinka.statemachine.StateMachine
import pl.wojtach.malinka.statemachine.createInitialState
import pl.wojtach.malinka.statemachine.states.State

/**
 * Created by Lukasz on 07.06.2017.
 */

internal fun setupStateMachine(savedBundle: Bundle? = null, intent: Intent? = null): StateMachine<State> =
        StateMachine.getInstance(
                savedBundle?.getParcelable<State?>(State.BUNDLE_KEY)
                        ?: intent?.getParcelableExtra<State?>(State.BUNDLE_KEY)
                        ?: createInitialState()
        )
