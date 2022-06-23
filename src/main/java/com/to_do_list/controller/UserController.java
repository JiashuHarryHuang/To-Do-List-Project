package com.to_do_list.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.to_do_list.common.Result;
import com.to_do_list.domain.User;
import com.to_do_list.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Login
     * @param user Contains username and password
     * @param session Used to store user id
     * @return Success message
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody User user, HttpSession session) {
        log.info("Loggin in: {}", user.toString());

        //Encrypt password
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        log.info(password);

        //Query by condition: SELECT * FROM user WHERE username = ?
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, user.getUsername());
        User userInTbl = userService.getOne(userQueryWrapper);

        //Check if username exists
        if (userInTbl == null) {
            return Result.error("Username doesn't exist!");
        }

        //Check if password is correct
        if (!password.equals(userInTbl.getPassword())) {
            return Result.error("Password is incorrect!");
        }

        //Store user id into session.
        session.setAttribute("user", userInTbl.getId());
        return Result.success("Logged in successfully!");
    }

    @PostMapping("/signup")
    public Result<String> signup(@RequestBody User user, HttpServletResponse response) {
        log.info("Signing up: {}", user.toString());

        //Check if username already exists
        String username = user.getUsername();
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, username);
        int count = userService.count(userQueryWrapper);

        if (count != 0) {
            return Result.error("Username already exists");
        }

        //Encrypt password
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);

        //Add the user data into the database
        userService.save(user);

        return Result.success("Signed up successfully");
    }
}
