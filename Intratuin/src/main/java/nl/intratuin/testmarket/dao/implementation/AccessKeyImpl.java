package nl.intratuin.testmarket.dao.implementation;

import nl.intratuin.testmarket.dao.contract.AccessKeyDao;
import nl.intratuin.testmarket.entity.AccessKey;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;

@Repository
public class AccessKeyImpl implements AccessKeyDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(AccessKey accessKey) {
        em.persist(accessKey);
    }

    public Integer getCustomerIdByAccessKey(String accessKey) {
        LocalDate currentDate = LocalDate.now();
        TypedQuery<AccessKey> queryFindByAccessKey = em.createQuery("SELECT AK FROM AccessKey AK WHERE AK.accessKey = :regex",
                AccessKey.class);
        queryFindByAccessKey.setParameter("regex", accessKey);
        try{
        AccessKey accessKeyEntity = queryFindByAccessKey.getSingleResult();
        if (accessKeyEntity != null) {
            return currentDate.isBefore(accessKeyEntity.getExpireDate().toLocalDate())
                    ? accessKeyEntity.getCustomerId() : -1;
        } else {
            return null;
        }} catch (Exception e) {
            System.out.print(e.getMessage());}
        return null;}

}
