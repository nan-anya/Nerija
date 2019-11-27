package com.example.nerija;

public class TransportAlarmSystem
{
    InputManager inputManager;

    public TransportAlarmSystem()
    {
        inputManager = new InputManager();
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager)
    {
        this.inputManager = inputManager;
    }
}
