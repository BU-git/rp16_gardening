package nl.intratuin.testmarket.dao.implementation;

import nl.intratuin.testmarket.dao.contract.ProductDao;
import nl.intratuin.testmarket.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Иван on 27.03.2016.
 */
@Repository
public class ProductDaoImpl implements ProductDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Product findById(int id) {
        return em.find(Product.class,id);
    }

    @Override
    public List<Product> findAll() {
        TypedQuery<Product> queryFindAllProducts = em.createQuery("SELECT C FROM Product C", Product.class);
        return queryFindAllProducts.getResultList();
    }

    @Override
    public List<Product> findByName(String name) {
        TypedQuery<Product> queryFindProductsByName = em.createQuery("SELECT C FROM Product C WHERE C.productName " +
                "LIKE :regexp", Product.class);
        queryFindProductsByName.setParameter("regexp","%"+name+"%");
        return queryFindProductsByName.getResultList();
    }

    @Override
    public List<Product> findAllByCategory(String category) {
        TypedQuery<Product> queryFindProductsByCategory = em.createQuery("SELECT C FROM Product C WHERE C.categoryId " +
                "LIKE :regexp", Product.class);
        queryFindProductsByCategory.setParameter("regexp","%"+category+"%");
        return queryFindProductsByCategory.getResultList();
    }
}
