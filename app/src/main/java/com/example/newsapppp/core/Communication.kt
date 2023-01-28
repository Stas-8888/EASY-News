package com.example.newsapppp.core

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow

interface Communication {

    interface EmitState<T> {
        suspend fun put(value: T)
    }

    interface Collect<T> {
        suspend fun collect(collector: FlowCollector<T>)
    }

    abstract class Abstract<T>(
        private val stateFlow: MutableSharedFlow<T> = MutableSharedFlow()
    ) : EmitState<T>, Collect<T> {

        override suspend fun put(value: T) {
            stateFlow.emit(value)
        }

        override suspend fun collect(collector: FlowCollector<T>) =
            stateFlow.collect(collector)
    }
}
