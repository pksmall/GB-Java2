package pavelkorzhenko;

import org.w3c.dom.ranges.RangeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBook {
    private Map<Long, Record> records = new HashMap<Long, Record>();
    private long nextId;
    private long getNextId() {
        if (nextId < Long.MAX_VALUE && !records.containsKey(nextId))
            return nextId++;
        nextId = 0;
        while (nextId < Long.MAX_VALUE) {
            if (!records.containsKey(nextId))
                return nextId;
            ++nextId;
        }
        throw new RangeException((short)111, "No more IDs availiable");
    }
    public Record addRecord(String phone, String name)
            throws CloneNotSupportedException {
        long recordId = getNextId();
        Record record = new Record(recordId, phone, name);
        records.put(recordId, record);
        return (Record)record.clone();
    }
    public Record getRecord(long id) throws CloneNotSupportedException {
        if (records.containsKey(id))
            return (Record)records.get(id).clone();
        return null;
    }
    public List<Record> getRecord(String name) throws CloneNotSupportedException {
        List<Record> result = new ArrayList<Record>();
        for (Record record : this.records.values()) {
            if (record.getName().equals(name)) {
                result.add(record);
            }
        }
        return result;
    }
    public Record updateRecord(long id, String phone, String name)
            throws CloneNotSupportedException {
        Record record = new Record(id, phone, name);
        records.put(id, record);
        return getRecord(id);
    }
    public void removeRecord(long id) {
        records.remove(id);
    }
    public List<Record> allRecords() {
        List<Record> result = new ArrayList<Record>();
        for (Record record : this.records.values())
            result.add(record);
        return result;
    }
}
