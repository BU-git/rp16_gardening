package nl.intratuin.testmarket.dao.implementation;

import nl.intratuin.testmarket.dao.contract.CategoryDao;
import nl.intratuin.testmarket.entity.Category;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Category> findAllRootCaegories() {
        TypedQuery<Category> queryFindAllRootCategories = em.createQuery("SELECT C FROM Category C WHERE C.parentId is null",
                Category.class);
        return queryFindAllRootCategories.getResultList();
    }

    @Override
    public List<Category> findChildCategories(int categoryId) {
        TypedQuery<Category> queryFindChildCategories = em.createQuery("SELECT C FROM Category C WHERE C.parentId " +
                "LIKE :regexp", Category.class);
        queryFindChildCategories.setParameter("regexp","%"+categoryId+"%");
        return queryFindChildCategories.getResultList();
    }

    @Override
    public List<Category> findById(int categoryId) {
        TypedQuery<Category> queryFindCategoryById = em.createQuery("SELECT C FROM Category C WHERE C.categoryId " +
                "LIKE :regexp", Category.class);
        queryFindCategoryById.setParameter("regexp",categoryId);
        return queryFindCategoryById.getResultList();
    }
}
