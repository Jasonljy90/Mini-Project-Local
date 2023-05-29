package Jason.Mini.Project.services;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import Jason.Mini.Project.model.ChangePassword;
import Jason.Mini.Project.model.Login;
import Jason.Mini.Project.model.UserAccount;
import Jason.Mini.Project.repositories.AccountRepository;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserLoginVerification userLoginVerification;

    // Create Account -> send user verification email
    public boolean createAccount(UserAccount userAccount) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String password = userAccount.getPassword();

        // Get email and check if exists in database
        String email = userAccount.getEmail();
        SqlRowSet resultByEmail = accountRepository.findAccountByEmail(email);
        if (resultByEmail.next()) {
            System.out.println("Email already taken, please use another email");
            return false;
        }

        String username = userAccount.getUsername();
        SqlRowSet resultByUsername = accountRepository.findAccountByUsername(username);
        if (resultByUsername.next()) {
            System.out.println("Username already taken, please use another Username");
            return false;
        }

        boolean passwordValid = PasswordComplexityValidator.isValid(password);

        if (!passwordValid) {
            return false;
        }

        String passwordHash = HashingService.generateHashPassword(password);
        userAccount.setPassword(passwordHash);
        userAccount.setIsEnabled(false);

        // Get TimeStamp
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        userAccount.setLast_login(timestamp.toString());
        userAccount.setNumFailedLoginAttempt(0);
        boolean insertRepoSuccess = accountRepository.createAccount(userAccount);

        if (insertRepoSuccess) {
            return true;
        }
        return false;
    }

    // Works with angular
    // Login
    public UserAccount login(Login login) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // get email and check num of failed login attempt and account locked
        String email = login.getEmail();

        SqlRowSet result = accountRepository.findAccountByEmail(email);

        // User account found
        if (result.next()) {
            if (result.getBoolean("is_enabled") == false) {
                UserAccount userAccount = new UserAccount();
                System.out.println("Account is locked, please reset password");
                return userAccount;
            }

            boolean loginSuccess = userLoginVerification.verifyPassword(login.getPassword(),
                    result.getString("password"));

            if (loginSuccess == false) {
                int count = result.getInt("num_failed_login_attempt");
                count++;
                if (count == 5) {
                    accountRepository.updateIsEnabled(email, false);
                    accountRepository.updateFailLoginCount(email, 0);
                }
                accountRepository.updateFailLoginCount(email, count);
                System.out.println("Wrong password or email. Please try again 1");
                UserAccount userAccount = new UserAccount();
                return userAccount;
            } else {
                UserAccount userAccount = new UserAccount();
                userAccount.setEmail(email);
                userAccount.setUsername(result.getString("username"));
                userAccount.setUserId(result.getInt("user_id"));
                userAccount.setIsEnabled(true);
                userAccount.setPassword(result.getString("password"));
                userAccount.setLast_login(result.getString("last_login"));
                userAccount.setNumFailedLoginAttempt(result.getInt("num_failed_login_attempt"));

                // verify password if wrong password increment num of failed login attempt
                return userAccount;
            }
        } else {
            System.out.println("Wrong password or email. Please try again 2");
            UserAccount userAccount = new UserAccount();
            return userAccount;
        }
    }

    // Change Password
    public boolean changePassword(ChangePassword changePassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Check if old password is correct
        SqlRowSet result = accountRepository.findAccountByEmail(changePassword.getEmail());
        // User account not found
        if (!result.next()) {
            System.out.println("Wrong password or email. Please try again");
            return false;
        }

        boolean oldPasswordValid = userLoginVerification.verifyPassword(changePassword.getOldPassword(),
                result.getString("password"));

        boolean passwordValid = PasswordComplexityValidator.isValid(changePassword.getNewPassword());

        if (!passwordValid || !oldPasswordValid) {
            return false;
        }

        String passwordHash = HashingService.generateHashPassword(changePassword.getNewPassword());
        return accountRepository.changePassword(changePassword.getEmail(), passwordHash);
    }

    // Reset Change Password
    public boolean resetPassword(String email, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String passwordHash = HashingService.generateHashPassword(password);
        return accountRepository.changePassword(email, passwordHash);
    }

    public boolean findAccountByEmail(String email) {
        SqlRowSet result = accountRepository.findAccountByEmail(email);
        if (result.next()) {
            return true;
        }
        return false;
    }

    public boolean findAccountByUsername(String username) {
        SqlRowSet result = accountRepository.findAccountByUsername(username);
        if (result.next()) {
            return true;
        }
        return false;
    }

    // Set isEnabled in MySQL
    public boolean setIsEnabledTrueByUsername(String username) {
        return accountRepository.updateIsEnabled(username, true);
    }

    // Delete account
    public boolean deleteAccount(String email) {
        return accountRepository.deleteAccount(email);
    }

}
