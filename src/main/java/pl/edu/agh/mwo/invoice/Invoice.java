package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Collection<Product> products;


    protected Invoice() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Produkt musi być uzupełniony");
        }
        products.add(product);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException("Produkt musi być uzupełniony a ilość musi być większa niż zero");
        }

        IntStream.range(0, quantity)
                .forEach(i -> addProduct(product));
    }

    public BigDecimal getSubtotal() {
        return products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public BigDecimal getTax() {
        return products.stream()
                .map(product -> product.getPrice().multiply(product.getTaxPercent()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal() {
        //return getSubtotal();
        return getSubtotal().add(getTax());
    }
}
