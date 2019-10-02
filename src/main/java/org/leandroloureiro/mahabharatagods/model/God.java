package org.leandroloureiro.mahabharatagods.model;

import java.util.Objects;

public class God {

    private final String name;
    private final long hitCount;

    public God(final String name, final long hitCount) {
        this.name = name;
        this.hitCount = hitCount;
    }

    public String getName() {
        return name;
    }

    public long getHitCount() {
        return hitCount;
    }

    @Override
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        var god = (God) o;

        return hitCount == god.hitCount && Objects.equals(name, god.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hitCount);
    }

    @Override
    public String toString() {
        return "God{" + "name='" + name + '\'' + ", hitCount=" + hitCount + '}';
    }
}
