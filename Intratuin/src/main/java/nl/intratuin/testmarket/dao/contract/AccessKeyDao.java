package nl.intratuin.testmarket.dao.contract;

import nl.intratuin.testmarket.entity.AccessKey;

public interface AccessKeyDao {
    void save(AccessKey accessKey);
    AccessKey getNonDeprecatedKey(String accessKey);
}
