package me.simpleplatformer.Util;

import java.util.EnumMap;

/**
 * Created by User on 19/03/2015.
 */
public class KeyMap {
    private EnumMap<KeyFunction, Integer> map;

    public KeyMap() {
        map = new EnumMap<>(KeyFunction.class);
    }

    public void setKeyFunction(KeyFunction function, int glfwKey) {
        map.put(function, glfwKey);
    }

    public int getKey(KeyFunction function) {
        return map.get(function);
    }
}
