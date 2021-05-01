package de.dhbw.binaeratops.view.player.map;

import java.util.Objects;

public class Tupel <o>{

    o key;
    o value;

    public Tupel(o key, o value) {
        this.key = key;
        this.value = value;
    }

    public Tupel(){}

    public o getKey() {
        return key;
    }

    public void setKey(o key) {
        this.key = key;
    }

    public o getValue() {
        return value;
    }

    public void setValue(o value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tupel<?> tupel = (Tupel<?>) o;
        return Objects.equals(key, tupel.key) && Objects.equals(value, tupel.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
