package nl.intratuin.testmarket.service.contract;

import nl.intratuin.testmarket.entity.AccessKey;

public interface AccessKeyService {
    Integer getCustomerIdByAccessKey(String accessKey);

    public void save(AccessKey accessKey);
}
