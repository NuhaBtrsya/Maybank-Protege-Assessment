class Item {

    private String name;
    private double price;
    private String category;

    public Item(String n, double p, String c) {
        this.name = n;
        this.price = p;
        this.category = c;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }   

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Name = " + name + " | Price = RM " + price + " | Category = " + category;
    }
    
}
    