package com.itkeller.smpsystems.Utils.Position;

import java.util.UUID;

public class PermissionKey {

    private final String key;
    private final UUID uuid;


    public PermissionKey(UUID uuid,String key) {
        this.key = key;
        this.uuid = uuid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PermissionKey other = (PermissionKey) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        } else if (!uuid.equals(other.uuid))
            return false;
        return true;
    }


    public String getName() {
        return key;
    }


    public UUID getUuid() {
        return uuid;
    }
    
    
}
