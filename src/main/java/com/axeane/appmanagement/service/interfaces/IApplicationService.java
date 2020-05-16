package com.axeane.appmanagement.service.interfaces;

import com.axeane.appmanagement.domain.Application;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IApplicationService {
    Application saveApplication(Application app);

    Application updateApplication(Application app);

    @Transactional(readOnly = true)
    Application getApplicationById(Long id);

    void deleteApplicationById(Long id);

    List<Application> getAllApplications();
}
