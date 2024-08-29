import java.util.*;
public class RestaurantMenuList {

    private List<Item> menuItems = new ArrayList<>();

    public void addItem(String name, double price, String category) {
        Item item = new Item(name, price, category);
        menuItems.add(item);
    }

    //using general compareto function
    public void sortItemsByCategory() {
        Collections.sort(menuItems, new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                return a.getCategory().compareTo(b.getCategory());
            }
        });
    }

    public void sortItemsByPrice() {
        Collections.sort(menuItems, new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                return Double.compare(a.getPrice(), b.getPrice());
            }
        });
    }

    public void sortItemsByName() {
        Collections.sort(menuItems, new Comparator<Item>() {
            @Override
            public int compare(Item a, Item b) {
                return a.getName().compareTo(b.getName());
            }
        });
    }

    public void printMenu() {
        for (Item item : menuItems) {
            System.out.println(item);
        }
    }

    public static void main(String[] args) {
        //declare object
        RestaurantMenuList menu = new RestaurantMenuList();

        //declare scanner
        Scanner sc = new Scanner(System.in);

        System.out.print("How many items you want to add to the menu? ");
        int numItems = sc.nextInt();

        sc.nextLine();
        for (int i = 0; i < numItems; i++) {
            System.out.print("Enter item name: ");
            String name = sc.nextLine();

            System.out.print("Enter item price: ");
            double price = sc.nextDouble();

            sc.nextLine();
            System.out.print("Enter item category:");
            String category = sc.nextLine();

            menu.addItem(name, price, category);
        }

        //all sorted in ascending order

        System.out.println("Original Menu:");
        menu.printMenu();

        menu.sortItemsByCategory();
        System.out.println("\nMenu sorted by Category:");
        menu.printMenu();

        menu.sortItemsByPrice();
        System.out.println("\nMenu sorted by Price:");
        menu.printMenu();

        menu.sortItemsByName();
        System.out.println("\nMenu sorted by Name:");
        menu.printMenu();
    }
}