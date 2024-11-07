package store.domain;

import static store.utils.Constants.ENTER;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class Products {

    private final List<Product> productList;

    public Products(List<Product> productList) {
        this.productList = productList;
    }

    public static Products create(List<Product> productList){
        return new Products(productList);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(ENTER);

        for (Product product : productList) {
            joiner.add(product.toString());
        }

        return joiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Products products = (Products) o;
        return Objects.equals(productList, products.productList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productList);
    }
}
