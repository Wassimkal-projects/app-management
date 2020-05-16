package com.axeane.appmanagement.service.implementation;

import com.axeane.appmanagement.AppManagementApplication;
import com.axeane.appmanagement.domain.Application;
import com.axeane.appmanagement.domain.Module;
import com.axeane.appmanagement.repository.ModuleRepository;
import com.axeane.appmanagement.service.interfaces.IApplicationService;
import com.axeane.appmanagement.service.interfaces.IModuleService;
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
public class ModuleServiceImplTest {

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
    private IModuleService moduleService;

    @Autowired
    private ModuleRepository moduleRepository;

    private Module module;
    private Application application;

    private Application createApplicationEntity() {
        Application application = new Application();
        application.setAppName(DEFAULT_APP_NAME);
        application.setAppType(DEFAULT_APP_TYPE);
        application.setAppVersion(DEFAULT_APP_VERSION);
        application.setDescription(DEFAULT_DESCRIPTION);
        application.setWebUrl(DEFAULT_WEB_URL);
        application.setDevelopedBy(DEFAULT_DEVELOPED_BY);
        application.setIsOnProd(IS_ON_PROD);

        return application;
    }

    private Module createModuleEntity() {
        Module module = new Module();
        module.setModuleName(DEFAULT_MODULE_NAME);
        module.setModuleVersion(DEFAULT_MODULE_VERSION);
        module.setIsActive(IS_ACTIVE);
        return module;
    }

    @Before
    public void initTest() {
        module = createModuleEntity();
        application = createApplicationEntity();
    }

    @Test
    @Transactional
    public void saveModule() {
        Application savedApplication = applicationService.saveApplication(application);
        module.setApplication(savedApplication);
        Module savedModule = moduleService.saveModule(module);

        assertThat(savedModule).isNotNull();
        assertThat(savedModule.getId()).isNotNull();
        assertThat(savedModule.getModuleName()).isEqualTo(DEFAULT_MODULE_NAME);
        assertThat(savedModule.getModuleVersion()).isEqualTo(DEFAULT_MODULE_VERSION);
        assertThat(savedModule.getIsActive()).isEqualTo(IS_ACTIVE);
    }

    @Test
    public void getModuleById() {
        Application savedApplication = applicationService.saveApplication(application);
        module.setApplication(savedApplication);
        Module savedModule = moduleService.saveModule(module);
        Module foundModule = moduleService.getModuleById(savedModule.getId());

        assertThat(foundModule).isNotNull();
        assertThat(foundModule.getId()).isNotNull();
        assertThat(foundModule.getModuleName()).isEqualTo(DEFAULT_MODULE_NAME);
        assertThat(foundModule.getModuleVersion()).isEqualTo(DEFAULT_MODULE_VERSION);
        assertThat(foundModule.getIsActive()).isEqualTo(IS_ACTIVE);
    }

    @Test
    public void getAllModule() {
        Application savedApplication = applicationService.saveApplication(application);
        module.setApplication(savedApplication);
        Module savedModule = moduleService.saveModule(module);
        List<Module> foundModules = moduleService.getAllModule(savedModule.getApplication().getId());

        assertThat(foundModules.size()).isEqualTo(1);
        assertThat(foundModules.get(foundModules.size()-1).getModuleName()).isEqualTo(DEFAULT_MODULE_NAME);
        assertThat(foundModules.get(foundModules.size()-1).getModuleVersion()).isEqualTo(DEFAULT_MODULE_VERSION);
        assertThat(foundModules.get(foundModules.size()-1).getIsActive()).isEqualTo(IS_ACTIVE);

    }

    @Test
    public void deleteModuleById() {
        Application savedApplication = applicationService.saveApplication(application);
        module.setApplication(savedApplication);
        Module savedModule = moduleService.saveModule(module);

        assertThat(savedModule).isNotNull();
        assertThat(savedModule.getId()).isNotNull();
        moduleService.deleteModuleById(savedModule.getId());
        Optional<Module> deletedModule = moduleRepository.findById(savedApplication.getId());
        assertThat(deletedModule).isNotPresent();
    }

    @Test
    public void updateModule() {
    }
}
