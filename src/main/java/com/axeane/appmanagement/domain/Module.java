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
