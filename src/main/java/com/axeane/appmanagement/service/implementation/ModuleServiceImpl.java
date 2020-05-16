package com.axeane.appmanagement.service.implementation;

import com.axeane.appmanagement.domain.Application;
import com.axeane.appmanagement.domain.Module;
import com.axeane.appmanagement.repository.ApplicationRepository;
import com.axeane.appmanagement.repository.ModuleRepository;
import com.axeane.appmanagement.service.interfaces.IModuleService;
import com.axeane.appmanagement.web.rest.errors.FormatNotValidException;
import com.axeane.appmanagement.web.rest.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ModuleServiceImpl implements IModuleService {

    private final ModuleRepository moduleRepository;
    private final ApplicationRepository applicationRepository;


    public ModuleServiceImpl(ModuleRepository moduleRepository, ApplicationRepository applicationRepository) {
        this.moduleRepository = moduleRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Module saveModule(Module module) {
        if (module.getId() != null) {
            throw new FormatNotValidException("Id should not be provided");
        }

        if (module.getApplication() == null || module.getApplication().getId() == null) {
            throw new FormatNotValidException("Application id should no be null");
        }

        Application application = applicationRepository.findById(module.getApplication().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Application not found")
        );
        module.setApplication(application);
        return moduleRepository.save(module);
    }

    @Override
    public Module getModuleById(Long id) {
        return moduleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Module not found")
        );
    }

    @Override
    public List<Module> getAllModule(Long id) {
        return moduleRepository.getAllModulesByApp(id);
    }

    @Override
    public void deleteModuleById(Long id) {
        Module module = getModuleById(id);
        moduleRepository.delete(module);
    }

    @Override
    public Module updateModule(Module module) {
        if (module.getId() == null) {
            throw new FormatNotValidException("Id should be provided");
        }

        return moduleRepository.findById(module.getId()).map(moduleToUpdate -> {
                    moduleToUpdate.setModuleName(module.getModuleName());
                    moduleToUpdate.setModuleVersion(module.getModuleVersion());
                    moduleToUpdate.setIsActive(module.getIsActive());
                    return moduleRepository.save(moduleToUpdate);
                }
        ).orElseThrow(
                () -> new ResourceNotFoundException("Module not found")
        );
    }
}
