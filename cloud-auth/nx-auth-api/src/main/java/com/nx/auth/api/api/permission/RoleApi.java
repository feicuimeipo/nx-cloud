package com.nx.auth.api.api.permission;

import java.util.Collection;

/**
 * 角色 API 接口
 *
 * @author 锐信视通
 */
public interface RoleApi {

    /**
     * 校验角色们是否有效。如下情况，视为无效：
     * 1. 角色编号不存在
     * 2. 角色被禁用
     *
     * @param ids 角色编号数组
     */
    void validRoles(Collection<Long> ids);

}
