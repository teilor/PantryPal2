package br.com.teilorsoares.pantrypal.presentation.util

class Event<T> {
    private val observers = mutableSetOf<(T) -> Unit>()

    operator fun plusAssign(observer: (T) -> Unit) {
        observers.add(observer)
    }

    operator fun minusAssign(observer: (T) -> Unit) {
        observers.remove(observer)
    }

    operator fun invoke(value: T) {
        for (observer in observers)
            observer(value)
    }
}

class SuspendedEvent<T> {
    private val observers = mutableSetOf<suspend (T) -> Unit>()

    operator fun plusAssign(observer: suspend (T) -> Unit) {
        observers.add(observer)
    }

    operator fun minusAssign(observer: suspend (T) -> Unit) {
        observers.remove(observer)
    }

    suspend operator fun invoke(value: T) {
        for (observer in observers)
            observer(value)
    }
}