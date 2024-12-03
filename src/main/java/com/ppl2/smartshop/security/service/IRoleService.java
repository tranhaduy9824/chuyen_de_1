package com.ppl2.smartshop.security.service;

import com.ppl2.smartshop.entities.Role;
import com.ppl2.smartshop.services.IGeneralService;

public interface IRoleService extends IGeneralService<Role, Integer> {
    Role findByName(String name);
}