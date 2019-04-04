package br.edu.ifce.pigeon.navigation;

public final class Navigation {

    private static Navigation instance;
    private INavigation navigationImpl;

    private Navigation() {
    }

    public void setNavigationImpl(INavigation impl) {
        this.navigationImpl = impl;
    }

    public INavigation getNavigationImpl() {
        return navigationImpl;
    }

    public static void openCreatePigeon() {
        getInstance().getNavigationImpl().openCreatePigeon();
    }

    public static void openCreateUser() {
        getInstance().getNavigationImpl().openCreateUser();
    }

    public static void close() { getInstance().getNavigationImpl().close(); }

    public static Navigation getInstance() {
        if (instance == null) {
            instance = new Navigation();
        }

        return instance;
    }
}
