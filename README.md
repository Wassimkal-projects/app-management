# Gestion d'applications 
Le but de ce projet est de créer une relation de base de données One to many entre la table Application et la table Module

## Création du projet: 
Pour générer le projet, on a utilisé spring initializr, comme alternative on peut installer Spring boot CLI: 

![Image](https://github.com/Wassimkal-projects/app-management/blob/master/src/main/resources/initialzr.JPG)  

## Structure du projet: 
![Image](https://github.com/Wassimkal-projects/app-management/blob/master/src/main/resources/architecture.JPG)  

Puisque nous utilisons PostgreSQL comme base de données, nous devons configurer l'URL, le nom d'utilisateur et le mot de passe de la base de données afin que Spring puisse établir une connexion avec la base de données au démarrage,
cette configuration se fait au niveau du fichier properties.yml
## Properties.yml:
```yaml
server:
  port: 8070
spring:
  liquibase:
    change-log: classpath:/db/changelog/changelog-master.xml
  jpa:
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

  datasource:
    url: jdbc:postgresql://localhost:5432/onetomany
    username: wassim
    password: wassim
 ```
## Entités:
### Application.java

 ```java
 package com.axeane.appmanagement.domain;
 
 
 import javax.persistence.*;
 import javax.validation.constraints.NotNull;
 import java.io.Serializable;
 import java.util.HashSet;
 import java.util.Objects;
 import java.util.Set;
 
 @Entity
 @Table(name = "application")
 public class Application implements Serializable {
 
     private static final long serialVersionUID = 1L;
 
     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_SEQ")
     @SequenceGenerator(sequenceName = "application_seq", allocationSize = 1, name = "APPLICATION_SEQ")
     private Long id;
 
     @NotNull
     @Column(name = "app_name")
     private String appName;
 
     @NotNull
     @Column(name = "app_type")
     private String appType;
 
     @NotNull
     @Column(name = "description")
     private String description;
 
     @NotNull
     @Column(name = "app_version")
     private String appVersion;
 
     @Column(name = "web_url")
     private String webUrl;
 
     @Column(name = "developed_by")
     private String developedBy;
 
     @Column(name = "is_on_prod")
     private Boolean isOnProd;
 
     @OneToMany(mappedBy = "application", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<Module> modules = new HashSet<>();
 
     public Long getId() {
         return id;
     }
 
     public void setId(Long id) {
         this.id = id;
     }
 
     public String getAppName() {
         return appName;
     }
 
     public String getDescription() {
         return description;
     }
 
     public void setDescription(String description) {
         this.description = description;
     }
 
     public void setAppName(String appName) {
         this.appName = appName;
     }
 
     public String getAppType() {
         return appType;
     }
 
     public void setAppType(String appType) {
         this.appType = appType;
     }
 
     public String getAppVersion() {
         return appVersion;
     }
 
     public void setAppVersion(String appVersion) {
         this.appVersion = appVersion;
     }
 
     public String getWebUrl() {
         return webUrl;
     }
 
     public void setWebUrl(String webUrl) {
         this.webUrl = webUrl;
     }
 
     public String getDevelopedBy() {
         return developedBy;
     }
 
     public void setDevelopedBy(String developedBy) {
         this.developedBy = developedBy;
     }
 
     public Boolean getIsOnProd() {
         return isOnProd;
     }
 
     public void setIsOnProd(Boolean onProd) {
         isOnProd = onProd;
     }
 
     public Set<Module> getModules() {
         return modules;
     }
 
     public void setModules(Set<Module> modules) {
         this.modules = modules;
     }
 
     @Override
     public String toString() {
         return "Application{" +
                 "id=" + id +
                 ", appName='" + appName + '\'' +
                 ", appType='" + appType + '\'' +
                 ", description='" + description + '\'' +
                 ", appVersion='" + appVersion + '\'' +
                 ", webUrl='" + webUrl + '\'' +
                 ", developedBy='" + developedBy + '\'' +
                 ", isOnProd=" + isOnProd +
                 ", modules=" + modules +
                 '}';
     }
 
     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         Application that = (Application) o;
         return Objects.equals(getId(), that.getId()) &&
                 Objects.equals(getAppName(), that.getAppName()) &&
                 Objects.equals(getAppType(), that.getAppType());
     }
 
     @Override
     public int hashCode() {
         return Objects.hash(getId(), getAppName(), getAppType());
     }
 }

  ```

### Module.java
```java
package com.axeane.appmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "module")
public class Module implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODULE_SEQ")
    @SequenceGenerator(sequenceName = "module_seq", allocationSize = 1, name = "MODULE_SEQ")
    private Long id;

    @NotNull
    @Column(name = "module_name")
    private String moduleName;

    @NotNull
    @Column(name = "module_version")
    private String moduleVersion;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleVersion() {
        return moduleVersion;
    }

    public void setModuleVersion(String moduleVersion) {
        this.moduleVersion = moduleVersion;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    @JsonIgnore
    public Application getApplication() {
        return application;
    }

    @JsonProperty
    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", moduleName='" + moduleName + '\'' +
                ", moduleVersion='" + moduleVersion + '\'' +
                ", isActive=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(getId(), module.getId()) &&
                Objects.equals(getModuleName(), module.getModuleName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getModuleName());
    }
}

```

## Repository
Ensuite, nous allons définir les repository pour accéder aux données de la base de données:

### ApplicationRepository.java
  ```java
package com.axeane.appmanagement.repository;

import com.axeane.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}

  ```
### ModuleRepository.java
  ```java
package com.axeane.appmanagement.repository;

import com.axeane.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query(value = "select * from module as m where m.application_id =:id", nativeQuery = true)
    List<Module> getAllModulesByApp(@Param("id") Long id);
}
  ```
  
## Ecrire les API REST 
Écrivons maintenant les API REST pour effectuer des opérations CRUD sur les entités Application et Module.

Toutes les classes de contrôleur suivantes sont définies dans le package com.axeane.appmanagement.resource.

### ApplicationResource.class
  ```java
  package com.axeane.appmanagement.web.rest;
  
  import com.axeane.Application;
  import com.axeane.IApplicationService;
  import org.springframework.http.HttpStatus;
  import org.springframework.http.ResponseEntity;
  import org.springframework.web.bind.annotation.*;
  
  import javax.validation.Valid;
  
  @RestController
  @RequestMapping("/application")
  public class ApplicationResource {
  
      private final IApplicationService applicationService;
  
      public ApplicationResource(IApplicationService applicationService) {
          this.applicationService = applicationService;
      }
  
      @PostMapping("/saveApplication")
      public ResponseEntity saveApplication(@Valid @RequestBody Application app) {
          return new ResponseEntity<>(applicationService.saveApplication(app), HttpStatus.CREATED);
      }
  
      @PutMapping("/updateApplication")
      public ResponseEntity updateApplication(@Valid @RequestBody Application app){
          return new ResponseEntity<>(applicationService.updateApplication(app), HttpStatus.OK);
      }
  
      @GetMapping("/getApplicationById/{id}")
      public ResponseEntity getApplicationById(@PathVariable Long id){
          return new ResponseEntity<>(applicationService.getApplicationById(id),HttpStatus.OK);
      }
  
      @GetMapping
      public ResponseEntity getAllApplications(){
          return new ResponseEntity<>(applicationService.getAllApplications(),HttpStatus.OK);
      }
  
      @DeleteMapping("/deleteApplicationById/{id}")
      public ResponseEntity deleteApplicationById(@PathVariable Long id){
          applicationService.deleteApplicationById(id);
          return new ResponseEntity(HttpStatus.OK);
      }
  
  
  }

  ```
  
### ModuleResource.class
```java
package com.axeane.appmanagement.web.rest;

import com.axeane.Module;
import com.axeane.IModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/module")
public class ModuleResource {

    private final IModuleService moduleService;

    public ModuleResource(IModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping("/saveModule")
    public ResponseEntity saveModule(@Valid @RequestBody Module module) {
        return new ResponseEntity<>(moduleService.saveModule(module), HttpStatus.CREATED);
    }

    @PutMapping("/updateModule")
    public ResponseEntity updateModule(@Valid @RequestBody Module module) {
        return new ResponseEntity<>(moduleService.updateModule(module), HttpStatus.CREATED);

    }

    @GetMapping("/getModuleById/{id}")
    public ResponseEntity getModuleById(@PathVariable Long id) {
        return new ResponseEntity<>(moduleService.getModuleById(id), HttpStatus.OK);
    }

    @GetMapping("/getAllModulesByApp/{id}")
    public ResponseEntity getAllModuleByApp(@PathVariable Long id) {
        return new ResponseEntity<>(moduleService.getAllModule(id), HttpStatus.OK);
    }

    @DeleteMapping("deleteModuleById/{id}")
    public ResponseEntity deleteModuleById(@PathVariable Long id) {
        moduleService.deleteModuleById(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}

```


## Services: 
### ApplicationServiceImpl.class
```java
package com.axeane.appmanagement.service.implementation;

import com.axeane.Application;
import com.axeane.ApplicationRepository;
import com.axeane.IApplicationService;
import com.axeane.FormatNotValidException;
import com.axeane.ResourceNotFoundException;
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

```
### ModuleServiceImpl.class
```java
package com.axeane.appmanagement.service.implementation;

import com.axeane.Application;
import com.axeane.Module;
import com.axeane.ApplicationRepository;
import com.axeane.ModuleRepository;
import com.axeane.IModuleService;
import com.axeane.FormatNotValidException;
import com.axeane.ResourceNotFoundException;
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

```


## Testing with POSTMAN:
### Save application: 
![Image](https://github.com/Wassimkal-projects/app-management/blob/master/src/main/resources/save-app.JPG)  

### GetApplicationById: 
![Image](https://github.com/Wassimkal-projects/app-management/blob/master/src/main/resources/get-by-idJPG.JPG)  

## Ecrire les test Unitaires:
### ApplicationServiceImplTest.class
```java
package com.axeane.appmanagement.service.implementation;

import com.axeane.SpringbootOneToManyApplication;
import com.axeane.Application;
import com.axeane.Module;
import com.axeane.ApplicationRepository;
import com.axeane.ModuleRepository;
import com.axeane.IApplicationService;
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
@SpringBootTest(classes = SpringbootOneToManyApplication.class)
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

```

### ModuleServiceImplTest.class
```java
package com.axeane.appmanagement.service.implementation;

import com.axeane.SpringbootOneToManyApplication;
import com.axeane.Application;
import com.axeane.Module;
import com.axeane.ModuleRepository;
import com.axeane.IApplicationService;
import com.axeane.IModuleService;
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
@SpringBootTest(classes = SpringbootOneToManyApplication.class)
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

```
