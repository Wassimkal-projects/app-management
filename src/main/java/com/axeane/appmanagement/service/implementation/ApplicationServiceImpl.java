package com.axeane.appmanagement.service.implementation;

import com.axeane.appmanagement.domain.Application;
import com.axeane.appmanagement.repository.ApplicationRepository;
import com.axeane.appmanagement.service.interfaces.IApplicationService;
import com.axeane.appmanagement.web.rest.errors.FormatNotValidException;
import com.axeane.appmanagement.web.rest.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplicationServiceImpl implements IApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application saveApplication(Application app) {
        if(app.getId() != null){
            throw new FormatNotValidException("Id field should not be provided");
        }
         app.getModules().forEach(module -> module.setApplication(app));
        return applicationRepository.save(app);
    }

    @Override
    public Application updateApplication(Application app){
        if(app.getId() == null){
            throw new FormatNotValidException("Id should be provided");
        }
        app.getModules().forEach(module -> module.setApplication(app));
        return applicationRepository.findById(app.getId())
                .map(appToUpdate -> {
                   appToUpdate.setAppName(app.getAppName());
                   appToUpdate.setAppType(app.getAppType());
                   appToUpdate.setDescription(app.getDescription());
                   appToUpdate.setAppVersion(app.getAppVersion());
                   appToUpdate.setWebUrl(app.getWebUrl());
                   appToUpdate.setDevelopedBy(app.getDevelopedBy());
                   appToUpdate.setIsOnProd(app.getIsOnProd());
                   appToUpdate.getModules().clear();
                   appToUpdate.getModules().addAll(app.getModules());
                   return applicationRepository.save(appToUpdate);
                })
                .orElseThrow(
                () -> new ResourceNotFoundException("Application not found")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Application getApplicationById(Long id){
        return applicationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Application not found")
        );
    }

    @Override
    public void deleteApplicationById(Long id){
        Application appToDelete = getApplicationById(id);
        applicationRepository.delete(appToDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
}
