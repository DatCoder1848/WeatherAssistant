package com.example.weather_assistant.common;

import java.util.Objects;

public class Location
{
    private String name, coordinate;
    Location (String name, String coordinate)
    {
        this.name = name;
        this.coordinate = coordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return Objects.equals(getName(), location.getName()) && Objects.equals(getCoordinate(), location.getCoordinate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCoordinate());
    }
}
