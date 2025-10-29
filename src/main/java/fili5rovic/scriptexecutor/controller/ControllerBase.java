package fili5rovic.scriptexecutor.controller;

import fili5rovic.scriptexecutor.manager.IManager;

import java.util.List;

public abstract class ControllerBase {

    public final void setup() {
        List<IManager> managers = createManagers();
        for (IManager manager : managers) {
            manager.initialize();
        }
    }

    protected abstract List<IManager> createManagers();
}
