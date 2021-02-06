package web.dao;

import web.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getRoleList();
    Role getRoleById(int id);
}
