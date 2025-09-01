package io.github.terraria.logic;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ItemHolderTest {
    private static final int cap = 10;
    private static class DummyItemHolder extends ItemHolder {
        public DummyItemHolder() {
            super(cap);
        }
        public DummyItemHolder(int cap) {
            super(cap);
        }
    }
    private static class Identical implements Item {
        static final int hash = 9687;
        @Override
        public boolean equals(Object o) {
            if(this == o) return true;
            return o != null && getClass() == o.getClass();
        }
        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public ItemType type() {
            return null;
        }
    }
    @Test
    void newItemHolderIsEmpty() {
        ItemHolder holder = new DummyItemHolder();
        assertThat(holder.browse()).isEmpty();
    }
    @Test
    void constructorArgumentCoalescesToNonnegative() {
        ItemHolder holder = new DummyItemHolder(-3);
        assertThat(holder.browse()).isEmpty();
        assertFalse(holder.insert(Mockito.mock(Item.class)));
    }
    @Test
    void testInsert() {
        ItemHolder holder = new DummyItemHolder();
        for(int i=1; i<=cap; i++) {
            if(i%2 == 1) {
                Item item = Mockito.mock(Item.class);
                holder.insert(item);
                assertEquals(1, holder.getCount(item));
            }
            else {
                holder.insert(new Identical());
                assertEquals(i/2, holder.getCount(new Identical()));
            }
        }
        assertFalse(holder.insert(new Identical()));
        assertEquals(cap/2, holder.getCount(new Identical()));
        Item item = Mockito.mock(Item.class);
        assertFalse(holder.insert(item));
        assertEquals(0, holder.getCount(item));
    }

    @Test
    void testRemove() {
        ItemHolder holder = new DummyItemHolder();
        for(int i=1; i<=cap; i++) {
            if(i%2 == 1) {
                Item item = Mockito.mock(Item.class);
                holder.insert(item);
                assertEquals(1,holder.remove(item, i));
            }
            else {
                holder.insert(new Identical());
            }
        }
        assertEquals(1,holder.remove(new Identical()));
        assertEquals(cap/2-1, holder.getCount(new Identical()));
        assertEquals(cap/2-1,holder.remove(new Identical(), cap/2-1));
        assertEquals(0, holder.getCount(new Identical()));
    }
}
