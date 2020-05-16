package com.axeane.appmanagement.service.implementation;

import com.axeane.appmanagement.AppManagementApplication;
import com.axeane.appmanagement.domain.Application;
import com.axeane.appmanagement.domain.Module;
import com.axeane.appmanagement.repository.ApplicationRepository;
import com.axeane.appmanagement.service.interfaces.IApplicationService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppManagementApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApplicationServiceImplTest {

    private static final String DEFAULT_APP_NAME = "APP_NAME";
    private static final String DEFAULT_APP_TYPE = "APP_TYPE";
    private static final String DEFAULT_DESCRIPTION = "DESCRIPTION";
    private static final String DEFAULT_APP_VERSION = "APP_VERSION";
    private static final String DEFAULT_WEB_URL = "weburl.com";
    private static final String DEFAULT_DEVELOPED_BY = "DEVELOPED_BY";
    private static final Boolean IS_ON_PROD = true;

    private static final String DEFAULT_MODULE_NAME = "MODULE_NAME";
    private static final String DEFAULT_MODULE_VERSION = "MODULE_VERSION";
    private static final Boolean IS_ACTIVE = true;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    private IApplicationService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    private Application application;

    private Application createEntity() {
        Application application = new Application();
        application.setAppName(DEFAULT_APP_NAME);
        application.setAppType(DEFAULT_APP_TYPE);
        application.setAppVersion(DEFAULT_APP_VERSION);
        application.setDescription(DEFAULT_DESCRIPTION);
        application.setWebUrl(DEFAULT_WEB_URL);
        application.setDevelopedBy(DEFAULT_DEVELOPED_BY);
        application.setIsOnProd(IS_ON_PROD);

        Module module = new Module();
        module.setModuleName(DEFAULT_MODULE_NAME);
        module.setModuleVersion(DEFAULT_MODULE_VERSION);
        module.setIsActive(IS_ACTIVE);
        return application;
    }

    @Before
    public void initTest() {
        application = createEntity();
    }

    @Test
    @Transactional
    public void saveApplication() {
        Application savedApplication = applicationService.saveApplication(application);
        assertThat(savedApplication).isNotNull();
        assertThat(savedApplication.getId()).isNotNull();
        assertThat(savedApplication.getAppName()).isEqualTo(DEFAULT_APP_NAME);
        assertThat(savedApplication.getAppType()).isEqualTo(DEFAULT_APP_TYPE);
        assertThat(savedApplication.getAppVersion()).isEqualTo(DEFAULT_APP_VERSION);
        assertThat(savedApplication.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(savedApplication.getDevelopedBy()).isEqualTo(DEFAULT_DEVELOPED_BY);
        assertThat(savedApplication.getWebUrl()).isEqualTo(DEFAULT_WEB_URL);
        assertThat(savedApplication.getIsOnProd()).isEqualTo(IS_ON_PROD);
    }

    @Test
    @Transactional
    public void updateApplication() {
        Application savedApplication = applicationService.saveApplication(application);
        savedApplication.setAppName("NEW APP NAME");
        savedApplication.setDescription("NEW DESCRIPTION");
        Application updatedApplication = applicationService.updateApplication(savedApplication);
        assertThat(updatedApplication).isNotNull();
        assertThat(updatedApplication.getId()).isEqualTo(savedApplication.getId());
        assertThat(updatedApplication.getAppName()).isEqualTo("NEW APP NAME");
        assertThat(updatedApplication.getAppType()).isEqualTo(DEFAULT_APP_TYPE);
        assertThat(updatedApplication.getAppVersion()).isEqualTo(DEFAULT_APP_VERSION);
        assertThat(updatedApplication.getDescription()).isEqualTo("NEW DESCRIPTION");
        assertThat(updatedApplication.getDevelopedBy()).isEqualTo(DEFAULT_DEVELOPED_BY);
        assertThat(updatedApplication.getWebUrl()).isEqualTo(DEFAULT_WEB_URL);
        assertThat(updatedApplication.getIsOnProd()).isEqualTo(IS_ON_PROD);
    }

    @Test
    @Transactional
    public void getApplicationById() {
        Application savedApplication = applicationService.saveApplication(application);
        assertThat(savedApplication).isNotNull();
        assertThat(savedApplication.getId()).isNotNull();

        Application foundApplication = applicationService.getApplicationById(savedApplication.getId());
        assertThat(foundApplication.getAppName()).isEqualTo(DEFAULT_APP_NAME);
        assertThat(foundApplication.getAppType()).isEqualTo(DEFAULT_APP_TYPE);
        assertThat(foundApplication.getAppVersion()).isEqualTo(DEFAULT_APP_VERSION);
        assertThat(foundApplication.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(foundApplication.getDevelopedBy()).isEqualTo(DEFAULT_DEVELOPED_BY);
        assertThat(foundApplication.getWebUrl()).isEqualTo(DEFAULT_WEB_URL);
        assertThat(foundApplication.getIsOnProd()).isEqualTo(IS_ON_PROD);

    }

    @Test
    @Transactional
    public void deleteApplicationById() {
        Application savedApplication = applicationService.saveApplication(application);

        assertThat(savedApplication).isNotNull();
        assertThat(savedApplication.getId()).isNotNull();
        applicationService.deleteApplicationById(savedApplication.getId());
        Optional<Application> deletedApp = applicationRepository.findById(savedApplication.getId());
        assertThat(deletedApp).isNotPresent();
    }

    @Test
    @Transactional
    public void getAllApplications() {
        applicationService.saveApplication(application);

        List<Application> foundApps = applicationService.getAllApplications();
        assertThat(foundApps.size()).isEqualTo(1);

        assertThat(foundApps.get(foundApps.size()-1).getAppName()).isEqualTo(DEFAULT_APP_NAME);
        assertThat(foundApps.get(foundApps.size()-1).getAppType()).isEqualTo(DEFAULT_APP_TYPE);
        assertThat(foundApps.get(foundApps.size()-1).getAppVersion()).isEqualTo(DEFAULT_APP_VERSION);
        assertThat(foundApps.get(foundApps.size()-1).getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(foundApps.get(foundApps.size()-1).getDevelopedBy()).isEqualTo(DEFAULT_DEVELOPED_BY);
        assertThat(foundApps.get(foundApps.size()-1).getWebUrl()).isEqualTo(DEFAULT_WEB_URL);
        assertThat(foundApps.get(foundApps.size()-1).getIsOnProd()).isEqualTo(IS_ON_PROD);
    }
}
