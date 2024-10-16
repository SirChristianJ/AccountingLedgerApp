package com.pluralsight;
/**
 *User class is used to represent a user,
 * this includes user's full name, email address, phone number
 */

public class User {
    private String fullName;
    private String userName;
    private String emailAddress;
    private String phoneNumber;

    public User(String fullName,String userName, String emailAddress, String phoneNumber) {
        this.fullName = fullName;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    //@Override
    public void toFormat() {
        System.out.println( "User{" +
                "\nfullName='" + fullName + '\'' +
                ", \nuserName='" + userName + '\'' +
                ", \nemailAddress='" + emailAddress + '\'' +
                ", \nphoneNumber='" + phoneNumber + '\'' +
                '}');
    }

    @Override
    public String toString() {
        return "User{" +
                "\nfullName='" + fullName + '\'' +
                ", \nuserName='" + userName + '\'' +
                ", \nemailAddress='" + emailAddress + '\'' +
                ", \nphoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
