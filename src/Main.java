import view.ItemView;

public class Main {
    public static void main(String[] args) {

        //1. call the displayAllItemsFiltered()
        //2. call the showMenu option from the view

        ItemView view = new ItemView();
        view.displayAllItemsFiltered();
        view.showMenu();

    }
}