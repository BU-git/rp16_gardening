package nl.intratuin.testmarket.dao.contract;

import nl.intratuin.testmarket.entity.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> findAllCategories();

    List<Category> findAllRootCategories();

    List<Category> findChildCategories(int categoryId);

    List<Category> findById(int categoryId);

}
