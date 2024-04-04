package com.example.Zeta.service;

import com.example.Zeta.dto.AdminDto;
import com.example.Zeta.model.Admin;

public interface AdminService {
    Admin findByUsername(String username);

    Admin save(AdminDto adminDto);
}
