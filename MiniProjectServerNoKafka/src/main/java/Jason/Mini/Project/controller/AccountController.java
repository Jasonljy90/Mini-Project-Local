package Jason.Mini.Project.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Jason.Mini.Project.model.ChangePassword;
import Jason.Mini.Project.model.Login;
import Jason.Mini.Project.model.OtpRedis;
import Jason.Mini.Project.model.UserAccount;
import Jason.Mini.Project.services.AccountService;
import Jason.Mini.Project.services.EmailService;
import Jason.Mini.Project.services.OtpRedisService;
import Jason.Mini.Project.services.PasswordGeneratorService;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

//user_id, is_enabled, email, username, password, last_login, num_failed_login_attempt
@Controller
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    EmailService emailService;

    @Autowired
    OtpRedisService otpRedisService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    PasswordGeneratorService passwordGeneratorService;

    // Works with Angular
    @ResponseBody
    @PostMapping(path = "/signup", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public JsonObject createAccount(Model model, UserAccount userAccount)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        // Insert account into database once verify all necessary fields are ok
        boolean signUpSuccessful = accountService.createAccount(userAccount);

        if (signUpSuccessful) {
            model.addAttribute("userAccount", userAccount);
            OtpRedis otp = new OtpRedis();
            String username = userAccount.getUsername();

            // generate and save OTP in redis
            String otpValue = otpRedisService.generateSaveOTP(username, otp);
            otp.setOtp(otpValue);

            emailService.sendEmail(userAccount.getEmail(), "OTP",
                    "Your otp is " + otpValue + " OTP expires in 5 minutes");
            // redirect to key in OTP page
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("success", true);
            JsonObject result = jsonObjectBuilder.build();
            return result;
        }
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("success", false);
        JsonObject result = jsonObjectBuilder.build();
        return result;
    }

    // Works with Angular
    @ResponseBody
    @PostMapping(path = "/login", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public JsonObject loginAccount(Model model, Login login) throws NoSuchAlgorithmException, InvalidKeySpecException {

        // Insert account into database once verify all necessary fields are ok
        UserAccount userAccount = accountService.login(login);
        if (userAccount.getUsername() != null) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("username", userAccount.getUsername());
            JsonObject result = jsonObjectBuilder.build();
            return result;
        }
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("username", "");
        JsonObject result = jsonObjectBuilder.build();
        return result;
    }

    // Works with Angular
    @ResponseBody
    @PutMapping(path = "/changepassword", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public JsonObject passwordChange(@RequestBody ChangePassword changePassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        boolean changePasswordSuccess = accountService.changePassword(changePassword);

        if (changePasswordSuccess) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("success", true);
            JsonObject result = jsonObjectBuilder.build();
            return result;
        }
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("success", false);
        JsonObject result = jsonObjectBuilder.build();
        return result;
    }

    // Works with Angular
    // Reset Password use Query String
    @ResponseBody
    @GetMapping(path = "/reset")
    public JsonObject resetPassword(@RequestParam(required = true) String email)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        boolean result = accountService.findAccountByEmail(email);
        if (result == false) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("success", false);
            JsonObject result1 = jsonObjectBuilder.build();
            return result1;
        }

        String password = passwordGeneratorService.generatePassayPassword();
        // Update password in DB
        result = accountService.resetPassword(email, password);

        if (result == false) {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("success", false);
            JsonObject result1 = jsonObjectBuilder.build();
            return result1;
        }

        String message = "Your new password is " + password;
        emailService.sendEmail(email, "Reset Password", message);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("success", true);
        JsonObject result1 = jsonObjectBuilder.build();
        return result1;
    }

    // Works with Angular
    // OTP verification
    @ResponseBody
    @PostMapping(path = "/verifyotp", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public JsonObject verifyOtp(OtpRedis otp) {
        String username = otp.getUsername();
        String otpInDb = otpRedisService.findOtpByUsername(username);

        if (otpInDb.equals(otp.getOtp())) {
            otpRedisService.clearOtpByUsername(username);
            // Set isEnabled to true
            accountService.setIsEnabledTrueByUsername(username);

            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("success", true);
            JsonObject result = jsonObjectBuilder.build();
            return result;
        }

        if (otpInDb == "") {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("success", false);
            JsonObject result = jsonObjectBuilder.build();
            return result;
        }

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        jsonObjectBuilder.add("success", false);
        JsonObject result = jsonObjectBuilder.build();
        return result;
    }

    // Delete Account RequestParam/Query String
    // http://localhost:8080/deleteaccount?email=5
    // User can only delete account after login
    @PostMapping(path = "/deleteaccount")
    public String deleteAccount(@RequestParam(name = "email", required = true) String email) {
        boolean result = accountService.deleteAccount(email);

        if (result) {
            return "deletesuccess";
        }
        return "deletefail";
    }
}

/*
 * PATH VARIABLE
 * 
 * @GetMapping(path = "/{game_id}")
 * public ResponseEntity<String> byRank(@PathVariable(value = "game_id") Integer
 * gameId) {
 * 
 * JsonObject resp = gameSvc.byGid(gameId);
 * 
 * if (resp.size() == 0) {
 * return ResponseEntity.status(HttpStatus.NOT_FOUND)
 * .body("Game ID does not exists");
 * }
 * 
 * return ResponseEntity.status(HttpStatus.OK)
 * .body(resp.toString());
 * }
 */

/*
 * REQUEST PARAM/Query String
 * 
 * @GetMapping() // http://localhost:8080/games?limit=5&offset=0
 * public ResponseEntity<String> byName(
 * 
 * @RequestParam(name = "limit", required = false, defaultValue = "25") int
 * limit,
 * 
 * @RequestParam(name = "offset", required = false, defaultValue = "0") int
 * offset) { // http://localhost:8080/games?limit=5&offset=0
 * 
 * JsonObject resp = gameSvc.byName(limit, offset);
 * 
 * return ResponseEntity.status(HttpStatus.OK)
 * .body(resp.toString());
 * }
 */

/*
 * @PostMapping()
 * public String order(@RequestParam(required = true) String
 * customerName, @RequestParam(required = true) String
 * shipAddress, @RequestParam(required = true) String
 * notes, @RequestParam(required = true) String product, @RequestParam(required
 * = true) Integer quantity, Model model){
 * Order order = new Order(customerName, shipAddress, notes, product, quantity);
 * order.setCustomerName(customerName);
 * order.setShipAddress(shipAddress);
 * order.setNotes(notes);
 * order.setProduct(product);
 * order.setQuantity(quantity);
 * Boolean result = orderRepoImpl.insertOrders(order);
 * 
 * if(result){
 * model.addAttribute("order", order);
 * return "result";
 * }
 * //model.addAttribute("insertrslt", "Order placed unsuccessfully");
 * return "index";
 * }
 */

/*
 * @PostMapping
 * public ResponseEntity<String> createBoardGame(@RequestBody Mastermind ms){
 * System.out.println("MS >" + ms.toString());
 * int insertCnt = bgSvc.saveGame(ms);
 * Mastermind result = new Mastermind();
 * result.setId(ms.getId());
 * result.setInsertCount(insertCnt);
 * 
 * if(insertCnt == 0){
 * return ResponseEntity
 * .status(HttpStatus.INTERNAL_SERVER_ERROR)
 * .contentType(MediaType.APPLICATION_JSON)
 * .body( result.toJSONInsert().toString());
 * }
 * return ResponseEntity
 * .status(HttpStatus.CREATED)
 * .contentType(MediaType.APPLICATION_JSON)
 * .body( result.toJSONInsert().toString());
 * }
 */