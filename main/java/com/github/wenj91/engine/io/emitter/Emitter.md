Emitter#on(event, fn)
    Register an event handler fn.

Emitter#once(event, fn)
    Register a single-shot event handler fn, removed immediately after it is invoked the first time.

Emitter#off(event, fn)
    Pass event and fn to remove a listener.
    Pass event to remove all listeners on that event.
    Pass nothing to remove all listeners on all events.

Emitter#emit(event, ...)
    Emit an event with variable option args.

Emitter#listeners(event)
    Return an array of callbacks, or an empty array.

Emitter#hasListeners(event)
    Check if this emitter has event handlers.