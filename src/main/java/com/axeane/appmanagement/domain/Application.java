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
