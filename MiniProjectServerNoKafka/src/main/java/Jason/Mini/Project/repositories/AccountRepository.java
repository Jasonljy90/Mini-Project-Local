package Jason.Mini.Project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import Jason.Mini.Project.model.UserAccount;

@Repository
public class AccountRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String insertAccountSQL = "insert into account(is_enabled, email, username, password, last_login, num_failed_login_attempt) values(?, ?, ?, ?, ?, ?);";
    private final String findCustAccountSQL = "select * from account where email=?;";
    private final String findCustAccountUsernameSQL = "select * from account where username=?;";
    private final String changePasswordSQL = "update account set password = ? where email = ?;";
    private final String updateFailLoginCountSQL = "update account set num_failed_login_attempt = ? where email = ?;";
    private final String accountEnabledSQL = "update account set is_enabled = ? where username = ?;";
    private final String deleteUserSQL = "delete from account where email = ?";

    // Insert
    public boolean createAccount(UserAccount userAccount) {
        Integer result = jdbcTemplate.update(insertAccountSQL,
                userAccount.getIsEnabled(),
                userAccount.getEmail(),
                userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.getLast_login(),
                userAccount.getNumFailedLoginAttempt());
        return result > 0 ? true : false;
    }

    // Search by email
    public SqlRowSet findAccountByEmail(String email) {
        return jdbcTemplate.queryForRowSet(findCustAccountSQL, email);
    }

    // Search by username
    public SqlRowSet findAccountByUsername(String username) {
        return jdbcTemplate.queryForRowSet(findCustAccountUsernameSQL, username);
    }

    // Update
    public boolean updateIsEnabled(String username, boolean isEnabled) {
        int result = jdbcTemplate.update(accountEnabledSQL, isEnabled, username);
        return result > 0 ? true : false;
    }

    // Update
    public boolean updateFailLoginCount(String email, int count) {
        int result = jdbcTemplate.update(updateFailLoginCountSQL, count, email);
        return result > 0 ? true : false;
    }

    // Update
    public boolean changePassword(String email, String password) {
        int result = jdbcTemplate.update(changePasswordSQL, password, email);
        return result > 0 ? true : false;
    }

    // Delete
    public boolean deleteAccount(String email) {
        int result = jdbcTemplate.update(deleteUserSQL, email);
        return result > 0 ? true : false;
    }

    // @Transactional
    // public boolean insertOrders(Order orders) {
    // Boolean saveOrderResult = saveOrders(orders);
    // Boolean saveOrderDetailsResult = saveOrderDetails(orders);
    // if (saveOrderResult & saveOrderDetailsResult == true) {
    // return true;
    // }
    // return false;
    // }
}
