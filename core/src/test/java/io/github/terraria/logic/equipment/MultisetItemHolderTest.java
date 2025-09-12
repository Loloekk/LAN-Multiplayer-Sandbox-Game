package io.github.terraria.logic.equipment;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class MultisetItemHolderTest {
    Item item() { return Mockito.mock(Item.class); }
    @Test
    void newItemHolderIsEmpty() {
        ItemHolder holder = new MultisetItemHolder(10);
        assertThat(holder.browse()).isEmpty();
    }
    @Test
    void constructorArgumentCoalescesToNonnegative() {
        ItemHolder holder = new MultisetItemHolder(-3);
        assertThat(holder.browse()).isEmpty();
        assertFalse(holder.insert(item()));
    }
    @Test
    void capacitySingleInsertionReturnTest() {
        final int capacity = 2;
        ItemHolder holder = new MultisetItemHolder(capacity);
        holder.insert(item());
        holder.insert(item());
        assertFalse(holder.insert(item()));
    }
    @Test
    void capacityAccumulatedInsertionTest() {
        final int capacity = 5;
        ItemHolder holder = new MultisetItemHolder(capacity);
        Multiset<Item> set = HashMultiset.create();
        final int a = 3;
        final Item aItem = item();
        holder.insert(aItem, a);
        set.add(aItem, a);
        final int b = 4;
        final Item bItem = item();
        holder.insert(bItem, b);
        set.add(bItem, a+b-capacity);
        assertEquals(set, holder.browse());
    }
    @Test
    void capacityAccumulatedInsertionReturnTest() {
        final int capacity = 5;
        ItemHolder holder = new MultisetItemHolder(capacity);
        final int a = 3;
        holder.insert(item(), a);
        final int b = 4;
        assertEquals(a+b-capacity, holder.insert(item(), b));
    }
}
