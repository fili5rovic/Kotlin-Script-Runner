package fili5rovic.scriptexecutor.events;

import fili5rovic.scriptexecutor.events.myEvents.MyEvent;

@FunctionalInterface
public interface MyEventListener<T extends MyEvent> {
    void handle(T event);
}
