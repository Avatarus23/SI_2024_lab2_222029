import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SILab2Test {
    @Test
    void everyBranch() {
        List<Item> lista = new ArrayList<>();
        RuntimeException exception;

        //1->2->39
        //Test case: allItems list is null
        exception = assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, 0));
        assertTrue(exception.getMessage().contains("allItems list can't be null!"));

        //Test case: No barcode exception
        lista = new ArrayList<Item>();
        lista.add(new Item("product1", null, 100, 1));
        List<Item> finalLista = lista;

        exception = assertThrows(RuntimeException.class, () -> SILab2.checkCart(finalLista, 0));
        assertTrue(exception.getMessage().contains("No barcode!"));

        //Test case: Invalid character in item barcode
        lista = new ArrayList<Item>();
        lista.add(new Item("product1", "222029", 100, 1));
        lista.add(new Item("product2", "222029bobi", 200, 2));
        List<Item> finalLista1 = lista;

        exception = assertThrows(RuntimeException.class, () -> SILab2.checkCart(finalLista1, 10));
        assertTrue(exception.getMessage().contains("Invalid character in item barcode!"));

        //0.1->4->5.1->5.2->33->36->37->39
        //Test case: No items with zero discount (total price < total price)
        lista = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i + 1);
            stringBuilder.append(i + 1000);
            lista.add(new Item("", stringBuilder.toString(), i + 1, 1));
        }
        assertEquals(false, SILab2.checkCart(lista, 1));

        //0.1->4->5.1->5.2->33->34->39
        //Test case: All items have zero discount
        lista = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(i + 1);
            stringBuilder.append(i * 1000);
            lista.add(new Item("", stringBuilder.toString(), i + 1, 0));
        }
        assertEquals(true, SILab2.checkCart(lista, 100));

        //Test case: Normal list with a mix of discounts
        lista = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(0);
            stringBuilder.append(i + 1 * 1000);
            StringBuilder nov = new StringBuilder();
            nov.append("Product");
            nov.append(i + 1);
            lista.add(new Item(nov.toString(), stringBuilder.toString(), i + 1 * 100, 0 + i));
        }
        assertEquals(false, SILab2.checkCart(lista, 100));
    }

    @Test
    void multipleConditionTest() {
        List<Item> lista = new ArrayList<Item>();
        //if (item.getPrice() > 300 && item.getDiscount() > 0 && item.getBarcode().charAt(0) == '0')

        //T && T && T
        lista.add(new Item("product1", "222029", 500, 50));
        assertEquals(false, SILab2.checkCart(lista, 100));

        //F && x && x
        lista = new ArrayList<>();
        lista.add(new Item("product2", "222029", 100, 1));
        assertEquals(true, SILab2.checkCart(lista, 500));

        //x && F && x
        lista = new ArrayList<>();
        lista.add(new Item("product3", "222029", 250, 0));
        assertEquals(true, SILab2.checkCart(lista, 300));

        //x && x && F
        lista = new ArrayList<>();
        lista.add(new Item("product4", "222029", 100, 1));
        assertEquals(true, SILab2.checkCart(lista, 100));

        //F && F && F
        //finish code here
        lista = new ArrayList<>();
        lista.add(new Item("product5", "222029", 300, 3));
        assertEquals(true, SILab2.checkCart(lista, 1000));
    }
}