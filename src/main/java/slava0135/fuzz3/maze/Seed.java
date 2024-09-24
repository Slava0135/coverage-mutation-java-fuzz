package slava0135.fuzz3.maze;

import java.util.HashSet;
import java.util.Set;

public class Seed {
    private String data;
    public Set<Location> coverage;
    private double distance;
    private double energy;

    public Seed(String data) {
        this.data = data;
        this.coverage = new HashSet<>();
        this.distance = -1;
        this.energy = 0.0;
    }

    @Override
    public String toString() {
        return this.data;
    }

    // Getters and setters

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Set<Location> getCoverage() {
        return coverage;
    }

    public void setCoverage(Set<Location> coverage) {
        this.coverage = coverage;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }
}
