package Jason.Mini.Project.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import Jason.Mini.Project.model.CountryInfo;

@Repository
public class CountryRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String insertCountryinfoSQL = "insert into countryinfo(country_name, currency_name, currency_symbol, area, capital, region, subregion, languages, maps, population, flags, date, username) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private String findSearchHistorySQL = "select * from countryinfo where username = ?;";
    // private final String findCustAccountSQL = "select * from account where
    // email=?;";
    // private final String findCustAccountUsernameSQL = "select * from account
    // where username=?;";
    // private final String changePasswordSQL = "update account set password = ?
    // where email = ?;";
    // private final String updateFailLoginCountSQL = "update account set
    // num_failed_login_attempt = ? where email = ?;";
    // private final String accountEnabledSQL = "update account set is_enabled = ?
    // where username = ?;";
    // private final String deleteUserSQL = "delete from account where email = ?";

    // Insert
    public boolean saveSearch(CountryInfo countryInfo) {
        Integer result = jdbcTemplate.update(insertCountryinfoSQL,
                countryInfo.getCountryName(),
                countryInfo.getCurrencyName(),
                countryInfo.getCurrencySymbol(),
                countryInfo.getArea(),
                countryInfo.getCapital(),
                countryInfo.getRegion(),
                countryInfo.getSubregion(),
                countryInfo.getLanguages(),
                countryInfo.getMaps(),
                countryInfo.getPopulation(),
                countryInfo.getFlags(),
                countryInfo.getDate(),
                countryInfo.getUsername());
        return result > 0 ? true : false;
    }

    public SqlRowSet findOrderDetailsByOrderId(String username) {
        return jdbcTemplate.queryForRowSet(findSearchHistorySQL, username);
    }
    // // Search by email
    // public SqlRowSet findAccountByEmail(String email) {
    // return jdbcTemplate.queryForRowSet(findCustAccountSQL, email);
    // }

    // // Search by username
    // public SqlRowSet findAccountByUsername(String username) {
    // return jdbcTemplate.queryForRowSet(findCustAccountUsernameSQL, username);
    // }

    // // Update
    // public boolean updateIsEnabled(String username, boolean isEnabled) {
    // int result = jdbcTemplate.update(accountEnabledSQL, isEnabled, username);
    // return result > 0 ? true : false;
    // }

    // // Update
    // public boolean updateFailLoginCount(String email, int count) {
    // int result = jdbcTemplate.update(updateFailLoginCountSQL, count, email);
    // return result > 0 ? true : false;
    // }

    // // Update
    // public boolean changePassword(String email, String password) {
    // int result = jdbcTemplate.update(changePasswordSQL, password, email);
    // return result > 0 ? true : false;
    // }

    // // Delete
    // public boolean deleteAccount(String email) {
    // int result = jdbcTemplate.update(deleteUserSQL, email);
    // return result > 0 ? true : false;
    // }

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
