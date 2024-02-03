package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public abstract class Product {
    private final String name;

    private final BigDecimal price;

    private final BigDecimal taxPercent;

    protected Product(String name, BigDecimal price, BigDecimal tax) {

        if ((name==null) || (name.equals(""))) {
            throw new IllegalArgumentException("Produkt bez nazwy");
        }

        if (price==null) {
            throw new IllegalArgumentException("Cena to null");
        }

        if (price.compareTo(new BigDecimal(0)) ==-1){
                throw new IllegalArgumentException("Produkt bez nazwy");
            }



        this.name = name;
        this.price = price;
        this.taxPercent = tax;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public BigDecimal getPriceWithTax() {
        BigDecimal priceWithTax;
        priceWithTax = price.add(price.multiply(taxPercent));
        return priceWithTax;
    }
}
