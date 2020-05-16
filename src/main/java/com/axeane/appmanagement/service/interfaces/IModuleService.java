package com.axeane.appmanagement.service.interfaces;

import com.axeane.appmanagement.domain.Module;

import java.util.List;

public interface IModuleService {
    Module saveModule(Module module);

    Module getModuleById(Long id);

    List<Module> getAllModule(Long id);

    void deleteModuleById(Long id);

    Module updateModule(Module module);
}
