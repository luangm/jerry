package io.luan.jerry.common.domain;

public enum EntityState {
    Added, // newly added.
    Modified, // tracked, modified, not yet saved.
    Deleted, // tracked, deleted, not yet saved.
    Unchanged, // Tracked, not changed. Typically loaded from db or just saved
    Detached, // Not tracked
}
