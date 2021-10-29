package pl.com.kocielapki.cattery.auth;

import java.util.List;

class Menu {
    private String name;
    private String url;
    private List<Menu> children;


    public Menu(String name, String url, List<Menu> children) {
        this.name = name;
        this.url = url;
        this.children = children;
    }


    public Menu(String name, String url) {
        this.name = name;
        this.url = url;
    }

    boolean hasChildren() {
        return !getChildren().isEmpty();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<Menu> getChildren() {
        return children;
    }


    @Override
    public String toString() {
        return "Menu{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", children=" + children +
                '}';
    }
}
