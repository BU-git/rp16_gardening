package nl.intratuin.testmarket.dao.implementation;

import nl.intratuin.testmarket.dao.contract.AccessKeyDao;
import nl.intratuin.testmarket.entity.AccessKey;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class AccessKeyImpl implements AccessKeyDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(AccessKey accessKey) {
        em.persist(accessKey);
    }

    public AccessKey getNonDeprecatedKey(String accessKey){
        LocalDateTime currentDate = LocalDateTime.now();
        TypedQuery<AccessKey> queryFindByAccessKey = em.createQuery("SELECT AK FROM AccessKey AK WHERE AK.accessKey = :regex",
                AccessKey.class);
        queryFindByAccessKey.setParameter("regex", accessKey);
        try {
            AccessKey accessKeyEntity = queryFindByAccessKey.getSingleResult();
            if (accessKeyEntity != null && currentDate.isBefore(accessKeyEntity.getExpireDate())) {
                return accessKeyEntity;
            } else {
                return null;
            }
        } catch(Exception e){
            return null;
        }
    }

    public boolean addRefreshToken(String accessToken, String refreshToken){
        AccessKey keyEntity=getNonDeprecatedKey(accessToken);
        if(keyEntity!=null) {
            keyEntity.setRefreshAccessKey(refreshToken);
            em.persist(keyEntity);
            return true;
        }
        return false;
    }

    public boolean useRefresh(String accessToken, String refreshToken, int expire){
        AccessKey keyEntity=getNonDeprecatedKey(accessToken);
        if(keyEntity!=null && keyEntity.getRefreshAccessKey()!=null && keyEntity.getRefreshAccessKey().equals(refreshToken)) {
            LocalDateTime expireLocalDate = LocalDateTime.now().plusSeconds(expire);
            keyEntity.setExpireDate(expireLocalDate);
            em.persist(keyEntity);
            return true;
        }
        return false;
    }
}
