module io.github.kennethfan {
    requires javafx.controls;
    requires javafx.fxml;

    // 如果使用了 common-util，需要添加
    requires common.util;

    // 开放包给 JavaFX FXML
    opens io.github.kennethfan to javafx.fxml;
    opens io.github.kennethfan.controller to javafx.fxml;

    // 导出包
    exports io.github.kennethfan;
    exports io.github.kennethfan.controller;
}