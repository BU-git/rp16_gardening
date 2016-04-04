package nl.intratuin.testmarket.service.implementation;

import nl.intratuin.testmarket.dao.contract.CategoryDao;
import nl.intratuin.testmarket.entity.Category;
import nl.intratuin.testmarket.service.contract.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> findAllCategories() {
        return categoryDao.findAllCategories();
    }

    @Override
    public List<Category> findAllRootCategories() {
        return categoryDao.findAllRootCategories();
    }

    @Override
    public List<Category> findChildCategories(int categoryId) {
        return categoryDao.findChildCategories(categoryId);
    }

    @Override
    public List<Category> findById(int categoryId) {
        return categoryDao.findById(categoryId);
    }
}
