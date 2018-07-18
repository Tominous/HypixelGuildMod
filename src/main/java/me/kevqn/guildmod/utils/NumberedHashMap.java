package me.kevqn.guildmod.utils;
import java.util.concurrent.*;
import java.util.*;

public class NumberedHashMap<type1, type2>
{
    private ArrayList<ConcurrentHashMap> createdList;
    
    public NumberedHashMap() {
        this.createdList = new ArrayList<ConcurrentHashMap>();
    }
    
    public synchronized int size() {
        return this.createdList.size();
    }
    
    public synchronized void put(final type1 key, final type2 value) {
        if (this.containsKeyAndValue(key, value)) {
            return;
        }
        final ConcurrentHashMap createdMap = new ConcurrentHashMap();
        createdMap.put(key, value);
        this.createdList.add(createdMap);
    }
    
    public synchronized void remove(final int listPosition) {
        this.createdList.remove(listPosition);
    }
    
    public synchronized void removeMapsContainingKey(final Object key) {
        for (int i = 0; i < this.size(); ++i) {
            final Map<Object, Object> tempMap = this.createdList.get(i);
            final Map.Entry<Object, Object> entry = tempMap.entrySet().iterator().next();
            if (entry.getKey() == key) {
                this.remove(i);
            }
        }
    }
    
    public synchronized void removeMapWithSaidValues(final Object key, final Object value) {
        for (int i = 0; i < this.size(); ++i) {
            final Map<Object, Object> tempMap = this.createdList.get(i);
            final Map.Entry<Object, Object> entry = tempMap.entrySet().iterator().next();
            if (entry.getKey() == key && entry.getValue() == value) {
                this.remove(i);
            }
        }
    }
    
    public synchronized type2 getValue(final int listPosition) {
        if (this.createdList.isEmpty()) {
            return null;
        }
        if (listPosition >= 0 && listPosition < this.createdList.size()) {
            final Map<Object, Object> tempMap = this.createdList.get(listPosition);
            final Map.Entry<Object, Object> entry = tempMap.entrySet().iterator().next();
            return (type2)entry.getValue();
        }
        throw new ArrayIndexOutOfBoundsException();
    }
    
    public synchronized type1 getKey(final int listPosition) {
        if (this.createdList.isEmpty()) {
            return null;
        }
        if (listPosition >= 0 && listPosition < this.createdList.size()) {
            final Map<Object, Object> tempMap = this.createdList.get(listPosition);
            final Map.Entry<Object, Object> entry = tempMap.entrySet().iterator().next();
            return (type1)entry.getKey();
        }
        throw new ArrayIndexOutOfBoundsException();
    }
    
    public synchronized ConcurrentHashMap getMap(final int listPosition) {
        if (this.createdList.isEmpty()) {
            return null;
        }
        if (listPosition >= 0 && listPosition < this.createdList.size()) {
            return this.createdList.get(listPosition);
        }
        throw new ArrayIndexOutOfBoundsException();
    }
    
    public synchronized boolean containsKey(final Object key) {
        if (this.createdList.isEmpty()) {
            for (int i = 0; i < this.size(); ++i) {
                final Map<Object, Object> tempMap = this.createdList.get(i);
                if (tempMap.containsKey(key)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public synchronized boolean containsKeyAndValue(final Object key, final Object value) {
        if (!this.createdList.isEmpty()) {
            for (int i = 0; i < this.size(); ++i) {
                final Map<Object, Object> tempMap = this.createdList.get(i);
                if (tempMap.containsKey(key) && tempMap.containsValue(value)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public synchronized boolean isEmpty() {
        return this.createdList.isEmpty();
    }
    
    public synchronized void clear() {
        this.createdList.clear();
    }
    
    public synchronized void changeKeyInMap(final Object oldKey, final Object value, final Object newKey) {
        if (this.containsKey(oldKey)) {
            final Map tempMap = this.createdList.get(this.getListPosition(oldKey, value));
            final ConcurrentHashMap newMap = new ConcurrentHashMap();
            newMap.put(newKey, tempMap.get(0));
            final int position = this.getListPosition(oldKey, value);
            this.createdList.remove(position);
            this.createdList.add(position, newMap);
        }
    }
    
    public synchronized void changeValueInMap(final Object key, final Object oldValue, final Object newValue) {
        if (this.containsKey(key)) {
            final ConcurrentHashMap newMap = new ConcurrentHashMap();
            newMap.put(key, newValue);
            this.createdList.remove(this.getListPosition(key, oldValue));
            this.createdList.add(newMap);
        }
    }
    
    public synchronized int getMapPositionNumber(final Object key, final Object value) {
        if (this.containsKeyAndValue(key, value)) {
            for (int i = 0; i < this.createdList.size(); ++i) {
                final Map tempMap = this.createdList.get(i);
                if (tempMap.containsKey(key) && tempMap.containsValue(value)) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private synchronized int getListPosition(final Object key, final Object value) {
        if (!this.createdList.isEmpty()) {
            if (value != null) {
                return this.getMapPositionNumber(key, value);
            }
            for (int i = 0; i < this.createdList.size(); ++i) {
                final Map<Object, Object> tempMap = this.createdList.get(i);
                final Map.Entry<Object, Object> entry = tempMap.entrySet().iterator().next();
                if (entry.getKey().equals(key)) {
                    return i;
                }
            }
        }
        throw new ArrayIndexOutOfBoundsException();
    }
    
    public synchronized void printContents() {
        for (int i = 0; i < this.createdList.size(); ++i) {
            System.out.println("[" + this.getKey(i) + " " + this.getValue(i) + "]");
        }
    }
}
