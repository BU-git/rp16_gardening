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
        queryFindProductsByName.setParameter("regexp","%"+name.toLowerCase()+"%");
        return queryFindProductsByName.getResultList();
    }

    @Override
    public List<Product> findAllByCategory(int category) {
        TypedQuery<Product> queryFindProductsByCategory = em.createQuery("SELECT P FROM Product P, Category C WHERE P.categoryId = C.categoryId " +
                "and P.categoryId = :regexp", Product.class);
        queryFindProductsByCategory.setParameter("regexp",category);
        List<Product> list = queryFindProductsByCategory.getResultList();
        return list;
    }

    @Override
    public Product findByBarcode(long barcode) {
        TypedQuery<Product> queryFindProductByBarcode = em.createQuery("SELECT P FROM Product P WHERE P.barcode = :regexp", Product.class);
        queryFindProductByBarcode.setParameter("regexp",barcode);
        List<Product> list = queryFindProductByBarcode.getResultList();
        return list.size() == 0 ? null : list.get(0);
    }
}
