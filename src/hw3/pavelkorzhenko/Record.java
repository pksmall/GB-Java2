package pavelkorzhenko;

import java.io.Serializable;

class Record implements Serializable, Cloneable {
    private final long id;
    private String phone;
    private String name;
    Record(long id, String phone, String name) {
        this.id = id;
        this.phone = phone;
        this.name = name;
    }
    @Override
    public String toString() {
        return name + ": " + phone;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Record other = (Record) obj;
        if ((this.phone == null) ? (other.phone != null) : !this.phone.equals(other.phone)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 91 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 91 * hash + (this.phone != null ? this.phone.hashCode() : 0);
        hash = 91 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public long getId() {
        return id;
    }
    public String getPhone() {
        return phone;
    }
    public String getName() {
        return name;
    }
}
