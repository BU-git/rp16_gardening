package nl.intratuin.testmarket.service.implementation;

import nl.intratuin.testmarket.dao.contract.AccessKeyDao;
import nl.intratuin.testmarket.dao.contract.CustomerDao;
import nl.intratuin.testmarket.dto.Credentials;
import nl.intratuin.testmarket.dto.LoginAndCacheResult;
import nl.intratuin.testmarket.dto.ResultLoginWithSocial;
import nl.intratuin.testmarket.entity.AccessKey;
import nl.intratuin.testmarket.entity.Customer;
import nl.intratuin.testmarket.service.contract.CustomerService;
import org.springframework.social.facebook.api.User;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Named
public class CustomerServiceImpl implements CustomerService {
    @Inject
    private CustomerDao customerDao;
    @Inject
    private AccessKeyDao accessKeyDao;

    public Customer findById(int id) {
        return customerDao.findById(id);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Transactional
    public boolean addCustomer(Customer customer) {
        //Check whether is email already registered or not
        String emailToRegister = customer.getEmail().toLowerCase();
        Integer existedCustomerId = customerDao.findByEmail(emailToRegister);

        if (existedCustomerId != null) {
            return false;
        } else {
            customerDao.save(customer);
            return true;
        }
    }

    @Transactional
    public LoginAndCacheResult login(Credentials credentials) {
        String emailToLogin = credentials.getEmail();
        Integer foundCustomerId = customerDao.findByEmail(emailToLogin);
        if (foundCustomerId != null) {
            Customer customerToLogin = customerDao.findById(foundCustomerId);
            if (customerToLogin.getPassword()==null){
                return new LoginAndCacheResult("Your prifile does not contain password. " +
                        "Log in using social networks and set password in private page", null);
            } else if (customerToLogin.getPassword().equals(credentials.getPassword())) {
                if (!credentials.getFlagToCache()) {
                    return new LoginAndCacheResult("Login is successful", null);
                } else {
                    AccessKey accessKeyEntity = new AccessKey();

                    String accessKey = UUID.randomUUID().toString() + customerToLogin.getId();
                    LocalDate expireLocalDate = LocalDate.now().plusDays(5);

                    accessKeyEntity.setCustomerId(customerToLogin.getId());
                    accessKeyEntity.setAccessKey(accessKey);
                    accessKeyEntity.setExpireDate(Date.valueOf(expireLocalDate));

                    accessKeyDao.save(accessKeyEntity);

                    return new LoginAndCacheResult("Hello, " + customerToLogin.getLastName() +
                            " " + customerToLogin.getFirstName(), accessKey);
                }
            } else {
                return new LoginAndCacheResult("Wrong", null);
            }
        }else{
            return new LoginAndCacheResult("Wrong", null);
            }
        }

        @Transactional
        public ResultLoginWithSocial loginTwitter (String email){
            Integer foundCustomerId = customerDao.findByEmail(email);
            if (foundCustomerId != null) {
                return ResultLoginWithSocial.SUCCESS;
            } else {
                Customer newCustomer = new Customer();
                newCustomer.setEmail(email);
                customerDao.save(newCustomer);
                return ResultLoginWithSocial.SUCCESSREGISTER;
            }
        }

        @Transactional
        public ResultLoginWithSocial loginWithFacebook (User profile){
            String emailToLoginWithFacebook = profile.getEmail();
            if (emailToLoginWithFacebook != null) {
                Integer existedCustomerId = customerDao.findByEmail(emailToLoginWithFacebook);

                return existedCustomerId != null
                        ? ResultLoginWithSocial.SUCCESS
                        : addWithFacebook(profile);
            } else {
                return ResultLoginWithSocial.ERROR;
            }
        }

        public Integer getCustomerIdByFacebookProfile(User profile){
            String emailToLoginWithFacebook = profile.getEmail();
            if (emailToLoginWithFacebook != null) {
                Integer existedCustomerId = customerDao.findByEmail(emailToLoginWithFacebook);

                return existedCustomerId != null
                        ? existedCustomerId
                        : -1;
            } else {
                return null;
            }
        }

        @Transactional
        private ResultLoginWithSocial addWithFacebook (User profile){
            Customer customer = new Customer();

            customer.setFirstName(profile.getFirstName());
            customer.setLastName(profile.getLastName());
            customer.setEmail(profile.getEmail().toLowerCase());

            if (profile.getLocation() != null) {
                customer.setCity(profile.getLocation().getName().toString());
            }
            String birthdayFromFacebook = profile.getBirthday();
            if (birthdayFromFacebook != null) {
                String[] arrForBirthday = birthdayFromFacebook.split("/");
                LocalDate birthdayLocalDate = LocalDate.of(Integer.parseInt(arrForBirthday[2]),
                        Integer.parseInt(arrForBirthday[0]), Integer.parseInt(arrForBirthday[1]));
                java.sql.Date birthday = java.sql.Date.valueOf(birthdayLocalDate);
                customer.setBirthday(birthday);
            }

            String genderFromFacebook = profile.getGender();
            if (genderFromFacebook != null) {
                customer.setGender(genderFromFacebook.equals("male") ? 1 : 0);
            }
            customerDao.save(customer);
            return ResultLoginWithSocial.SUCCESSREGISTER;
        }
    }
