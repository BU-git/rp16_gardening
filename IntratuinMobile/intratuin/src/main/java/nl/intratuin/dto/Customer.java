package nl.intratuin.dto;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

/**
 * Created by Ivan on 19.03.2016.
 *
 * @author Ivan
 * @since 19.03.2016.
 */
public class Customer implements Parcelable {
    private int id;

    private String firstName;

    private String tussen;

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    private java.sql.Date birthday;

    private String city;

    private String streetName;

    private String houseNumber;

    private String postalCode;

    private int gender;

//    private byte[] fingerprint;

    public Customer() {
    }

    private Customer(Parcel in) {
        id = in.readInt();
        gender = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        password = in.readString();
        phoneNumber = in.readString();
        city = in.readString();
        streetName = in.readString();
        houseNumber = in.readString();
        postalCode = in.readString();
//        in.readByteArray(fingerprint);
        birthday = (java.sql.Date) in.readSerializable();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(gender);
        out.writeString(firstName);
        out.writeString(lastName);
        out.writeString(email);
        out.writeString(password);
        out.writeString(phoneNumber);
        out.writeString(city);
        out.writeString(streetName);
        out.writeString(houseNumber);
        out.writeString(postalCode);
        out.writeSerializable(birthday);
//        out.writeByteArray(fingerprint);
    }

    /**
     * The constant CREATOR generates instances of {@code Product} class from a Parcel
     *
     * @see Parcel
     * @see Parcelable
     */
    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets birthday.
     *
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday.
     *
     * @param birthday the birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets street name.
     *
     * @return the street name
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Sets street name.
     *
     * @param streetName the street name
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Gets house number.
     *
     * @return the house number
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Sets house number.
     *
     * @param houseNumber the house number
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets tussen.
     *
     * @return the tussen
     */
    public String getTussen() {
        return tussen;
    }

    /**
     * Sets tussen.
     *
     * @param tussen the tussen
     */
    public void setTussen(String tussen) {
        this.tussen = tussen;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public int getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    public static Customer parseFromJsonStr(String jsonStr) {
        try {
            JSONObject json = new JSONObject(jsonStr);

            Customer c = new Customer();
            String fullName = json.getString("name");
            String[] names = fullName.split(" ");
            if (names.length == 2) {
                c.firstName = names[0];
                c.tussen = "";
                c.lastName = names[1];
            } else {
                c.firstName = names[0];
                c.tussen = names[1];
                c.lastName = names[2];
            }
            c.email = json.getString("email");
            c.birthday = java.sql.Date.valueOf(json.getString("birthday"));
            c.gender = json.getInt("gender");
            c.phoneNumber = json.getString("phoneNumber");
            c.city = json.getString("city");
            c.postalCode = json.getString("postalCode");
            c.streetName = json.getString("streetName");
            c.houseNumber = json.getString("houseNumber");
            return c;
        } catch (JSONException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
