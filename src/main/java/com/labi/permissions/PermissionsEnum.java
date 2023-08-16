package com.labi.permissions;

public enum PermissionsEnum {

    OPERATOR("server.op"),
    ADMIN("server.admin"),
    MOD("server.mod");
    private String permission;

    PermissionsEnum(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
