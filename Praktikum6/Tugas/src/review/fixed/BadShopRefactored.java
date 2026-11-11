package review.fixed;

import java.util.ArrayList;
import java.util.List;

/*
 * T3 - CODE REVIEW
 * This file compiles and runs, but its design has several problems.
 * Find them, explain them, and refactor (see the module for instructions).
 */

abstract class Courier {
    protected abstract long ratePerKilogram();

    public final long cost(int kilograms) {
        if (kilograms <= 0) {
            throw new IllegalArgumentException("Berat harus positif: " + kilograms);
        }
        return ratePerKilogram() * kilograms;
    }
}

final class RegularCourier extends Courier {
    @Override protected long ratePerKilogram() { return 10_000L; }
}

final class ExpressCourier extends Courier {
    @Override protected long ratePerKilogram() { return 18_000L; }
}

final class SameDayCourier extends Courier {
    @Override protected long ratePerKilogram() { return 30_000L; }
}

interface PaymentMethod {
    boolean pay(long amount);
    String printInvoice();
}

interface Refundable {
    boolean refund(long amount);
}

interface Shippable {
    long ship(int weightGrams);
}

final class GiftVoucher implements PaymentMethod {
    private double balance = 100000.0;

    @Override
    public boolean pay(long amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }

    @Override
    public String printInvoice() {
        return "Voucher, remaining balance " + balance;
    }
}

final class Address {
    private final String street;
    private final String city;

    Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    Address withStreet(String newStreet) { return new Address(newStreet, city); }
    Address withCity(String newCity) { return new Address(street, newCity); }

    @Override
    public String toString() { return street + ", " + city; }
}

final class SimpleOrder {
    private final String id;
    private final Address address;

    SimpleOrder(String id, Address address) {
        this.id = id;
        this.address = address;
    }

    Address getAddress() { return address; }

    @Override
    public String toString() { return id + " -> " + address; }
}

class Cart {
    private final List<Item> items = new ArrayList<>();

    class Item {
        private final String name;
        private final long price;
        private final int quantity;
        Item(String name, long price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
        long subtotal() { return price * quantity; }
        String getName() { return name; }
    }

    void add(Item item) { items.add(item); }

    long total() {
        long total = 0;
        for (Item item : items) total += item.subtotal();
        return total;
    }
}

interface Discount {
    default long apply(long price) {
        if (this instanceof MemberDiscount) {
            return price - price * 5 / 100;
        } else if (this instanceof FlashSaleDiscount) {
            return price - price * 30 / 100;
        }
        return price;
    }
}
class MemberDiscount implements Discount {
}
class FlashSaleDiscount implements Discount {
}

public class BadShopRefactored {
    public static void main(String[] args) {
        Cart cart = new Cart();
        cart.add(cart.new Item("Kopi Gayo", 85_000, 2));
        System.out.println("Total cart: " + cart.total());

        Courier courier = new ExpressCourier();
        System.out.println("Ongkir Ekspres 2 kg: " + courier.cost(2));

        Address home = new Address("Jl. Sudirman 10", "Padang");
        SimpleOrder first = new SimpleOrder("ORD-1", home);
        SimpleOrder second = new SimpleOrder("ORD-2", home.withCity("Bukittinggi"));
        System.out.println(first);
        System.out.println(second);

        Discount flashSale = new FlashSaleDiscount();
        System.out.println("Harga flash sale: " + flashSale.apply(100_000));

        PaymentMethod voucher = new GiftVoucher();
        System.out.println(voucher.pay(30_000) + " | " + voucher.printInvoice());
    }
}