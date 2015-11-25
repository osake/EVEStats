package com.tlabs.eve.api;



import java.util.HashMap;
import java.util.Map;

public final class NamesResponse extends EveAPIResponse {

    private static final long serialVersionUID = -8548006791679363095L;

    private Map<Long, String> names = new HashMap<>();
    private String key;

    public void add(long id, String name) {
        names.put(id, name);
    }

    public Map<Long, String> getNames() {
        return this.names;
    }

    public String getName(long id) {
        return this.names.get(id);
    }

    public final String getKey() {
        return key;
    }

    public final void setKey(String key) {
        this.key = key;
    }

}
