package nl.intratuin.testmarket.service.implementation;

import nl.intratuin.testmarket.dao.contract.AccessKeyDao;
import nl.intratuin.testmarket.service.contract.AccessKeyService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class AccessKeyServiceImpl implements AccessKeyService{
    @Inject
    private AccessKeyDao accessKeyDao;

    public Integer getCustomerIdByAccessKey(String accessKey){
        return accessKeyDao.getCustomerIdByAccessKey(accessKey);
    }
}

