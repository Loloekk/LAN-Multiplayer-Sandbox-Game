package io.github.terraria.logic;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ItemHolderTest {
    private final int cap = 10;
    private class DummyItemHolder extends ItemHolder {

        public DummyItemHolder() {
            super(cap);
        }
    }
    @Test
    void newItemHolderIsEmpty() {
        ItemHolder holder = new DummyItemHolder();
        assertThat(holder.browse()).isEmpty();
    }
    @Test
    void getCount() {

    }
    @Test
    void testInsert() {
        ItemHolder holder = new DummyItemHolder();
        Item global = Mockito.mock(Item.class);
        for(int i=1; i<=cap; i++) {
            if(i%2 == 1) {
                Item item = Mockito.mock(Item.class);
                holder.insert(item);
                assertEquals(1, holder.getCount(item));
            }
            else {
                holder.insert(global);
                assertEquals(i/2, holder.getCount(global));
            }
        }
        assertFalse(holder.insert(global));
        assertEquals(cap/2, holder.getCount(global));
    }

    @Test
    void remove() {
    }

    @Test
    void testRemove() {
    }
}
