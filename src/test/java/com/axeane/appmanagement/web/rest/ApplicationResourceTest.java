package com.axeane.appmanagement.web.rest;

import com.axeane.appmanagement.domain.Application;
import com.axeane.appmanagement.domain.Module;
import com.axeane.appmanagement.service.interfaces.IApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ApplicationResource.class)
public class ApplicationResourceTest {

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

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private IApplicationService applicationService;

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
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void initTest() {
        application = createEntity();
    }

    @Test
    public void saveApplication() throws Exception {
        given(applicationService.saveApplication(application)).willReturn(application);
        mockMvc.perform(post("/application/saveApplication")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(application)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateApplication() throws Exception {
        given(applicationService.updateApplication(application)).willReturn(application);
        mockMvc.perform(put("/application/updateApplication")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(application)))
                .andExpect(status().isOk());
    }

    @Test
    public void getApplicationById() throws Exception {
        application.setId(1L);
        given(applicationService.getApplicationById(application.getId())).willReturn(application);
        mockMvc.perform(get("/application/getApplicationById/{id}", application.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.appName").value(DEFAULT_APP_NAME))
                .andExpect(jsonPath("$.appType").value(DEFAULT_APP_TYPE))
                .andExpect(jsonPath("$.appVersion").value(DEFAULT_APP_VERSION))
                .andExpect(jsonPath("$.developedBy").value(DEFAULT_DEVELOPED_BY))
                .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
                .andExpect(jsonPath("$.isOnProd").value(IS_ON_PROD))
                .andExpect(jsonPath("$.webUrl").value(DEFAULT_WEB_URL))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllApplications() throws Exception {
        List<Application> apps = singletonList(application);
        given(applicationService.getAllApplications()).willReturn(apps);
        mockMvc.perform(get("/application")
                .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].appName").value(DEFAULT_APP_NAME))
                .andExpect(jsonPath("$[0].appType").value(DEFAULT_APP_TYPE))
                .andExpect(jsonPath("$[0].appVersion").value(DEFAULT_APP_VERSION))
                .andExpect(jsonPath("$[0].developedBy").value(DEFAULT_DEVELOPED_BY))
                .andExpect(jsonPath("$[0].description").value(DEFAULT_DESCRIPTION))
                .andExpect(jsonPath("$[0].isOnProd").value(IS_ON_PROD))
                .andExpect(jsonPath("$[0].webUrl").value(DEFAULT_WEB_URL))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteApplicationById() throws Exception {
        application.setId(1L);
        doNothing().when(applicationService).deleteApplicationById(application.getId());
        mockMvc.perform(
                delete("/application/deleteApplicationById/{id}", application.getId()))
                .andExpect(status().isOk());
        verify(applicationService, times(1)).deleteApplicationById(application.getId());
        verifyNoMoreInteractions(applicationService);
    }
}
