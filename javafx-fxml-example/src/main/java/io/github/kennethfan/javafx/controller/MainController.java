package io.github.kennethfan.javafx.controller;

import io.github.kennethfan.javafx.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("用户名和密码不能为空");
            return;
        }

        // 创建用户对象 (实际项目中这里可能是调用服务层)
        User user = new User(username, password);

        // 模拟验证
        if ("admin".equals(user.getUsername()) && "123456".equals(user.getPassword())) {
            messageLabel.setText("登录成功! 欢迎 " + user.getUsername());
        } else {
            messageLabel.setText("登录失败: 用户名或密码错误");
        }
    }

    @FXML
    private void handleClear() {
        usernameField.clear();
        passwordField.clear();
        messageLabel.setText("");
    }
}
