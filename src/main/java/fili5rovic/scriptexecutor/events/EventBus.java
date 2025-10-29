package fili5rovic.scriptexecutor.events;

import fili5rovic.scriptexecutor.events.myEvents.MyEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {

    private static EventBus instance;

    private final Map<Class<? extends MyEvent>, List<MyEventListener<? extends MyEvent>>> map = new HashMap<>();

    public static EventBus instance() {
        if (instance == null)
            instance = new EventBus();
        return instance;
    }

    private EventBus() {}

    public <T extends MyEvent> void register(Class<T> eventClass, MyEventListener<T> listener) {
        map.computeIfAbsent(eventClass, e -> new ArrayList<>()).add(listener);
    }

    @SuppressWarnings("unchecked")
    public <T extends MyEvent> void publish(T event) {
        List<MyEventListener<? extends MyEvent>> listeners = map.get(event.getClass());
        if (listeners != null) {
            for (MyEventListener<? extends MyEvent> listener : listeners) {
                ((MyEventListener<T>) listener).handle(event);
            }
        }
    }
}
