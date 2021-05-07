package simple_gui_app;

import java.io.Serializable;

/**
 * Product Object Class
 * Date: April 15, 2021
 * Programmed by: Tommy Chiu
 */

class Product implements Serializable
{
    String name;
    int    id;
    double price;

    public Product(String name, int id, double price)
    {
        this.name  = name;
        this.id    = id;
        this.price = price;
    }
}
