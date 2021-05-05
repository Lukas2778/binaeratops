package de.dhbw.binaeratops.model.map;

import java.util.Objects;

/**
 *
 * @param <object> Art des Tupels.
 */
public class Tuple<object>{

    object key;
    object value;

    public Tuple(object AKey, object AValue) {
        this.key = AKey;
        this.value = AValue;
    }

    public Tuple(){}

    public object getKey() {
        return key;
    }

    public void setKey(object AKey) {
        this.key = AKey;
    }

    public object getValue() {
        return value;
    }

    public void setValue(object AValue) {
        this.value = AValue;
    }

    @Override
    public boolean equals(Object AObject) {
        if (this == AObject) return true;
        if (AObject == null || getClass() != AObject.getClass()) return false;
        Tuple<?> tuple = (Tuple<?>) AObject;
        return Objects.equals(key, tuple.key) && Objects.equals(value, tuple.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
