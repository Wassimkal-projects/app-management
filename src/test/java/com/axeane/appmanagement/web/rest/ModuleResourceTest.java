package com.axeane.appmanagement.web.rest;

import com.axeane.appmanagement.domain.Application;
import com.axeane.appmanagement.domain.Module;
import com.axeane.appmanagement.service.interfaces.IModuleService;
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
@WebMvcTest(ModuleResource.class)
public class ModuleResourceTest {

    private static final String DEFAULT_MODULE_NAME = "MODULE_NAME";
    private static final String DEFAULT_MODULE_VERSION = "MODULE_VERSION";
    private static final Boolean IS_ACTIVE = true;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private IModuleService moduleService;

    private Module module;

    private Module createEntity() {
        Application application = new Application();
        application.setId(1L);

        Module module = new Module();
        module.setApplication(application);
        module.setModuleName(DEFAULT_MODULE_NAME);
        module.setModuleVersion(DEFAULT_MODULE_VERSION);
        module.setIsActive(IS_ACTIVE);
        return module;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void initTest() {
        module = createEntity();
    }

    @Test
    public void saveModule() throws Exception {
        given(moduleService.saveModule(module)).willReturn(module);
        mockMvc.perform(post("/module/saveModule")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(module)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateModule() throws Exception {
        given(moduleService.updateModule(module)).willReturn(module);
        mockMvc.perform(put("/module/updateModule")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(module)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getModuleById() throws Exception {
        module.setId(1L);
        given(moduleService.getModuleById(module.getId())).willReturn(module);
        mockMvc.perform(get("/module/getModuleById/{id}", module.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.moduleName").value(DEFAULT_MODULE_NAME))
                .andExpect(jsonPath("$.moduleVersion").value(DEFAULT_MODULE_VERSION))
                .andExpect(jsonPath("$.isActive").value(IS_ACTIVE))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllModules() throws Exception {
        List<Module> modules = singletonList(module);
        given(moduleService.getAllModule(1L)).willReturn(modules);
        mockMvc.perform(get("/module/getAllModulesByApp/{id}", 1L)
                .contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].moduleName").value(DEFAULT_MODULE_NAME))
                .andExpect(jsonPath("$[0].moduleVersion").value(DEFAULT_MODULE_VERSION))
                .andExpect(jsonPath("$[0].isActive").value(IS_ACTIVE))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteApplicationById() throws Exception {
        module.setId(1L);
        doNothing().when(moduleService).deleteModuleById(module.getId());
        mockMvc.perform(
                delete("/module/deleteModuleById/{id}", module.getId()))
                .andExpect(status().isOk());
        verify(moduleService, times(1)).deleteModuleById(module.getId());
        verifyNoMoreInteractions(moduleService);
    }
}
