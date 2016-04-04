package nl.intratuin.testmarket.service.contract;

import nl.intratuin.testmarket.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories();

    List<Category> findAllRootCategories();

    List<Category> findChildCategories(int categoryId);

    List<Category> findById(int categoryId);

}
