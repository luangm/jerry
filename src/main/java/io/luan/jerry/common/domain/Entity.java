package io.luan.jerry.common.domain;

import lombok.Getter;
import lombok.Setter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public abstract class Entity implements Serializable, PropertyChangeListener {

    private static final long serialVersionUID = 1L;

    private final PropertyChangeSupport pcs;

    @Getter
    @Setter
    private EntityState state;

    protected Entity() {
        this.state = EntityState.Detached;
        pcs = new PropertyChangeSupport(this);
        pcs.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (this.state == EntityState.Unchanged) {
            this.state = EntityState.Modified;
        }
    }

    protected void fireIndexedPropertyChange(String propertyName, int index, Object oldValue, Object newValue) {
        this.pcs.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        this.pcs.firePropertyChange(propertyName, oldValue, newValue);
    }

}
