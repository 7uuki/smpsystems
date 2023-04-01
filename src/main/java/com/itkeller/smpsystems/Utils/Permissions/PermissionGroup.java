package com.itkeller.smpsystems.Utils.Permissions;

import java.util.ArrayList;

public class PermissionGroup {

    public ArrayList<String> permissions;
    public ArrayList<String> inheritance;
    private String name;
    

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public PermissionGroup(String name) {
        this.name = name;
    }


    private void add(String permission){
        if(!permissions.contains(permission)){
            permissions.add(permission);
        }

    }
    
}
