package com.kargo.yycg.base.service.impl;

import com.kargo.yycg.base.service.AreaManagerService;
import com.kargo.yycg.base.service.SystemConfigService;
import com.kargo.yycg.base.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lwf on 17-1-17.
 */
@Service
public class BaseServiceFacade {

    @Autowired
    private UserManagerService userManagerService ;

    public UserManagerService getUserManagerService() {
        return userManagerService;
    }

    public void setUserManagerService(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    @Autowired
    private SystemConfigService systemConfigService ;

    public SystemConfigService getSystemConfigService() {
        return systemConfigService;
    }

    public void setSystemConfigService(SystemConfigService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }

    @Autowired
    private AreaManagerService areaManagerService;

    public AreaManagerService getAreaManagerService() {
        return areaManagerService;
    }

    public void setAreaManagerService(AreaManagerService areaManagerService) {
        this.areaManagerService = areaManagerService;
    }
}
