//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package kr.movements.vertical.common.util;
public enum Boot {
    main("/", "/"),
    swagger1("swagger", "/swagger"),
    swagger2("webjars", "/webjars"),
    swagger3("csrf", "/csrf"),
    error("error", "/error"),
    init("init", "/init"),
    login("login", "/users/user/login/"),
    csv("csv", "text/csv"),
    jpeg("jpg", "image/jpeg"),
    png("png", "image/png"),
    gif("gif", "image/gif"),
    bmp("bmp", "image/bmp"),
    bmpx("bmp", "image/x-bmp"),
    svg("svg", "image/svg+xml");

    private String name;
    private String value;

    private Boot(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}
