package com.labi.permissions;

public enum PermissionsEnum {

    OPERATOR("server.op");
    private String permission;

    PermissionsEnum(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
