package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    public int getNumber() {

        return new Random().nextInt(10000);
    }

    ;
    private Map<Product, Integer> products = new HashMap<Product, Integer>();

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }


        boolean foundDuplicate = false;
        for (Product existingProduct : products.keySet()) {
            if (existingProduct.getName().equals(product.getName())) {
                // Jeśli nazwa produktu jest taka sama, zwiększ liczbę sztuk
                Integer currentQuantity = products.get(existingProduct);
                products.put(existingProduct, currentQuantity + quantity);
                foundDuplicate = true;
                break;
            }
        }

        // Jeśli nie znaleziono duplikatu, dodaj nową pozycję na fakturze
        if (!foundDuplicate) {
            products.put(product, quantity);
        }

            }



    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public String generateProductList() {
        StringBuilder productList = new StringBuilder();


        productList.append("Numer faktury: ").append(getNumber()).append("\n");

        productList.append("╔═══════════════════════════════════════════════════════════════════════════╗\n");
        productList.append("║           Lista produktów                                                 ║\n");
        productList.append("╠═══════════════════════════════════════════════════════════════════════════╣\n");

        products.forEach((product, quantity) -> {
            BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            String productName = product.getName();
            String quantityAndPrice = "Ilość:" + quantity + "|Cena:" + totalPrice;
            String line = String.format("║ %-50s │ %-20s ║\n", productName, quantityAndPrice);
            productList.append(line);
        });


        productList.append("╚═══════════════════════════════════════════════════════════════════════════╝\n");
        productList.append("Liczba pozycji: ").append(getNumberOfItems());

        return productList.toString();
    }

    public int getNumberOfItems() {return products.values().stream().mapToInt(Integer::intValue).sum();
    }

    public boolean hasAnyItems() {
        return !products.isEmpty();}

    public int getNumberOfPosition() {
        return products.size();
    }


    public int getProductQuantity(Product product) {
        return products.getOrDefault(product, 0);
    }



}
